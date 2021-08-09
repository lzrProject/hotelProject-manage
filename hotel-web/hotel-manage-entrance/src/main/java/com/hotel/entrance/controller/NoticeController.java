package com.hotel.entrance.controller;

import entity.Result;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Describe: 消息控制器
 */
@RestController
@RequestMapping("/system/notice")
public class NoticeController {

    private String prefix = "system/notice";

    @GetMapping("/main")
    public ModelAndView main()
    {
        return new Result().jumpPage(prefix + "/main");
    }
}
