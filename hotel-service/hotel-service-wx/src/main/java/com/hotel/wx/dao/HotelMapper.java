package com.hotel.wx.dao;

import com.hotel.wx.pojo.Hotel;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HotelMapper extends Mapper<Hotel> {
    @Select("<script>" +
            "select id,image_url as imageUrl,hotel_name as hotelName,score ,services ,address ,distance ,price,parent_id as parentId,sort from tb_hotelList" +
            "<where>" +
            "<if test=\"parentId!=null and parentId!=''\">" +
            "and parent_id = #{parentId}" +
            "</if>" +
            "</where>" +
            "order by sort asc" +
            "</script>")
    List<Hotel> selectList(Hotel hotel);
}
