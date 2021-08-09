package com.hotel.wx.service;

import com.github.pagehelper.PageInfo;
import com.hotel.wx.pojo.CustomerOrder;
import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.HotelFile;
import com.hotel.wx.pojo.Order;
import entity.PageDomain;

import java.util.List;

public interface OrderService {
    //添加数据
    void addAll(Order order);

    //添加数据
    void addAll(CustomerOrder customerOrder);

    //删除数据
    void delById(Integer id);

    //删除数据
    void delByHotelId(Integer id);

    //修改数据
    void update(Order order);


    //根据id查询
    Order findById(int id);

    //根据条件查询
    List<Order> findList(String openId,String status);

    //查询所有数据
    List<Order> findAll(String openId);

    //查询所有数据
    List<Order> findAll();

    //分页查询
    PageInfo<Order> findPage(int page, int size);

    //分页+条件查询
    PageInfo<Order> findPage(Order order,int page,int size);

    PageInfo<Order> page(Order order, PageDomain pageDomain);

    List<Order> toUserMenu(List<Order> sysMenus,Integer parentId);
}
