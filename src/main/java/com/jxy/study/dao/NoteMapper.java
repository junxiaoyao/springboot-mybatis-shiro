package com.jxy.study.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jxy.study.entity.Note;
import com.jxy.study.entity.NoteExample;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface NoteMapper {
    int countByExample(NoteExample example);

    int deleteByExample(NoteExample example);

    int insert(Note record);

    int insertSelective(Note record);

    List<Note> selectByExample(NoteExample example);

    int updateByExampleSelective(@Param("record") Note record,
        @Param("example") NoteExample example);

    int updateByExample(@Param("record") Note record, @Param("example") NoteExample example);
}