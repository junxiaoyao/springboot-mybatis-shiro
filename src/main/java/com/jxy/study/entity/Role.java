package com.jxy.study.entity;

import lombok.Data;

@Data
public class Role{
    private Integer id;
    private String name;
    private Integer roleLevel;
    private String description;
}
