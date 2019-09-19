package com.jxy.study.controller;

import com.jxy.study.entity.Role;
import com.jxy.study.entity.User;
import com.jxy.study.service.OneTestProxyService;
import com.jxy.study.service.RoleService;
import com.jxy.study.service.UserService;
import java.nio.file.attribute.UserPrincipal;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: ybl
 * @Date: 2018/12/14 0014 11:07
 * @Description:
 */
@Controller
public class LoginController {

    @Value("${project.mainPage}")
    private String mainPage;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OneTestProxyService oneTestProxyService;

    @RequestMapping(value = "getRoleById", method = RequestMethod.GET)
    @ResponseBody
    public Role getRoleById(Integer id) {
        return roleService.getRole(id);
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(Model model) {
        model.addAttribute("msg", "退出成功");
        return "login";
    }

    @RequestMapping(value = "mainPage", method = RequestMethod.GET)
    public String mainPage(Model model) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("user", userName);
        return mainPage;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String loginPost(HttpServletRequest request, String name, String password, Model model) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        // 3.执行登录方法
        try {
            subject.login(token);
            String url = "/mainPage";
            // 获取未登录之前地址
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            // 如果未登录之前有地址，跳转至原地址
            if (savedRequest != null) {
                url = savedRequest.getRequestUrl();
            }
            // 登录成功,进入主页
            return "redirect:" + url;
        } catch (UnknownAccountException e) {
            // 登录失败:用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            // 登录失败:密码错误
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }
}
