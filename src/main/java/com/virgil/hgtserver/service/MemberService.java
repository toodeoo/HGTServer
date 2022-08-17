package com.virgil.hgtserver.service;

public interface MemberService {

    String getMemberList(int travelId);

    String getMemberDetails(int travelId, String username);

}
