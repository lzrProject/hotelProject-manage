package com.hotel.file.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hotel.file.Dao.FileMapper;
import com.hotel.file.Dao.HotelFileMapper;
import com.hotel.file.service.FileService;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.HotelFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private HotelFileMapper hotelFileMapper;

    @Override
    public void addAll(File file) {
        fileMapper.insertSelective(file);
    }

    @Override
    public void delById(Integer id) {
        fileMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void batchRemove(List<Integer> ids) {
        fileMapper.batchRemove(ids);
    }

    @Override
    public void update(File file) {

    }

    @Override
    public File findById(Integer id) {
        return fileMapper.selectByPrimaryKey(id);
    }

    @Override
    public HotelFile findByFileId(Integer id) {
        Example example = new Example(HotelFile.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fileId",id);

        return hotelFileMapper.selectOneByExample(example);
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
