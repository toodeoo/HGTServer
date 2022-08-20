package com.virgil.hgtserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.List;

public interface WishService {
    String getWish( int travelId ,String flag, String token );

    String addNewWish( String wish ,String flag ,String token ,int travelId );

    String endVote( String token ,List<HashMap<String, Object>> list ,int isLeader, int travelId ) throws JsonProcessingException;
}
