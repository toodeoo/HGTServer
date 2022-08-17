package com.virgil.hgtserver;

import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.mappers.VoteMapper;
import com.virgil.hgtserver.service.TravelService;
import com.virgil.hgtserver.service.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void  myTest(){
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        voteService.createVote(token, list);
    }
}
