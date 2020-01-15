package com.jxy.study.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.jxy.study.dao.NoteMapper;
import com.jxy.study.entity.NoteExample;
import com.jxy.study.entity.NoteExample.Criteria;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author: jxy
 * @create: 2019-06-11 14:58
 */
@RestController
@RequestMapping("rest")
public class TestController {

    @Autowired
    private NoteMapper noteMapper;

    @RequestMapping("getTime")
    public String getTime() {
        NoteExample example=new NoteExample();
//Criterion criterion=example.createCriteria();
        Criteria criteria=example.createCriteria();
        Criteria criteria2=example.or();
        List<Integer> list=new ArrayList<>();
        list.add(1);
        criteria.andIdIn(list);
        criteria.andNameLike("1");
        list.add(2);
        criteria2.andIdIn(list);
        noteMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.OK.value());
        map.put("data", Calendar.getInstance().getTime().toString());
        return JSONUtils.toJSONString(map);
    }
}
