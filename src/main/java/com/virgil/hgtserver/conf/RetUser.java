package com.virgil.hgtserver.conf;

import com.virgil.hgtserver.pojo.User;
import lombok.Data;

@Data
public
class RetUser {
    public RetUser() {
    }

    public RetUser( User user ) {
        this.username = user.getUsername();
        this.age = user.getAge();
        this.sex = user.getSex();
        this.phone = user.getPhone();
        this.sosPhone = user.getSosPhone();
        this.avatar = user.getAvatar();
    }

    private String username;
    private int age;
    private String sex;
    private String phone;
    private String avatar;
    private String sosPhone;

    private int isLeader;

    private String work;

    private int code;
}
