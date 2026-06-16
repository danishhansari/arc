package com.arc.pojo;

import lombok.Getter;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
public class UserPojo {
    private String companyName;

    private String email;

    private String password;

    private String mobileNumber;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
