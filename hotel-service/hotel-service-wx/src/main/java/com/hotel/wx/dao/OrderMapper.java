package com.hotel.wx.dao;

import com.hotel.wx.pojo.Hotel;
import com.hotel.wx.pojo.Order;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends Mapper<Order> {
    @Select("<script>" +
            "select a.open_id as openId , c.id , c.`code` , c.hotel_name as hotelName , c.room_name as roomName , c.room_price as roomPrice , c.day_count as dayCount , c.start_date as startDate , c.end_date as endDate , c.`status` from tb_customer a,tb_customer_order b,tb_order c WHERE a.id = b.customer_id AND b.order_id = c.id " +
            "<if test=\"openId!=null and openId!=''\">" +
            "and open_id = #{openId}" +
            "</if>" +
            "<if test=\"code!=null and code!=''\">" +
            "and `code` = #{code} " +
            "</if>" +
            "</script>")
    List<Order> findAll(Map map);


}
