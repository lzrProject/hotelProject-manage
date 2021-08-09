package com.hotel.user.service;

import com.github.pagehelper.PageInfo;
import com.hotel.user.pojo.User;
import com.hotel.user.pojo.UserRole;

import java.util.List;

public interface UserRoleService {
    //添加数据
    void addAll(UserRole userRole);

    //批量添加
    int batchInsert(List<UserRole> userRoles);

    //删除数据
    void delById(Integer id);

    //删除数据
    void delByUserId(Integer userId);

    //修改数据
    void update(UserRole userRole);

    //根据id查询
    User findById(Integer id);

    List<UserRole> findByUserId(Integer id);

    //根据条件查询
    List<UserRole> findList(UserRole userRole);

    //查询所有数据
    List<UserRole> findAll();

    //分页查询
    PageInfo<UserRole> findPage(int page, int size);

    //分页+条件查询
    PageInfo<UserRole> findPage(UserRole userRole,int page,int size);
}
