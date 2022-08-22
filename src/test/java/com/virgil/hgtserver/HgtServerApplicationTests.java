package com.virgil.hgtserver;

import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.mappers.VoteMapper;
import com.virgil.hgtserver.mappers.WishMapper;
import com.virgil.hgtserver.service.TravelService;
import com.virgil.hgtserver.service.VoteService;
import com.virgil.hgtserver.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HgtServerApplicationTests {

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private TravelService travelService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VoteMapper voteMapper;

    @Autowired
    private VoteService voteService;

    private final String token = "osDXq5IFPw3eERUD8LejfgAF0108";

    @Autowired
    private WishService wishService;

    @Autowired
    private WishMapper wishMapper;

    @Test
    public void  myTest(){
        wishMapper.insertDefault("外卖！！！", 10, "eat", 1);
        wishMapper.insertDefault("不出门，自己做", 10, "eat", 1);
        wishMapper.insertDefault("不吃了", 10, "eat", 1);
    }
}
