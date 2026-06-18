package com.arc.service;

import com.arc.dto.UserDTO;
import com.arc.pojo.UserPojo;

public interface UserService {
    UserDTO signup(UserPojo pojo) throws Exception;
    UserDTO login(String email, String password) throws Exception;
    void sendEmail(String email);
}
