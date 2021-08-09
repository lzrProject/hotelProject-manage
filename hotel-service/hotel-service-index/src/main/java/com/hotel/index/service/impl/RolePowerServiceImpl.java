package com.hotel.index.service.impl;

import com.github.pagehelper.PageInfo;
import com.hotel.index.dao.RolePowerMapper;
import com.hotel.index.pojo.RolePower;
import com.hotel.index.service.RolePowerService;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class RolePowerServiceImpl implements RolePowerService {

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public void addAll(RolePower rolePower) {

    }

    @Override
    public int batchInsert(List<RolePower> rolePowers) {
        return rolePowerMapper.batchInsert(rolePowers);
    }

    @Override
    public void delById(Integer id) {
        rolePowerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delByRoleId(Integer roleId) {
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        rolePowerMapper.deleteByExample(example);
    }

    @Override
    public void delByPowerId(Integer powerId) {
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("powerId", powerId);
        rolePowerMapper.deleteByExample(example);
    }

    @Override
    public void update(RolePower rolePower) {

    }

    @Override
    public RolePower findById(Integer id) {
        return null;
    }

    @Override
    public List<RolePower> findPowerId(Integer userId) {
        List<RolePower> result = rolePowerMapper.findPowerId(userId);
        return result;
    }

    @Override
    public List<RolePower> findList(RolePower rolePower) {
        return null;
    }



    @Override
    public List<RolePower> list(Integer id) {
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", id);

        List<RolePower> result = rolePowerMapper.selectByExample(example);
        return result;
    }

    @Override
    public List<RolePower> findAll() {
        return null;
    }

    @Override
    public PageInfo<RolePower> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<RolePower> findPage(RolePower rolePower, int page, int size) {
        return null;
    }
}
