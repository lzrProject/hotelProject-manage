package com.hotel.houses.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hotel.houses.dao.HouseMapper;
import com.hotel.houses.pojo.Houses;
import com.hotel.houses.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;


    @Override
    public void addAll(Houses houses) {                 //添加数据
        /***
         * 方法带有Selective，会忽略空值
         */
        houseMapper.insertSelective(houses);
    }

    @Override
    public void delById(Integer id) {                   //删除数据
        houseMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Houses houses) {                 //修改数据
        houseMapper.updateByPrimaryKeySelective(houses);
    }

    /***
     * 当有主键时,优先用selectByPrimaryKey,
     * 当根据实体类属性查询时用select,
     * 当有复杂查询时,如模糊查询,条件判断时使用selectByExample
     *
     */
    @Override
    public Houses findById(int id) {                    //根据id查询
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Houses> findList(Houses houses) {       //根据条件查询
        Example example = createExample(houses);
        return houseMapper.selectByExample(example);
    }

    @Override
    public List<Houses> findAll() {                     //查询所有
        return houseMapper.selectAll();
    }

    @Override
    public PageInfo<Houses> findPage(int page, int size) {  //分页查询  page当前页面 size有多少条数据
        PageHelper.startPage(page, size);
        List<Houses> result = houseMapper.selectAll();
        return new PageInfo<Houses>(result);
    }

    @Override
    public PageInfo<Houses> findPage(Houses houses, int page, int size) {   //分页+条件查询
        PageHelper.startPage(page,size);
        Example example = createExample(houses);
        List<Houses> result = houseMapper.selectByExample(example);
        return new PageInfo<Houses>(result);
    }

    public Example createExample(Houses houses){
        Example example = new Example(Houses.class);    //自定义条件查询
        Example.Criteria criteria = example.createCriteria();//条件构造器

        if(houses != null){
            //根据名字模糊查询
            if(!StringUtils.isEmpty(houses.getName())){
                criteria.andLike("name","%"+houses.getName()+"%");
            }
            //根据首字母模糊查询
            if(!StringUtils.isEmpty(houses.getDatetime())){
                criteria.andEqualTo("datetime",houses.getDatetime());
            }
        }
        return example;
    }
}
