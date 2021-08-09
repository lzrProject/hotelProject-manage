package com.hotel.index.service;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.RolePower;

import java.util.List;

public interface RolePowerService {
    //添加数据
    void addAll(RolePower rolePower);

    //批量添加
    int batchInsert(List<RolePower> rolePowers);

    //删除数据
    void delById(Integer id);

    //删除roleId
    void delByRoleId(Integer roleId);

    //删除roleId
    void delByPowerId(Integer powerId);

    //修改数据
    void update(RolePower rolePower);

    //根据id查询
    RolePower findById(Integer id);

    //查询权限id
    List<RolePower> findPowerId(Integer userId);



    //根据条件查询
    List<RolePower> findList(RolePower rolePower);

    public List<RolePower> list(Integer id);

    //查询所有数据
    List<RolePower> findAll();

    //分页查询
    PageInfo<RolePower> findPage(int page, int size);

    //分页+条件查询
    PageInfo<RolePower> findPage(RolePower rolePower,int page,int size);
}
