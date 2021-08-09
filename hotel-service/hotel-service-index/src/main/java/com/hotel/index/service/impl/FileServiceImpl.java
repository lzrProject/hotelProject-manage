package com.hotel.index.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hotel.index.dao.FileMapper;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Role;
import com.hotel.index.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Override
    public void addAll(File file) {
        fileMapper.insertSelective(file);
    }

    @Override
    public void delById(Integer id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(File file) {

    }

    @Override
    public File findById(Integer id) {
        return null;
    }

    @Override
    public List<File> findByName(File file) {
        Example example = createExample(file);

        List<File> files = fileMapper.selectByExample(example);
        return files;
    }

    @Override
    public List<File> findList(File file) {
        return null;
    }

    @Override
    public List<File> findAll() {
        return null;
    }

    @Override
    public PageInfo<File> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<File> findPage(File file, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(file);
        List<File> list = fileMapper.selectByExample(example);
        return new PageInfo(list);
    }

    public Example createExample(File file){
        Example example = new Example(File.class);    //自定义条件查询
        Example.Criteria criteria = example.createCriteria();//条件构造器

        if(file != null){
            if(!StringUtils.isEmpty(file.getId())){
                criteria.andEqualTo("id",file.getId());
            }

            if(!StringUtils.isEmpty(file.getFileName())){
                criteria.andLike("fileName","%"+file.getFileName()+"%");
            }

            if(!StringUtils.isEmpty(file.getFileDesc())){
                criteria.andLike("fileDesc","%"+file.getFileDesc()+"%");
            }

            if(!StringUtils.isEmpty(file.getFilePath())){
                criteria.andLike("filePath","%"+file.getFilePath()+"%");
            }
        }
        return example;
    }
}
