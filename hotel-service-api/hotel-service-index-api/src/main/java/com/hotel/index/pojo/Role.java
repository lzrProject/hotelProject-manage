package com.hotel.index.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_role")
public class Role {

    /**
     * 编号
     * */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 角色名称
     * */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 角色值
     * */
    @Column(name = "role_code")
    private String roleCode;

    /**
     * 状态
     * */
    @Column(name = "enable")
    private String enable;

    /**
     * 描述
     */
    @Column(name = "details")
    private String details;

    /**
     * 排序
     * */
    @Column(name = "sort")
    private Integer sort;

    /**
     * 提供前端 显示
     * */
    private  boolean checked = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
