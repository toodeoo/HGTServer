package com.virgil.hgtserver.controller;


import com.virgil.hgtserver.service.WhisperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/hangout/whisper")
public class WhisperController {

    @Autowired
    private WhisperService whisperService;

    @PostMapping("/write")
    public String writeWhisper( @RequestBody HashMap<String, String> request ){
        String text = request.get("text");
        String person = request.get("person");
        return whisperService.writeWhisper(text, person);
    }


    @GetMapping("/newMsg")
    public String hasNewMsg(@RequestParam("username")String username){
        return whisperService.hasNewMsg(username);
    }

    @GetMapping("/historyMsg")
    public String getHistoryMsg(@RequestParam("username")String username){
        return whisperService.getHistoryMsg(username);
    }

    @GetMapping("/allUser")
    public String getAllUser(@RequestParam("token")String token){
        return whisperService.getAllUser(token);
    }

}
