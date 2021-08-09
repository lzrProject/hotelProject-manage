package com.hotel.wx.service;

import com.hotel.wx.pojo.Customer;

import java.util.List;

public interface CustomerService {
    //添加openid
    void addAll(Customer customer);

    //根据openid查找
    List<Customer> findByOpenId(String openId);
}
