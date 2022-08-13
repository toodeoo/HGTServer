package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.pojo.User;
import com.virgil.hgtserver.service.UserService;
import com.virgil.hgtserver.utils.Cypher;
import com.virgil.hgtserver.utils.WeixinUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public String login( HashMap<String, String> request ) {
        LoginMsg loginMsg = new LoginMsg();
        HashMap<String, String> response = WeixinUtils.getSession(request.get("code"));
        if(response.get("errcode").equals("0")){
            String token = Cypher.md5Encoder(response.get("openid"));
            User user = new User();
            user.setUsername(request.get("username"));
            user.setAvatar(request.get("avatar"));
            user.setOpenid(response.get("openid"));
            user.setSession_key(response.get("session_key"));
            user.setToken(token);
            try {
                user.setPhone(WeixinUtils.getPhone(request.get("encryptedData") ,request.get("iv") ,response.get("session_key")));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            userMapper.insertUser(user);
            loginMsg.setToken(token);
            loginMsg.setErrcode("0");
            return JSONObject.toJSONString(loginMsg);
        }
        else{
            loginMsg.setToken("");
            loginMsg.setErrcode(response.get("errcode"));
            return JSONObject.toJSONString(loginMsg);
        }
    }

    @Override
    public String getUserMsg( String token ) {
        User user = userMapper.queryByToken(token);
        return JSONObject.toJSONString(user);
    }

    @Override
    public String modifyUserMsg( HashMap<String, String> request ) {
        User user = userMapper.queryByToken(request.get("token"));
        for (String key : request.keySet()){
            if(key.equals("token"))
                continue;
            if (request.get(key).equals(""))
                continue;
            if(key.equals("username"))
                user.setUsername(request.get(key));
            else if(key.equals("age"))
                user.setAge(Integer.parseInt(request.get(key)));
            else if(key.equals("sex"))
                user.setSex(Integer.parseInt(request.get(key)));
            else if(key.equals("phone"))
                user.setPhone(request.get(key));
            else if(key.equals("sosphone"))
                user.setSosPhone(request.get(key));
        }
        userMapper.updateUserMsg(user);
        return null;
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
