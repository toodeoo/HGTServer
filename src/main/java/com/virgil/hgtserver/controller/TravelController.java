package com.virgil.hgtserver.controller;

import com.virgil.hgtserver.pojo.Travel;
import com.virgil.hgtserver.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/hangout/travel")
public class TravelController {

    @Autowired
    TravelService travelService;

    @PostMapping("/create")
    public String createTravel( @RequestBody HashMap<String, String> request ){
        Travel travel = new Travel(request.get("theme"), request.get("place"), request.get("token"));
        return travelService.createTravel(travel);
    }

    @GetMapping("/list")
    public String getListInfo(@RequestParam("token")String token){
        return travelService.getList(token);
    }

}
