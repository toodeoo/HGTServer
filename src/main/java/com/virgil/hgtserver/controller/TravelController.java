package com.virgil.hgtserver.controller;

import com.virgil.hgtserver.pojo.Travel;
import com.virgil.hgtserver.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
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

    @PostMapping("/share")
    public String acceptShare(@RequestBody HashMap<String, String> request) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String encyData = request.get("encryptedData");
        String iv = request.get("iv");
        String token = request.get("token");
        return travelService.acceptShare(encyData, iv, token);
    }

    @GetMapping("/details")
    public String getTravelDetails(@RequestParam("travelId")int travelId){
        return travelService.getDetails(travelId);
    }

    @PostMapping("/uploadImg")
    public String uploadImg( @RequestPart("file")MultipartFile file, @RequestParam("token")String token,
                             @RequestParam("travelId")int travelId, @RequestParam("text")String text, @RequestParam("time")String time)
            throws IOException {
        String filename = "/root/img/" + LocalTime.now().toString() + file.getOriginalFilename();
        String filePath = "https://121.5.154.71" + filename;
        File f = new File(filename);
        file.transferTo(f);
        return travelService.uploadImg(token, travelId, time, filePath, text);
    }

    @GetMapping("/downloadImg")
    public String downloadImg(@RequestParam("token")String token, @RequestParam("travelId")int travelId, @RequestParam("time")String time){
        return travelService.downloadImg(token, travelId, time);
    }

    @GetMapping("/getWish")
    public String getWish(@RequestParam("travelId")int travelId){
        return travelService.getWish(travelId);
    }

    @PostMapping("/postWish")
    public String postWish(@RequestBody HashMap<String, String> request ){
        String token = request.get("token");
        String flag = request.get("class");
        String text = request.get("text");
        int travelId = Integer.parseInt(request.get("travelId"));
        return travelService.postWish(token, flag, text, travelId);
    }

}
