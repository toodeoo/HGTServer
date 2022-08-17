package com.virgil.hgtserver.service;

import com.virgil.hgtserver.pojo.Travel;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface TravelService {
    String createTravel( Travel travel );

    String getList(String token);

    String acceptShare(String encyData, String iv, String token) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    String getDetails(int travelId);

}
