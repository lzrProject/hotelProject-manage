package com.hotel.wx.controller;

import com.hotel.wx.pojo.Customer;
import com.hotel.wx.service.CustomerService;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("customer")
@RestController
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("addOpenId")
    public Integer addOpenId(@RequestBody Customer customer){
        customerService.addAll(customer);
        return customer.getId();
    }

    @GetMapping("findByOpenId")
    public Result<List<Customer>> findByOpenId(@RequestParam String openId){
        List<Customer> byOpenId = customerService.findByOpenId(openId);
        return new Result(true, StatusCode.OK,"查找成功",byOpenId);
    }
}
