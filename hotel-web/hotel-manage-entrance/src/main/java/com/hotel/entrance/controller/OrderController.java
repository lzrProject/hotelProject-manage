package com.hotel.entrance.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.hotel.user.pojo.User;
import com.hotel.wx.feign.OrderFeign;
import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.Order;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderFeign orderFeign;

    /**
     * 订单界面
     * */
    private String MODULE_PATH = "system/order/";

    /**
     * 订单信息
     * @return
     */
    @RequestMapping("main")
    public ModelAndView index(){
        return new Result().jumpPage(MODULE_PATH+"main");
    }

    @GetMapping("data")
    public JSONObject data(Order order, @RequestParam int page, @RequestParam int limit) {
        Result<PageInfo> result = orderFeign.findAll(order, page, limit);
        if(result.isFlag()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message",result.getMessage());
            jsonObject.put("count",result.getData().getTotal());
            jsonObject.put("data",result.getData().getList());
            return jsonObject;
        }
        return null;
    }
}
