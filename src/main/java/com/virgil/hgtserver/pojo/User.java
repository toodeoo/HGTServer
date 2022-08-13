package com.virgil.hgtserver.pojo;

import lombok.Data;

@Data
public class User {
    public User(){
        age = 0;
        sex = 0;
        sosPhone = "110";
    }
    private String username;
    private int age;
    private int sex;
    private String openid;
    private String session_key;
    private String phone;
    private String avatar;
    private String token;
    private String sosPhone;
}
