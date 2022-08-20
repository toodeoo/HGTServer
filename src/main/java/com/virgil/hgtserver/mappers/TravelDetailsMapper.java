package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.TravelDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TravelDetailsMapper {

    TravelDetails queryCertainTravel( @Param("travelId")int travelId);

    void insertNewDetail( TravelDetails travelDetails );
}
