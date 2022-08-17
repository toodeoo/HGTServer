package com.virgil.hgtserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.virgil.hgtserver.conf.RetCode;
import com.virgil.hgtserver.mappers.TravelMapper;
import com.virgil.hgtserver.mappers.WhisperMapper;
import com.virgil.hgtserver.pojo.Whisper;
import com.virgil.hgtserver.service.WhisperService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WhisperServiceImpl implements WhisperService {

    @Autowired
    private WhisperMapper whisperMapper;

    @Autowired
    private TravelMapper travelMapper;

    @Override
    public String writeWhisper( String text ,String person ) {
        int code = 0;
        Whisper whisper = new Whisper();
        whisper.setText(text);
        whisper.setPerson(person);
        whisper.setIsRead(1);
        int id = whisperMapper.queryMaxId() + 1;
        whisper.setId(id);
        whisper.setTime(LocalDate.now().toString());
        whisperMapper.insertWhisper(whisper);
        return JSONObject.toJSONString(new RetCode(code));
    }

    @Override
    public String hasNewMsg( String person ) {
        int code = 0;
        if(whisperMapper.queryNewMsg(person) > 0){
            code = 1;
        }
        return JSONObject.toJSONString(new RetCode(code));
    }

    @Override
    public String getHistoryMsg( String person ) {
        List<Whisper> list = whisperMapper.queryByPerson(person);
        whisperMapper.updateByPerson(person);
        int code = list.size() > 0 ? 1 : 0;
        List<retWhisper> retList = new ArrayList<>();
        for (Whisper whisper: list){
            retList.add(new retWhisper(whisper));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("whisper", retList);
        return jsonObject.toJSONString();
    }

    @Override
    public String getAllUser(String token) {
        int id = travelMapper.queryMaxIdWithToken(token);
        List<String> list = whisperMapper.queryAll(id);
        int code = list.size() > 0 ? 1 : 0;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        jsonObject.put("username", list);
        return jsonObject.toJSONString();
    }
}

@Data
class retWhisper{
    public retWhisper(Whisper whisper){
        this.text = whisper.getText();
        this.text = whisper.getTime();
    }
    private String text;
    private String time;
}
