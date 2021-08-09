package com.hotel.index.service;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Menu;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    //添加数据
    void addAll(File file);

    //删除数据
    void delById(Integer id);

    //修改数据
    void update(File file);

    //根据id查询
    File findById(Integer id);

    //根据fileName查询
    List<File> findByName(File file);

    //根据条件查询
    List<File> findList(File file);

    //查询所有数据
    List<File> findAll();

    //分页查询
    PageInfo<File> findPage(int page, int size);

    //分页+条件查询
    PageInfo<File> findPage(File file,int page,int size);
}
