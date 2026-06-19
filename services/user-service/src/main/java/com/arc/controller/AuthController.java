package com.arc.controller;

import com.arc.dto.UserDTO;
import com.arc.pojo.UserPojo;
import com.arc.pojo.ValidateEmailPojo;
import com.arc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserPojo userPojo) throws Exception {
        UserDTO dto = userService.login(userPojo.getEmail(), userPojo.getPassword());
        ResponseCookie cookie = ResponseCookie
                .from("token", dto.getJwt())
                .httpOnly(true)
                .path("/")
                .maxAge(86400)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(dto);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody UserPojo userPojo) throws Exception {
        UserDTO dto = userService.signup(userPojo);
        ResponseCookie cookie = ResponseCookie
                .from("token", dto.getJwt())
                .httpOnly(true)
                .path("/")
                .maxAge(86400)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(dto);
    }

    @PostMapping("/login/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendEmail(@RequestBody UserPojo userPojo) {
        userService.sendEmail(userPojo.getEmail());
    }

    @PostMapping("/validate/email")
    public void validateOtp(@RequestBody ValidateEmailPojo validateEmailPojo) {
        userService.validateOtp(validateEmailPojo);
    }
}
