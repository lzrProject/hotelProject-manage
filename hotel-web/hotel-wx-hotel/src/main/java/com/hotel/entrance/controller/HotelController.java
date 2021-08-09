package com.hotel.entrance.controller;

import com.hotel.wx.feign.CustomerFeign;
import com.hotel.wx.feign.HotelFeign;
import com.hotel.wx.feign.OrderFeign;
import com.hotel.wx.pojo.Customer;
import com.hotel.wx.pojo.CustomerOrder;
import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.Order;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("hotel")
public class HotelController {
    @Autowired
    private HotelFeign hotelFeign;

    @Autowired
    private OrderFeign orderFeign;

    @Autowired
    private CustomerFeign customerFeign;


    @GetMapping("list")
    public Object data(@RequestParam int parentId){
        Result result = hotelFeign.findByParentId(parentId);
        if(result.isFlag()){
            return result.getData();
        }
        return null;
    }

    @PostMapping("listByOne")
    public Object data(@RequestBody Hotel hotel){
        return null;
    }

    @PostMapping("pay")
    public Object payList(@RequestBody Order order){
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        String format = ft.format(date);
        String str = format + (int)Math.random()*3;

        order.setCode(str);
        order.setStatus("1");

        Result result = orderFeign.addAll(order);
//        System.out.println(order);
        return result.getData();
    }

    @PostMapping("findOrder")
    public Object findOrder(@RequestBody Order order){
        Result<List<Order>> results = orderFeign.findByOpenId(order);
        if(results.isFlag()){
            for(Order result : results.getData()){
                Result<Hotel> byId = hotelFeign.findById(result.getHotelId());
                result.setImageUrl(byId.getData().getImageUrl());
            }
            return results.getData();
        }
        return null;
    }


    @GetMapping("cancel")
    public Result cancel(@RequestParam Integer id){
        Order order = new Order();
        order.setStatus("0");
        order.setId(id);
        Result result = orderFeign.update(order);
        return result;
    }

}
