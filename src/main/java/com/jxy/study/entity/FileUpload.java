package com.jxy.study.entity;

import lombok.Data;

/**
 * @description
 * @author: jxy
 * @create: 2019-09-30 16:05
 */
@Data
public class FileUpload {

    private Integer id;

    private String fileName;

    private String fileType;

    private byte[] data;
}
