package com.hotel.wx.controller;

import com.github.pagehelper.PageInfo;
import com.hotel.wx.pojo.Customer;
import com.hotel.wx.pojo.Order;
import com.hotel.wx.service.CustomerService;

import com.hotel.wx.service.OrderService;
import entity.PageDomain;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("findAll")
    public Result<PageInfo> findAll(@RequestBody Order order, @RequestParam int page, @RequestParam int limit){
        PageDomain pageDomain = new PageDomain();
        pageDomain.setLimit(limit);
        pageDomain.setPage(page);
        PageInfo<Order> pageInfo = orderService.page(order, pageDomain);

        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    @PostMapping("add")
    public Result addAll(@RequestBody Order order){
        orderService.addAll(order);
        return new Result(true, StatusCode.OK,"查询成功");
    }

    @PostMapping("findByOpenId")
    public Result<List<Order>> findByOpenId(@RequestBody Order order){
        List<Order> list ;
        if(order.getStatus() == null){
            list = orderService.findAll(order.getOpenId());
        }else {
            list = orderService.findList(order.getOpenId(),order.getStatus());

        }
        return new Result(true,StatusCode.OK,"查询成功",list);

    }

    @GetMapping("findList")
    public Result<List<Order>> findList(){
        List<Order> result = orderService.findAll();
        return new Result(true,StatusCode.OK,"查询成功",result);
    }

    @PostMapping("update")
    public Result update(@RequestBody Order order){
        orderService.update(order);
        return new Result(true,StatusCode.OK,"修改成功");
    }
}
