package com.virgil.hgtserver.controller;

import com.virgil.hgtserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/hangout/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String userLogin( @RequestBody HashMap<String, String> request){
        /*System.out.println("Request Success");*/
        return userService.login(request);
    }

    @GetMapping("/msg")
    public String getUserMsg(@RequestParam("token") String token){
        return userService.getUserMsg(token);
    }

    @PostMapping("/modifyMsg")
    public String modifyUserMsg(@RequestBody HashMap<String, String> request){
        return userService.modifyUserMsg(request);
    }

}
