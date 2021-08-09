package com.hotel.wx.dao;

import com.hotel.wx.pojo.CustomerOrder;
import com.hotel.wx.pojo.Order;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CustomerOrderMapper extends Mapper<CustomerOrder> {

    @Select("<script>" +
            "SELECT c.id as id , c.`code` as code , c.hotel_name as hotelName , c.room_name as roomName , c.room_price as roomPrice , c.day_count as dayCount , c.start_date as startDate , c.end_date as endDate, c.`status` , c.hotel_id as hotelId FROM tb_customer a ,`tb_customer_order` b , tb_order c where open_id = #{openId} AND a.id = b.customer_id AND b.order_id = c.id " +
            "<if test=\"status!=null and status!=''\">" +
            "and status = #{status}" +
            "</if>" +
            " order by id desc" +
            "</script>")
    List<Order> findByOpenId(String openId,String status);

    @Select("SELECT c.id as id , c.`code` as code , c.hotel_name as hotelName , c.room_name as roomName , c.room_price as roomPrice , c.day_count as dayCount , c.start_date as startDate , c.end_date as endDate, c.`status` as `status` , c.hotel_id as hotelId FROM tb_customer a ,`tb_customer_order` b , tb_order c where open_id = #{openId} AND a.id = b.customer_id AND b.order_id = c.id order by id desc")
    List<Order> findByOpenId1(String openId);
}
