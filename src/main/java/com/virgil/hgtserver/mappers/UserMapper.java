package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
    void insertUser(User user);

    User queryByToken(String token);

    void updateUserMsg(User user);

    String isExist(String token);

    String querySessionByToken(String token);

    String queryAvatarByToken( @Param("token") String token );

    String queryUserName( @Param("token") String token );

    void updateSessionKey( User user );
}
