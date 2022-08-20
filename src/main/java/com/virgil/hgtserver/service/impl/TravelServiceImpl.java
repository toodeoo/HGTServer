package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.RetCode;
import com.virgil.hgtserver.mappers.TravelDetailsMapper;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.mappers.WishMapper;
import com.virgil.hgtserver.pojo.Travel;
import com.virgil.hgtserver.pojo.TravelDetails;
import com.virgil.hgtserver.service.TravelService;
import com.virgil.hgtserver.utils.WeixinUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TravelServiceImpl implements TravelService {

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TravelDetailsMapper travelDetailsMapper;

    @Autowired
    private WishMapper wishMapper;

    @Override
    public String createTravel( Travel travel ) {
        int code = 1;
        int id = travelMapper.queryMaxId() + 1;
        travel.setId(id);
        travel.setIsLeader(1);
        travel.setDate(new Date());
        travel.setActiveId(WeixinUtils.getActiveId(travel.getToken()));
        travelMapper.insertTravel(travel);
        wishMapper.insertDefault("外卖！！！", 10, "eat", id);
        wishMapper.insertDefault("不出门，自己做", 10, "eat", id);
        wishMapper.insertDefault("不吃了", 10, "eat", id);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("activityId", travel.getActiveId());
        return jsonObject.toJSONString();
    }

    @Override
    public String getList( String token ) {
        List<Travel> list = travelMapper.queryByToken(token);
        int code = list.size() == 0 ? 0 : 1;
        List<TravelIntro> retList = new ArrayList<>();
        for(Travel travel: list){
            TravelIntro travelIntro = new TravelIntro(travel);
            travelIntro.setPeopleNum(travelMapper.queryPeopleNumById(travel.getId()));
            retList.add(travelIntro);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("travelIntro", retList);
        jsonObject.put("username", userMapper.queryByToken(token).getUsername());
        return jsonObject.toJSONString();
    }

    @Override
    public String acceptShare( String encyData ,String iv ,String token ) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        int code = 0;
        String activeId = WeixinUtils.decode(encyData ,iv ,userMapper.querySessionByToken(token));
        if(travelMapper.isEqual(activeId) > 0){
            Travel origin = travelMapper.queryByActiveId(activeId);
            Travel travel = new Travel(origin);
            travel.setToken(token);
            travelMapper.insertTravel(travel);
            code = 1;

        }
        return JSONObject.toJSONString(new RetCode(code));
    }

    @Override
    public String getDetails( int travelId ) {
        TravelDetails travelDetails = travelDetailsMapper.queryCertainTravel(travelId);
        if(travelDetails != null) {
            return JSONObject.toJSONString(travelDetails);
        }
        else {
            return JSONObject.toJSONString(new RetCode(-1));
        }
    }

    @Override
    public String uploadImg( String token ,int travelId ,String time ,String filePath ) {
        travelMapper.insertImg(token, travelId, time, filePath);
        return JSONObject.toJSONString(new RetCode(1));
    }

    @Override
    public String downloadImg( String token ,int travelId ,String time ) {
        String filePath = travelMapper.queryImgPath(token, travelId, time);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("filePath", filePath);
        return jsonObject.toJSONString();
    }
}

@Data
class TravelIntro{
    public TravelIntro(Travel travel){
        this.travelId = travel.getId();
        this.place = travel.getPlace();
        this.theme = travel.getTheme();
    }
    private int travelId;
    private String place;
    private String theme;
    private int peopleNum;
}
