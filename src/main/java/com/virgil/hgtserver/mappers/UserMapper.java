package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {
    void insertUser(User user);

    User queryByToken(String token);

    void updateUserMsg(User user);

    int isExist(String token);

}
