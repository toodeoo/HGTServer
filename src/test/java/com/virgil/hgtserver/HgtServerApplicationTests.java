package com.virgil.hgtserver;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HgtServerApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void  myTest(){
        User user = userMapper.queryByToken("000000");
        System.out.println(JSONObject.toJSONString(user));
        user.setAge(19);
        userMapper.updateUserMsg(user);
        System.out.println(JSONObject.toJSONString(userMapper.queryByToken("000000")));
    }
}
