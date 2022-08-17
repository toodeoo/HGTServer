package com.virgil.hgtserver.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Vote {
    private String text;
    private String token;
    private Date startTime;
    private Date endTime;
    private int isEnd;
    private int code;
}
