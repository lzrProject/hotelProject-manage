package com.hotel.user.service;

import com.github.pagehelper.PageInfo;
import com.hotel.user.pojo.User;

import java.util.List;

public interface UserService {
    //添加数据
    void addAll(User user);

    //删除数据
    void delById(Integer id);

    //修改数据
    void update(User user);

    //根据id查询
    User findById(Integer id);

    List<User> findByUsername(String username);

    //根据条件查询
    List<User> findList(User user);

    //查询所有数据
    List<User> findAll();

    //分页查询
    PageInfo<User> findPage(int page, int size);

    //分页+条件查询
    PageInfo<User> findPage(User user,int page,int size);
}
