package com.arc.service;

import com.arc.dto.UserDTO;
import com.arc.dto.VerificationDTO;
import com.arc.pojo.UserPojo;
import com.arc.pojo.ValidateEmailPojo;

public interface UserService {
    UserDTO signup(UserPojo pojo) throws Exception;
    UserDTO login(String email, String password) throws Exception;
    void sendEmail(String email);
    VerificationDTO validateOtp(ValidateEmailPojo validateEmailPojo) throws Exception;
}
