package com.arc.controller;

import com.arc.dto.UserDTO;
import com.arc.dto.VerificationDTO;
import com.arc.pojo.UserPojo;
import com.arc.pojo.ValidateEmailPojo;
import com.arc.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserPojo userPojo) throws Exception {
        UserDTO dto = userService.login(userPojo.getEmail(), userPojo.getPassword());
        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserPojo userPojo) throws Exception {
        UserDTO dto = userService.signup(userPojo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @PostMapping("/login/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendEmail(@RequestBody UserPojo userPojo) {
        userService.sendEmail(userPojo.getEmail());
    }

    @PostMapping("/validate/email")
    public ResponseEntity<VerificationDTO> validateOtp(@RequestBody ValidateEmailPojo validateEmailPojo) throws Exception{
        VerificationDTO dto = userService.validateOtp(validateEmailPojo);
        ResponseCookie cookie = ResponseCookie
                .from("token", dto.getJwt())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(86400)
                .build();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(dto);
    }
}
