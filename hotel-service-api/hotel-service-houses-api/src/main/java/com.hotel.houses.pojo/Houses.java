package com.hotel.houses.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "tb_houses")
public class Houses implements Serializable {
    @Id
    /***
     * @GeneratedValue 用于标注主键
     * –IDENTITY：采用数据库ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
     * –AUTO： JPA自动选择合适的策略，是默认选项；
     * –SEQUENCE：通过序列产生主键，通过@SequenceGenerator 注解指定序列名，MySql不支持这种方式
     * –TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")

    private int id;

    @Column(name = "information")
    private String information;//房源信息
    @Column(name = "image")
    private String image;//房源图片地址
    @Column(name = "name")
    private String name;//委托人

    @Column(name = "datetime")
    private String datetime;//委托时间
    @Column(name = "inquiries")
    private int inquiries;//咨询量
    @Column(name = "showing")
    private int showing;//看房量
    @Column(name = "status")
    private int status;//房源状态

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getInquiries() {
        return inquiries;
    }

    public void setInquiries(int inquiries) {
        this.inquiries = inquiries;
    }

    public int getShowing() {
        return showing;
    }

    public void setShowing(int showing) {
        this.showing = showing;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
