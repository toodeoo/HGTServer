package com.virgil.hgtserver.controller;

import com.virgil.hgtserver.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/hangout/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping("/hasVote")
    public String hasVote( @RequestParam("token") String token ){
        return voteService.hasVote(token);
    }

    @PostMapping("/createVote")
    public String createVote( @RequestBody HashMap<String, Object> request ){
        String token = (String) request.get("token");
        List<String> list = (List<String>) request.get("vote");
        System.out.println(list.toString());
        return voteService.createVote(token, list);
    }

    @GetMapping("/getVote")
    public String getVote(@RequestParam("token")String token){
        return voteService.getVote(token);
    }


    @PostMapping("/vote")
    public String vote(@RequestBody HashMap<String, Object> request){
        String token = (String) request.get("token");
        List<String> list = (List<String>) request.get("vote");
        return voteService.vote(token, list);
    }
}
