package com.virgil.hgtserver.mappers;

import com.virgil.hgtserver.pojo.Whisper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface WhisperMapper {
    void insertWhisper( Whisper whisper );

    int queryMaxId();

    int queryNewMsg( String person );

    List<Whisper> queryByPerson(String person);

    void updateByPerson(String person);

    List<String> queryAll();

}
