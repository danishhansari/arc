package com.arc.service.impl;

import com.arc.assembler.UserAssembler;
import com.arc.config.JwtProvider;
import com.arc.dto.EmailDTO;
import com.arc.dto.UserDTO;
import com.arc.entity.User;
import com.arc.pojo.UserPojo;
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
        User existingUser = userRepository.findByEmail(pojo.getEmail());
        if(existingUser != null) {
            throw new Exception("email already registered");
        }
        String hashedPassword = passwordEncoder.encode(pojo.getPassword());
        User user = UserAssembler.getInstance().assembleDTO(pojo);
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        String jwtToken = jwtProvider.generateToken(authentication, savedUser.getId());
        UserDTO dto = UserAssembler.getInstance().assembleDetails(savedUser);
        dto.setJwt(jwtToken);
        kafkaProducerService.sendUserDetailToIssueService("user", dto);
        return dto;
    }

    @Override
    public UserDTO login(String email, String password) throws Exception {
        Authentication authentication = authentication(email, password);
        User user = userRepository.findByEmail(email);
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

     return new UsernamePasswordAuthenticationToken(userDetails,
                null,
                userDetails.getAuthorities());
    }

    @Override
    public void sendEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("user doesn't exists");
        }
        String otp = OtpGenerator.generateOtp();
        redisTemplate.opsForValue().set(user.getEmail(), otp);
        System.out.println("This is otp " + otp);
        EmailDTO emailDTO = new EmailDTO(user.getEmail(), otp);
        kafkaProducerService.sendAuthenticationEmail("email", emailDTO);
    }
}
