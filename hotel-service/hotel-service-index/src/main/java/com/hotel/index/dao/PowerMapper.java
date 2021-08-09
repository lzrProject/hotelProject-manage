package com.hotel.index.dao;

import com.hotel.index.pojo.Power;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PowerMapper extends Mapper<Power> {

    @Select("<script>" +
            "select id,power_name as powerName,power_type as powerType,power_code as powerCode,power_url as powerUrl,open_type as openType,parent_id as parentId,icon,sort,enable from tb_power" +
            "<where>" +
            "<if test=\"parentId!=null and parentId!=''\">" +
            "and parent_id = #{parentId}" +
            "</if>" +
            "</where>" +
            "order by sort asc" +
            "</script>")
    List<Power> selectList(Power power);

}
