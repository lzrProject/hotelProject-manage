package com.hotel.entrance.controller;

import entity.Result;
import entity.TokenDecode;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Describe: 错误处理类
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @GetMapping(value = "/403")
    public ModelAndView error(){
        return new Result().jumpPage("error/403");
    }
}
