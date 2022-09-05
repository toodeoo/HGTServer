package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.conf.SummaryWish;
import com.virgil.hgtserver.pojo.Wish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishMapper {
    List<Wish> queryWish( @Param("travelId") int travelId , @Param("class") String flag );

    void insertWish( Wish wish );

    int queryDouzi( @Param("travelId") int travelId, @Param("wish")String wish, @Param("class")String flag );

    void updateDouzi( @Param("token") String token , @Param("travelId") int travelId , @Param("douzi") int douzi,
                      @Param("wish")String wish, @Param("class")String flag );

    void updateIsEnd( @Param("token") String token , @Param("travelId") int travelId );

    String queryIsEnd( @Param("token") String token , @Param("travelId") int travelId );

    void updateAllIsEnd( @Param("travelId") int travelId );

    List<SummaryWish> querySummary( @Param("travelId") int travelId );

    void insertDefault( @Param("wish") String wish , @Param("douzi") int douzi , @Param("class") String flag, @Param("travelId")int id);

    List<Wish> queryWishById( @Param("travelId") int travelId );

    void insertToSummary( @Param("wish") String wish , @Param("class") String flag ,@Param("travelId") int travelId , @Param("douzi") int i );

    String queryInSummary( @Param("wish") String wish , @Param("class") String flag ,@Param("travelId") int travelId);
}
