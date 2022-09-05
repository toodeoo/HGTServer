package com.virgil.hgtserver.service;

import com.virgil.hgtserver.pojo.Travel;

public interface TravelService {
    String createTravel( Travel travel );

    String getList(String token);

    String acceptShare(String token, String activeId);

    String getDetails(int travelId);

    String uploadImg( String token ,int travelId ,String time ,String filePath, String text );

    String downloadImg( String token ,int travelId ,String time );

    String getWish( int travelId );

    String postWish( String token ,String flag ,String text ,int travelId );
}
