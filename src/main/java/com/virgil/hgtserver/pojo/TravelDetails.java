package com.virgil.hgtserver.pojo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TravelDetails {
    private String breakfast;
    private String morning;
    private String lunch;
    private String afternoon;
    private String dinner;
    private LocalDate date;
    private int travelId;
}
