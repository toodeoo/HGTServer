package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.Whisper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface WhisperMapper {
    void insertWhisper( Whisper whisper );

    String queryMaxId();

    String queryNewMsg( String person );

    List<Whisper> queryByPerson(String person);

    void updateByPerson(String person);

    List<String> queryAll( @Param ("travelId")int travelId );

}
