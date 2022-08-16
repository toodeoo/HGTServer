package com.virgil.hgtserver.service;

public interface WhisperService {

    String writeWhisper(String text, String person);

    String hasNewMsg(String person);

    String getHistoryMsg(String person);

    String getAllUser();

}
