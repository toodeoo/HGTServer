package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.Travel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TravelMapper {
    int insertTravel( Travel travel );

    int queryMaxId();

    List<Travel> queryByToken(String token);

}
