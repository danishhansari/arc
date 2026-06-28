package com.arc.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private UUID id;

    private String companyName;

    private String email;

    private String mobileNumber;

    private Timestamp createdAt;

    private Timestamp updatedAt;

}
