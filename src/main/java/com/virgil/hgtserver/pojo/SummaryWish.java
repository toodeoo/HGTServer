package com.virgil.hgtserver.pojo;

import lombok.Data;

@Data
public class SummaryWish {
    private String token;
    private String text;
    private String flag;
    private int travelId;
}
