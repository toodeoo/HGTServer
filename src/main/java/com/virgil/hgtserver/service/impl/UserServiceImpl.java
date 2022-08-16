package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
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
            try {
                if(!request.get("encryptedData").equals("") && !request.get("iv").equals(""))
                    user.setPhone(WeixinUtils.getPhone(request.get("encryptedData") ,request.get("iv") ,response.get("session_key")));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            if(userMapper.isExist(user.getToken()) == 0)
                userMapper.insertUser(user);
            loginMsg.setToken(user.getToken());
            loginMsg.setErrcode("0");
        }
        return JSONObject.toJSONString(loginMsg);
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
            switch (key) {
                case "username":
                    user.setUsername(request.get(key));
                    break;
                case "age":
                    user.setAge(Integer.parseInt(request.get(key)));
                    break;
                case "sex":
                    user.setSex(Integer.parseInt(request.get(key)));
                    break;
                case "phone":
                    user.setPhone(request.get(key));
                    break;
                case "sosphone":
                    user.setSosPhone(request.get(key));
                    break;
            }
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
