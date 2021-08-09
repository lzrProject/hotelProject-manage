package com.hotel.wx.service;

import com.github.pagehelper.PageInfo;
import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.HotelFile;

import java.util.List;

public interface HotelService {
    //添加数据
    void addAll(Hotel hotel);

    //添加数据
    void addAll(HotelFile hotelFile);

    //删除数据
    void delById(Integer id);

    //删除数据
    void delByHotelId(Integer id);

    //修改数据
    void update(Hotel hotel);

    //根据id查询
    Hotel findById(int id);

    //根据parentId查询
    List<Hotel> findByParentId(int parentId);

    //根据条件查询
    List<Hotel> findList(Hotel hotel);

    //查询所有数据
    List<Hotel> findAll();

    //分页查询
    PageInfo<Hotel> findPage(int page, int size);

    //分页+条件查询
    PageInfo<Hotel> findPage(Hotel hotel,int page,int size);

    public List<Hotel> toUserMenu(List<Hotel> sysMenus,Integer parentId);
}
