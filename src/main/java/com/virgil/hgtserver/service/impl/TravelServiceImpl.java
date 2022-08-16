package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.RetCode;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.pojo.Travel;
import com.virgil.hgtserver.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String createTravel( Travel travel ) {
        int code = 0;
        int id = travelMapper.queryMaxId() + 1;
        travel.setId(id);
        travel.setIsLeader(1);
        travel.setDate(new Date());
        travelMapper.insertTravel(travel);
        return JSONObject.toJSONString(new RetCode(code));
    }

    @Override
    public String getList( String token ) {
        List<Travel> list = travelMapper.queryByToken(token);
        int code = list.size() == 0 ? 0 : 1;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("travelIntro", list);
        jsonObject.put("username", userMapper.queryByToken(token).getUsername());
        return jsonObject.toJSONString();
    }
}
