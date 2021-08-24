package com.hotel.entrance.controller;

import entity.Result;
import io.swagger.annotations.Api;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/log")
public class LogController {

    /**
     * 日志详情
     * */
    private String MODULE_PATH = "system/log/";

    /**
     * 操作日志
     * @return
     */
//    @GetMapping("main")
//    @PreAuthorize("hasAuthority('sys:log:main')")
    public ModelAndView index() {
        return new Result().jumpPage(MODULE_PATH+"main");

    }
}
