package com.virgil.hgtserver.service;

import com.virgil.hgtserver.pojo.Travel;

public interface TravelService {
    String createTravel( Travel travel );

    String getList(String token);

}
