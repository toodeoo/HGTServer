package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.AsyncConf;
import com.virgil.hgtserver.conf.RetCode;
import com.virgil.hgtserver.conf.SummaryWish;
import com.virgil.hgtserver.mappers.TravelDetailsMapper;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.UserMapper;
import com.virgil.hgtserver.mappers.WishMapper;
import com.virgil.hgtserver.pojo.Wish;
import com.virgil.hgtserver.service.WishService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    private WishMapper wishMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TravelDetailsMapper travelDetailsMapper;

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private AsyncConf asyncConf;

    @Override
    public String getWish( int travelId ,String flag , String token ) {
        if(wishMapper.queryIsEnd(token, travelId) == null || wishMapper.queryIsEnd(token, travelId).equals("0")){
            return JSONObject.toJSONString(new RetCode(-1));
        }
        List<Wish> wishList = wishMapper.queryWish(travelId, flag);
        if (wishList == null){
            return JSONObject.toJSONString(new RetCode(-1));
        }
        else{
            List<RetWish> list = new ArrayList<>();
            HashMap<String, List<String>> hashMap = new HashMap<>();
            for(Wish wish: wishList){
                if(hashMap.containsKey(wish.getWish())){
                    hashMap.get(wish.getWish()).add(userMapper.queryAvatarByToken(wish.getToken()));
                }
                else {
                    List<String> avatarList = new ArrayList<>();
                    avatarList.add(userMapper.queryAvatarByToken(wish.getToken()));
                    hashMap.put(wish.getWish(), avatarList);
                }
            }
            for(String wish: hashMap.keySet()){
                RetWish retWish = new RetWish();
                retWish.setWish(wish);
                retWish.setAvatar(hashMap.get(wish));
                list.add(retWish);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("wishList", list);
            return jsonObject.toJSONString();
        }
    }

    @Override
    public String addNewWish( String wish ,String flag ,String token , int travelId ) {
        Wish w = new Wish(wish, travelId, token, flag,0 ,  1);
        wishMapper.insertWish(w);
        if(wishMapper.queryInSummary(wish, flag, travelId) == null)
            wishMapper.insertToSummary(wish, flag, travelId, 0);
        return JSONObject.toJSONString(new RetCode(1));
    }

    @Override
    public String endVote( String token , List<HashMap<String, Object>> list , int travelId ){
        int isLeader = travelMapper.queryIsLeader(token, travelId);
        for(HashMap<String, Object> item: list){
            List<ReqWish> reqWishes = JSONObject.parseArray(JSONArray.toJSONString(item.get("wishList")), ReqWish.class);
            String flag = (String) item.get("class");
            for(ReqWish reqWish: reqWishes){
                System.out.println(reqWish);
                int num = wishMapper.queryDouzi(travelId, reqWish.getWish(), flag);
                wishMapper.updateDouzi(token, travelId, reqWish.getDouzi() + num, reqWish.getWish(), flag);
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        if(isLeader != 1)
            wishMapper.updateIsEnd(token, travelId);
        else {
            wishMapper.updateAllIsEnd(travelId);
            List<SummaryWish> summaryWishList = wishMapper.querySummary(travelId);
            Collections.sort(summaryWishList);
            Map<String, List<SummaryWish>> map = summaryWishList.stream().collect(Collectors.groupingBy(SummaryWish::getFlag));
            jsonObject.put("wishList", map);
            jsonObject.put("memberNum", travelMapper.queryPeopleNumById(travelId));
            asyncConf.updateDetails(map, travelId, travelDetailsMapper);
        }
        return jsonObject.toJSONString();
    }


}

@Data
class RetWish{

    public RetWish(){};

    private String wish;
    private List<String> avatar;

}

@Data
class ReqWish{
    public ReqWish(){};

    private String wish;
    private int douzi;

}