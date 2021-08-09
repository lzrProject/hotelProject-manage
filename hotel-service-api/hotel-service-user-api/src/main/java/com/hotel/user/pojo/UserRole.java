package com.hotel.user.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_user_role")
public class UserRole {
    /**
     * 映射标识
     * */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 用户编号
     * */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 角色编号
     * */
    @Column(name = "role_id")
    private Integer roleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
