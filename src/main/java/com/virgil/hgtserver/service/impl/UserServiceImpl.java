package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.RetCode;
import com.virgil.hgtserver.conf.RetUser;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.pojo.User;
import com.virgil.hgtserver.service.UserService;
import com.virgil.hgtserver.utils.WeixinUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TravelMapper travelMapper;

    @Override
    public String login( HashMap<String, String> request ) {
        LoginMsg loginMsg = new LoginMsg();
        loginMsg.setToken("");
        HashMap<String, String> response = WeixinUtils.getSession(request.get("code"));
        loginMsg.setErrcode(response.get("errcode"));
        if(response.get("errcode").equals("0")){
            User user = new User();
            if(!request.get("username").equals(""))
                user.setUsername(request.get("username"));
            if(!request.get("avatar").equals(""))
                user.setAvatar(request.get("avatar"));
            user.setOpenid(response.get("openid"));
            user.setSession_key(response.get("session_key"));
            user.setToken(user.getOpenid());
            if(userMapper.isExist(user.getToken()) != null)
                userMapper.insertUser(user);
            else
                userMapper.updateUserMsg(user);

            loginMsg.setToken(user.getToken());
            loginMsg.setErrcode("0");
        }
        return JSONObject.toJSONString(loginMsg);
    }

    @Override
    public String getUserMsg( String token ) {
        User user = userMapper.queryByToken(token);
        RetUser retUser;
        if(user == null) {
           retUser = new RetUser();
           retUser.setCode(-1);
        }
        else {
            retUser = new RetUser(user);
        }
        return JSONObject.toJSONString(retUser);
    }

    @Override
    public String modifyUserMsg( HashMap<String, String> request ) {
        User user = userMapper.queryByToken(request.get("token"));
        for (String key : request.keySet()){
            if(key.equals("token"))
                continue;
            if(request.get(key).equals(""))
                continue;
            switch (key) {
                case "username":
                    user.setUsername(request.get(key));
                    break;
                case "age":
                    user.setAge(Integer.parseInt(request.get(key)));
                    break;
                case "sex":
                    user.setSex(request.get(key));
                    break;
                case "phone":
                    user.setPhone(request.get(key));
                    break;
                case "sosphone":
                    user.setSosPhone(request.get(key));
                    break;
                case "work":
                    travelMapper.updateWorkByToken(request.get("token"), request.get("work"),
                            Integer.parseInt(request.get("travelId")));
            }
        }
        userMapper.updateUserMsg(user);
        return JSONObject.toJSONString(new RetCode(0));
    }
}

@Data
class LoginMsg{
    private String token;
    private String errcode;

    @Override
    public String toString(){
        return "LoginMsg{" +
                "token=" + token + "\n" +
                "errcode=" + errcode + "\n" +
                "}";
    }
}

