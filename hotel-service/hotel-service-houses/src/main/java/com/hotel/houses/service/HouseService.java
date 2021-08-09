package com.hotel.houses.service;

import com.github.pagehelper.PageInfo;
import com.hotel.houses.pojo.Houses;

import java.util.List;

public interface HouseService {
    //添加数据
    void addAll(Houses houses);

    //删除数据
    void delById(Integer id);

    //修改数据
    void update(Houses houses);

    //根据id查询
    Houses findById(int id);

    //根据条件查询
    List<Houses> findList(Houses houses);

    //查询所有数据
    List<Houses> findAll();

    //分页查询
    PageInfo<Houses> findPage(int page,int size);

    //分页+条件查询
    PageInfo<Houses> findPage(Houses houses,int page,int size);
}
