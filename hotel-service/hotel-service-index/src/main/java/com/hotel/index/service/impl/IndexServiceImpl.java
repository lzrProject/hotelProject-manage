package com.hotel.index.service.impl;

import com.github.pagehelper.PageInfo;
import com.hotel.index.dao.IndexMapper;
import com.hotel.index.pojo.Menu;
import com.hotel.index.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    @Override
    public void addAll(Menu menu) {

    }

    @Override
    public void delById(Integer id) {

    }

    @Override
    public void update(Menu menu) {

    }

    @Override
    public Menu findById(int id) {
        return null;
    }

    @Override
    public List<Menu> findList(Menu menu) {
        Example example = createExample(menu);
        return indexMapper.selectByExample(example);
    }

    @Override
    public List<Menu> findAll() {
        return indexMapper.findAll();
    }

    @Override
    public PageInfo<Menu> findPage(int page, int size) {
        return null;
    }

    /**
     * Describe: 递归获取菜单tree
     * Param: sysMenus
     * Return: 操作结果
     * */
    @Override
    public List<Menu> toUserMenu(List<Menu> sysMenus,Integer parentId) {
        List<Menu> list = new ArrayList<>();
        for (Menu menu : sysMenus) {

                if (parentId.equals(menu.getParentId())) {
                    menu.setChildren(toUserMenu(sysMenus, menu.getId()));
                    list.add(menu);
                }

        }
        return list;
    }

    @Override
    public PageInfo<Menu> findPage(Menu menu, int page, int size) {
        return null;
    }

    public Example createExample(Menu menu){
        Example example = new Example(Menu.class);    //自定义条件查询
        Example.Criteria criteria = example.createCriteria();//条件构造器

        if(menu != null){
            //根据父id查询
            if(!StringUtils.isEmpty(menu.getParentId())){
                criteria.andEqualTo("parentId",menu.getParentId());
            }
        }
        return example;
    }
}
