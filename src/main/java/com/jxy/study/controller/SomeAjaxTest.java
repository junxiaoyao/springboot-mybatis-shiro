package com.jxy.study.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description
 * @author: jxy
 * @create: 2019-10-09 10:31
 */
@RestController
@RequestMapping("/ajax")
public class SomeAjaxTest {

    @GetMapping("getTime")
    public Object getTime(HttpServletRequest request) {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        map.put("data", new Date(System.currentTimeMillis()).toString());
        map.put("code", HttpStatus.OK.value());
        return map;
    }
}
