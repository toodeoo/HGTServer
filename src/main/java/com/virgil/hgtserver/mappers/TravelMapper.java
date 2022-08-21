package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.SummaryWish;
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

    void insertImg(@Param("token") String token , @Param("travelId") int travelId , @Param("time") String time , @Param("filePath") String filePath );

    String queryImgPath( @Param("token") String token , @Param("travelId") int travelId , @Param("time") String time );

    List<SummaryWish> queryAllById( @Param("travelId") int travelId );

    void insertWish( @Param("token") String token , @Param("class") String flag , @Param("text") String text , @Param("travelId") int travelId );
}
