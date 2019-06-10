package com.jxy.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
@Controller
@RequestMapping("webSocket")
public class WebSocketController extends SpringBeanAutowiringSupport {
    @RequestMapping(method = RequestMethod.GET)
    public String index(){
        return "websocket";
    }
}
