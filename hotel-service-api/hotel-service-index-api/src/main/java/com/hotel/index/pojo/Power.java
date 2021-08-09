package com.hotel.index.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "tb_power")
public class Power {

    /**
     * 权限编号
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 权限名称
     */
    @Column(name = "power_name")
    private String powerName;

    /**
     *权限类型
     */
    @Column(name = "power_type")
    private Integer powerType;

    /**
     * 权限标识(分类）
     */
    @Column(name = "power_code")
    private String powerCode;

    /**
     * 权限路径
     */
    @Column(name = "power_url")
    private String powerUrl;

    /**
     * 打开方式
     * */
    @Column(name = "open_type")
    private String openType;

    /**
     * 父类编号
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 排序
     */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 是否开启
     */
    @Column(name = "enable")
    private Integer enable;


    /**
     * 计算列 提供给前端组件
     * */
    @Transient
    private transient String checkArr = "0";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public Integer getPowerType() {
        return powerType;
    }

    public void setPowerType(Integer powerType) {
        this.powerType = powerType;
    }

    public String getPowerCode() {
        return powerCode;
    }

    public void setPowerCode(String powerCode) {
        this.powerCode = powerCode;
    }

    public String getPowerUrl() {
        return powerUrl;
    }

    public void setPowerUrl(String powerUrl) {
        this.powerUrl = powerUrl;
    }

    public String getOpenType() {
        return openType;
    }

    public void setOpenType(String openType) {
        this.openType = openType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }
}
