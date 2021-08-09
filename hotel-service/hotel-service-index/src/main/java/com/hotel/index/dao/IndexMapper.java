package com.hotel.index.dao;

import com.hotel.index.pojo.Menu;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface IndexMapper extends Mapper<Menu> {

    @Select("SELECT id , parent_id as parentId , power_name as title , power_type as type , open_type as openType , icon , power_url as href , sort , `enable`  FROM `tb_power` ")
    List<Menu> findAll();
}
