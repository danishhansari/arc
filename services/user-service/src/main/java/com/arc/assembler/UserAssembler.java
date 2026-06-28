package com.arc.assembler;

import com.arc.dto.UserDTO;
import com.arc.entity.User;
import com.arc.pojo.UserPojo;

public class UserAssembler {
    private static UserAssembler instance;
    private UserAssembler() {}

    public static UserAssembler getInstance() {
        if(instance == null) {
            synchronized (UserAssembler.class) {
                if(instance == null) {
                    instance = new UserAssembler();
                }
            }
        }
        return instance;
    }

    public User assembleDTO (UserPojo pojo) {
        User user = new User();
        user.setCompanyName(pojo.getCompanyName());
        user.setEmail(pojo.getEmail());
        user.setMobileNumber(pojo.getMobileNumber());
        return user;
    }

    public UserDTO assembleDetails(User user) {
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        dto.setCompanyName(user.getCompanyName());
        dto.setMobileNumber(user.getMobileNumber());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setId(user.getId());
        return dto;
    }
}
