package com.hotel.index.service;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.Menu;

import java.util.List;

public interface IndexService {
    //添加数据
    void addAll(Menu menu);

    //删除数据
    void delById(Integer id);

    //修改数据
    void update(Menu menu);

    //根据id查询
    Menu findById(int id);

    //根据条件查询
    List<Menu> findList(Menu menu);

    //查询所有数据
    List<Menu> findAll();

    //分页查询
    PageInfo<Menu> findPage(int page, int size);

    //分页+条件查询
    PageInfo<Menu> findPage(Menu menu,int page,int size);

    public List<Menu> toUserMenu(List<Menu> sysMenus,Integer parentId);
}
