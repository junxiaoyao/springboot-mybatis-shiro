package com.jxy.study.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
//@Grab("thymeleaf-spring4")
@Controller
@RequestMapping("/grovy")
class HGrovy {
    @RequestMapping(method = RequestMethod.GET)
    String home(){
        "login"
    }
}
