package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.Travel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TravelMapper {
    void insertTravel( Travel travel );

    int queryMaxId();

    List<Travel> queryByToken(String token);

    int queryIsLeader(@Param("token")String token, @Param("travelId")int travelId);

    String queryWork( @Param("token")String token, @Param("travelId")int travelId );

    int isEqual(String activeId);

    Travel queryByActiveId(String activeId);

    void updateWorkByToken( @Param("token")String token, @Param("work")String work, @Param("travelId")int travelId );

    List<String> queryByTravelId( @Param("travelId")int travelId );

    String queryToken( @Param("travelId") int travelId , @Param("username") String username );

    int queryPeopleNumById( @Param("travelId") int id );

    String queryMaxIdWithToken( @Param("token") String token );
}
