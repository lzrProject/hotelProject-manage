package com.hotel.user.service.impl;

import com.github.pagehelper.PageInfo;
import com.hotel.user.dao.UserRoleMapper;
import com.hotel.user.pojo.User;
import com.hotel.user.pojo.UserRole;
import com.hotel.user.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void addAll(UserRole userRole) {

    }

    @Override
    public int batchInsert(List<UserRole> userRoles) {
        return userRoleMapper.batchInsert(userRoles);
    }

    @Override
    public void delById(Integer id) {

    }

    @Override
    public void delByUserId(Integer userId) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public void update(UserRole userRole) {

    }

    @Override
    public User findById(Integer id) {
        return null;
    }

    @Override
    public List<UserRole> findByUserId(Integer id) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        return userRoleMapper.selectByExample(example);
    }

    @Override
    public List<UserRole> findList(UserRole userRole) {
        return null;
    }

    @Override
    public List<UserRole> findAll() {
        return null;
    }

    @Override
    public PageInfo<UserRole> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<UserRole> findPage(UserRole userRole, int page, int size) {
        return null;
    }
}
