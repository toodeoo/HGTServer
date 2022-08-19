package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.RetUser;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.pojo.User;
import com.virgil.hgtserver.service.MemberService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getMemberList( int travelId ) {
        List<String> tokenList = travelMapper.queryByTravelId(travelId);
        List<MemberIntro> retList = new ArrayList<>();
        for(String token: tokenList){
            MemberIntro memberIntro = new MemberIntro();
            User user = userMapper.queryByToken(token);
            memberIntro.setAvatar(user.getAvatar());
            memberIntro.setUsername(user.getUsername());
            memberIntro.setIsLeader(travelMapper.queryIsLeader(token, travelId));
            memberIntro.setWork(travelMapper.queryWork(token, travelId));
            retList.add(memberIntro);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("member", retList);
        return jsonObject.toJSONString();
    }

    @Override
    public String getMemberDetails( int travelId ,String username ) {
        String token = travelMapper.queryToken(travelId, username);
        if(token != null) {
            RetUser retUser = new RetUser(userMapper.queryByToken(token));
            retUser.setIsLeader(travelMapper.queryIsLeader(token, travelId));
            retUser.setWork(travelMapper.queryWork(token, travelId));
            return JSONObject.toJSONString(retUser);
        }
        else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errMsg", "No such user");
            return jsonObject.toJSONString();
        }
    }


}

@Data
class MemberIntro{
    private String username;
    private String work;
    private String avatar;
    private int isLeader;
}
