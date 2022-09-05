package com.virgil.hgtserver.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.virgil.hgtserver.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/hangout/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @GetMapping("/getWish")
    public String getWish( @RequestParam("travelId")int travelId, @RequestParam("class")String flag, @RequestParam("token")String token ){
        return wishService.getWish(travelId, flag, token);
    }

    @PostMapping("/postWish")
    public String postWish( @RequestBody HashMap<String, String> request ){
        String flag = request.get("class");
        String wish = request.get("wish");
        String token = request.get("token");
        int travelId = Integer.parseInt(request.get("travelId"));
        return wishService.addNewWish(wish, flag, token, travelId);
    }

    @PostMapping("/endVote")
    public String endVote(@RequestBody HashMap<String, Object> request) throws JsonProcessingException {
        String token = (String) request.get("token");
        int travelId = (Integer) request.get("travelId");
        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) request.get("item");
        return wishService.endVote(token, list, travelId);
    }

}
