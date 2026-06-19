package com.arc.service.impl;

import com.arc.assembler.UserAssembler;
import com.arc.config.JwtProvider;
import com.arc.dto.EmailDTO;
import com.arc.dto.UserDTO;
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

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final KafkaProducerService kafkaProducerService;
    private final StringRedisTemplate redisTemplate;

    @Override
    public UserDTO signup(UserPojo pojo) throws Exception {
        userRepository.findByEmail(pojo.getEmail()).orElseThrow(() -> new Exception("Email already registered"));
        String hashedPassword = passwordEncoder.encode(pojo.getPassword());
        User user = UserAssembler.getInstance().assembleDTO(pojo);
        user.setPassword(hashedPassword);
        user = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        String jwtToken = jwtProvider.generateToken(authentication, user.getId());
        UserDTO dto = UserAssembler.getInstance().assembleDetails(user);
        dto.setJwt(jwtToken);
        kafkaProducerService.sendUserDetailToIssueService("user", dto);
        return dto;
    }

    @Override
    public UserDTO login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        Authentication authentication = authentication(email, password);
        String jwtToken = jwtProvider.generateToken(authentication,user.getId());
        UserDTO dto = UserAssembler.getInstance().assembleDetails(user);
        dto.setJwt(jwtToken);
        return dto;
    }

    public Authentication authentication(String email, String password) throws Exception {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new Exception("Invalid credentials");
        }
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

    @Override
    public void validateOtp(ValidateEmailPojo validateEmailPojo) {
        String cacheKey = "otp:" + validateEmailPojo.getEmail();
        String cacheOtp = redisTemplate.opsForValue().get(cacheKey);
        if(cacheOtp == null) return;
        String otp = validateEmailPojo.getOtp();
        if(cacheOtp.equalsIgnoreCase(otp)) {
            System.out.println("It iw working fine for this otp " + cacheOtp);
        } else {
            System.out.println("I think otp is incorrect");
        }
    }
}
