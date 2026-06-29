package com.arc.service.impl;

import com.arc.assembler.UserAssembler;
import com.arc.config.JwtService;
import com.arc.dto.EmailDTO;
import com.arc.dto.UserDTO;
import com.arc.dto.VerificationDTO;
import com.arc.dto.WorkspaceDTO;
import com.arc.entity.User;
import com.arc.pojo.UserPojo;
import com.arc.pojo.ValidateEmailPojo;
import com.arc.repository.UserRepository;
import com.arc.service.KafkaProducerService;
import com.arc.service.UserService;
import com.arc.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final KafkaProducerService kafkaProducerService;
    private final StringRedisTemplate redisTemplate;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public UserDTO signup(UserPojo pojo) throws Exception {
        userRepository.findByEmail(pojo.getEmail()).ifPresent(e -> {
            throw new RuntimeException("User already exists");
        });
        String hashedPassword = passwordEncoder.encode(pojo.getPassword());
        User user = UserAssembler.getInstance().assembleDTO(pojo);
        user.setPassword(hashedPassword);
        user = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        String jwtToken = jwtService.generateToken(authentication, user.getId());
        UserDTO dto = UserAssembler.getInstance().assembleDetails(user);
//        dto.setJwt(jwtToken);
        kafkaProducerService.sendUserDetailToIssueService("user", dto);
        return dto;
    }

    @Override
    public UserDTO login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Authentication authentication = authentication(password, user);
        String jwtToken = jwtService.generateToken(authentication,user.getId());
        UserDTO dto = UserAssembler.getInstance().assembleDetails(user);
//        dto.setJwt(jwtToken);
        return dto;
    }

    public Authentication authentication(String password, User user) throws Exception {
        if (!passwordEncoder.matches(password, user.getPassword())) throw new Exception("Invalid credentials");
        UserDetails userDetails = customUserDetailsService.loadUserByEntity(user);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public void sendEmail(String email) {
        String cacheKey = "otp:" + email;
        if(redisTemplate.opsForValue().get(cacheKey) != null) return;
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    return userRepository.save(newUser);
                });
        String otp = OtpGenerator.generateOtp();
        redisTemplate.opsForValue().set(cacheKey, otp, 5, TimeUnit.MINUTES);
        EmailDTO emailDTO = new EmailDTO(user.getEmail(), otp);
        kafkaProducerService.sendAuthenticationEmail("email", emailDTO);
    }

    public Authentication authenticateOtp(User user) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByEntity(user);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public VerificationDTO validateOtp(ValidateEmailPojo validateEmailPojo) throws Exception {
        String cacheKey = "otp:" + validateEmailPojo.getEmail();
        String cacheOtp = redisTemplate.opsForValue().get(cacheKey);
        if(cacheOtp == null) {
            throw new Exception("Otp is expired");
        };
        String otp = validateEmailPojo.getOtp();
        if(!cacheOtp.equalsIgnoreCase(otp)) {
            throw new Exception("Otp is incorrect");
        }
        User user = userRepository.findByEmail(validateEmailPojo.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException(validateEmailPojo.getEmail()));
        UserDTO userDTO = UserAssembler.getInstance().assembleDetails(user);
        Authentication authentication = authenticateOtp(user);
        String jwt = jwtService.generateToken(authentication, user.getId());
        kafkaProducerService.sendUserDetailToIssueService("user", userDTO);
        List<WorkspaceDTO> workspaceDTOList = new ArrayList<>();
        return new VerificationDTO("Success", jwt, userDTO, workspaceDTOList);
    }
}
