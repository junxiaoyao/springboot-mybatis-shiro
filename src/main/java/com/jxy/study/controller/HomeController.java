package com.jxy.study.controller;

import com.jxy.study.config.MyFilterMap;
import com.jxy.study.entity.Role;
import com.jxy.study.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: ybl
 * @Date: 2018/12/14 0014 11:07
 * @Description:
 */
@Controller
@RequestMapping("/")
public class HomeController {

  @Autowired
  private RoleService roleService;

  @Autowired
  private MyFilterMap filterMap;

  private int size = 50;

//    @Autowired
//    private JdbcTemplate jdbcTemplate;

  @RequestMapping(value = "getRoleById",method = RequestMethod.GET)
  @ResponseBody
  public Role getRoleById(Long id) {
    return roleService.getRole(id);
  }
}
