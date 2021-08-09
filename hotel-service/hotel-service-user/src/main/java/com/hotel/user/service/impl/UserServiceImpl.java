package com.hotel.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hotel.user.dao.UserMapper;
import com.hotel.user.dao.UserRoleMapper;
import com.hotel.user.pojo.User;
import com.hotel.user.pojo.UserRole;
import com.hotel.user.service.UserService;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void addAll(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delById(Integer id) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);

        userMapper.deleteByPrimaryKey(id);
        userRoleMapper.deleteByExample(example);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findByUsername(String username) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        return userMapper.selectByExample(example);

    }

    @Override
    public List<User> findList(User user) {
        Example example = createExample(user);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public PageInfo<User> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<User> findPage(User user, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(user);
        List<User> list = userMapper.selectByExample(example);
        return new PageInfo<User>(list);
    }

    public Example createExample(User user){
        Example example = new Example(User.class);    //自定义条件查询
        Example.Criteria criteria = example.createCriteria();//条件构造器

        if(user != null){
            //根据名字模糊查询
            if(!StringUtils.isEmpty(user.getUsername())){
                criteria.andLike("username","%"+user.getUsername()+"%");
            }
            //根据首字母模糊查询
            if(!StringUtils.isEmpty(user.getPassword())){
                criteria.andEqualTo("password",user.getPassword());
            }
        }
        return example;
    }
}
