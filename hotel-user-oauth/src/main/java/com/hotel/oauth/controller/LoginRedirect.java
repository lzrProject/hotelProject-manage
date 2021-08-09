package com.hotel.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/***
 * 登录页面跳转
 */
@Controller
@RequestMapping(value = "/oauth")
public class LoginRedirect {
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

}
