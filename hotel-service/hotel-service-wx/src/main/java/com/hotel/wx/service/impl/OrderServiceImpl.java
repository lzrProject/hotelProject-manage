package com.hotel.wx.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hotel.wx.dao.CustomerMapper;
import com.hotel.wx.dao.CustomerOrderMapper;
import com.hotel.wx.dao.OrderMapper;
import com.hotel.wx.pojo.*;
import com.hotel.wx.service.OrderService;
import entity.PageDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerOrderMapper customerOrderMapper;

    @Override
    public void addAll(Order order) {
        orderMapper.insertSelective(order);

        Example example = new Example(Customer.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("openId",order.getOpenId());
        List<Customer> customers = customerMapper.selectByExample(example);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setCustomerId(customers.get(0).getId());
        customerOrder.setOrderId(order.getId());
        customerOrderMapper.insertSelective(customerOrder);

    }

    @Override
    public void addAll(CustomerOrder customerOrder) {

    }

    @Override
    public void delById(Integer id) {

    }

    @Override
    public void delByHotelId(Integer id) {

    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }


    @Override
    public Order findById(int id) {
        return null;
    }

    @Override
    public List<Order> findList(String openId ,String status) {
        return customerOrderMapper.findByOpenId(openId,status);
    }

    @Override
    public List<Order> findAll(String openId) {
        return customerOrderMapper.findByOpenId1(openId);
    }

    @Override
    public List<Order> findAll() {
        return orderMapper.selectAll();
    }

    @Override
    public PageInfo<Order> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<Order> findPage(Order order, int page, int size) {
        return null;
    }

    @Override
    public PageInfo<Order> page(Order order, PageDomain pageDomain) {
        PageHelper.startPage(pageDomain.getPage(),pageDomain.getLimit());
        HashMap hashMap = new HashMap();
        if(order.getCode() != null){
            hashMap.put("code",order.getCode());
        }
        if(order.getOpenId() != null) {
            hashMap.put("openId", order.getOpenId());
        }

        List<Order> orders = orderMapper.findAll(hashMap);
        return new PageInfo(orders);
    }

    @Override
    public List<Order> toUserMenu(List<Order> sysMenus, Integer parentId) {
        return null;
    }


}
