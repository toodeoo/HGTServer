package com.virgil.hgtserver.controller;

import com.virgil.hgtserver.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hangout/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public String getMemberList( @RequestParam("travelId")int travelId ){
        return memberService.getMemberList(travelId);
    }

    @GetMapping("/details")
    public String getMemberDetails(@RequestParam("username")String username, @RequestParam("travelId")int travelId){
        return memberService.getMemberDetails(travelId, username);
    }

}
