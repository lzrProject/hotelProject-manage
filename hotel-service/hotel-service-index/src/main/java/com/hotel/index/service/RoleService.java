package com.hotel.index.service;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.Power;
import com.hotel.index.pojo.Role;

import java.util.List;

public interface RoleService {
    //添加数据
    void addAll(Role role);

    //删除数据
    void delById(Integer id);

    void batchRemove(List<Integer> ids);

    //修改数据
    void update(Role role);

    //根据id查询
    Role findById(Integer id);

    //根据条件查询
    List<Role> findList(Role role);

    public List<Role> list(Role role);

    //查询所有数据
    List<Role> findAll();

    //分页查询
    PageInfo<Role> findPage(int page, int size);

    //分页+条件查询
    PageInfo<Role> findPage(Role role,int page,int size);
}
