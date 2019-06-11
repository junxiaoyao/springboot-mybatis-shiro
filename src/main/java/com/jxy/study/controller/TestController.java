package com.jxy.study.controller;

import java.util.Calendar;
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
  @RequestMapping("getTime")
  public String getTime() {
    return Calendar.getInstance().getTime().toString();
  }
}
