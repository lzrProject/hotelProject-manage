package com.hotel.index.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hotel.index.dao.RoleMapper;
import com.hotel.index.dao.RolePowerMapper;
import com.hotel.index.pojo.Power;
import com.hotel.index.pojo.Role;
import com.hotel.index.pojo.RolePower;
import com.hotel.index.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePowerMapper rolePowerMapper;

    @Override
    public void addAll(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void delById(Integer id) {
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", id);

        roleMapper.deleteByPrimaryKey(id);
        rolePowerMapper.deleteByExample(example);
    }

    @Override
    public void batchRemove(List<Integer> ids) {
        roleMapper.batchRemove(ids);
        rolePowerMapper.deleteByRoleIds(ids);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> findList(Role role) {
        return null;
    }

    @Override
    public List<Role> list(Role role) {
        return null;
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    @Override
    public PageInfo<Role> findPage(int page, int size) {
        return null;
    }

    @Override
    public PageInfo<Role> findPage(Role role, int page, int size) {
        PageHelper.startPage(page,size);
        Example example = createExample(role);
        List<Role> list = roleMapper.selectByExample(example);
        return new PageInfo<Role>(list);
    }

    public Example createExample(Role role){
        Example example = new Example(Role.class);    //自定义条件查询
        Example.Criteria criteria = example.createCriteria();//条件构造器

        if(role != null){
            //根据名字模糊查询
            if(!StringUtils.isEmpty(role.getRoleName())){
                criteria.andLike("roleName","%"+role.getRoleName()+"%");
            }
            //根据首字母模糊查询
            if(!StringUtils.isEmpty(role.getRoleCode())){
                criteria.andEqualTo("roleCode",role.getRoleCode());
            }
        }
        return example;
    }
}
