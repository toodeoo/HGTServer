package com.virgil.hgtserver.pojo;


import lombok.Data;

@Data
public class Whisper {
    private String  text;
    private int id;
    private int isRead; // 1表示未读，0表示已读
    private String person;
    private String time;
}
