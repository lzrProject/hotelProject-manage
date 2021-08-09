package com.hotel.index.service.impl;

import com.github.pagehelper.PageInfo;
import com.hotel.index.dao.PowerMapper;
import com.hotel.index.dao.RoleMapper;
import com.hotel.index.dao.RolePowerMapper;
import com.hotel.index.pojo.Menu;
import com.hotel.index.pojo.Power;
import com.hotel.index.pojo.RolePower;
import com.hotel.index.service.PowerService;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PowerServiceImpl implements PowerService {
    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public void addAll(Power power) {
        powerMapper.insertSelective(power);
    }

    @Override
    public void delById(Integer id) {
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("powerId", id);

        powerMapper.deleteByPrimaryKey(id);
        rolePowerMapper.deleteByExample(example);
    }



    @Override
    public void update(Power power) {
        powerMapper.updateByPrimaryKeySelective(power);
    }

    @Override
    public Power findById(Integer id) {
        return powerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Power> findByParentId(Integer pid) {
        Example example = new Example(Power.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",pid);
        return powerMapper.selectByExample(example);
    }

    @Override
    public List<Power> findList(Power power) {
        return null;
    }

    @Override
    public List<Power> list(Power power) {

        return powerMapper.selectList(power);
    }

    @Override
    public List<Power> findAll() {
        return powerMapper.selectAll();
    }

    @Override
    public PageInfo<Power> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<Power> findPage(Power power, int page, int size) {
        return null;
    }
}
