package com.hotel.index.service;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.Menu;
import com.hotel.index.pojo.Power;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PowerService {
    //添加数据
    void addAll(Power power);

    //删除数据
    void delById(Integer id);


    //修改数据
    void update(Power power);

    //根据id查询
    Power findById(Integer id);

    //根据parentId查询
    List<Power> findByParentId(Integer pid);

    //根据条件查询
    List<Power> findList(Power power);

    public List<Power> list(Power power);

    //查询所有数据
    List<Power> findAll();

    //分页查询
    PageInfo<Power> findPage(int page, int size);

    //分页+条件查询
    PageInfo<Power> findPage(Power power,int page,int size);
}
