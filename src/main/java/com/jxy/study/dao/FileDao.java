package com.jxy.study.dao;

import com.jxy.study.entity.FileUpload;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @description
 * @author: jxy
 * @create: 2019-09-30 16:04
 */

@Mapper
@Repository
public interface FileDao {

    int uploadFile(FileUpload fileUpload);

    FileUpload getByName(String fileName);
}
