package com.hotel.entrance.controller;
import com.hotel.wx.feign.OrderFeign;
import com.hotel.wx.pojo.Order;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    @Autowired
    private OrderFeign orderFeign;

    //3.添加定时任务
    @Scheduled(cron = "0 0 12 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String format = ft.format(date);

        Result<List<Order>> lists = orderFeign.findList();
        if(!lists.isFlag()){
            return;
        }
        for(Order list : lists.getData()){
            if(list.getStatus() == "1"){
                if(list.getEndDate().equals(format)){
                    Order order = new Order();
                    order.setId(list.getId());
                    order.setStatus("2");
                    orderFeign.update(order);
                }
            }
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
