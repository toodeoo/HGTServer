package com.virgil.hgtserver.service;

import java.util.HashMap;

public interface UserService {
    String login( HashMap<String, String> request);
    String getUserMsg(String token);
    String modifyUserMsg(HashMap<String, String> request);
}
