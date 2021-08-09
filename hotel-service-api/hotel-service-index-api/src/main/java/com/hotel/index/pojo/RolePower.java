package com.hotel.index.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tb_role_power")
public class RolePower {

    /**
     * 映射编号
     * */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 角色编号
     * */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 权限编号
     * */
    @Column(name = "power_id")
    private Integer powerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }
}
