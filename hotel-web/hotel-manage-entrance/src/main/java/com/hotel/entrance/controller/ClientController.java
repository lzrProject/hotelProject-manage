package com.hotel.entrance.controller;

import entity.Result;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/client")
public class ClientController {
    /**
     * 客户界面
     * */
    private String MODULE_PATH = "system/client/";

    /**
     * 客户信息
     * @return
     */
//    @GetMapping("/main")
//    public ModelAndView index(){
//        return new Result().jumpPage(MODULE_PATH+"main");
//    }
}
