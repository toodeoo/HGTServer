package com.virgil.hgtserver.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class TravelDetails {
    private String breakfast;
    private String morning;
    private String lunch;
    private String afternoon;
    private String dinner;
    private Date date;
    private int travelId;
}
