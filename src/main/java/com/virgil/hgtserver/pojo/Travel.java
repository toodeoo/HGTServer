package com.virgil.hgtserver.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Travel {
    public Travel(){}
    public Travel(String theme, String place, String token){
        this.theme = theme;
        this.place = place;
        this.token = token;
    }
    public Travel(Travel travel){
        this.theme = travel.getTheme();
        this.place = travel.getPlace();
        this.activeId = travel.getActiveId();
        this.date = travel.getDate();
        this.id = travel.getId();
        this.isLeader = 0;
    }
    private String theme;
    private String place;
    private int id;
    private String work;
    private int isLeader;
    private String token;
    private Date date;
    private String activeId;

}
