package com.virgil.hgtserver;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.mappers.WhisperMapper;
import com.virgil.hgtserver.pojo.Travel;
import com.virgil.hgtserver.pojo.User;
import com.virgil.hgtserver.pojo.Whisper;
import com.virgil.hgtserver.service.TravelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HgtServerApplicationTests {

    @Autowired
    private TravelService travelService;

    @Test
    public void  myTest(){
        Travel travel = new Travel("摆烂", "家里蹲", "111111111");
        travelService.createTravel(travel);
    }
}
