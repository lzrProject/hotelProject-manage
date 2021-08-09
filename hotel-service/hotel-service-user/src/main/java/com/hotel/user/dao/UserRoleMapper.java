package com.hotel.user.dao;

import com.hotel.user.pojo.UserRole;
import org.apache.ibatis.annotations.Insert;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserRoleMapper extends Mapper<UserRole> {
    @Insert("<script>" +
            "insert into tb_user_role " +
            "(user_id, role_id ) " +
            "values " +
            "<foreach collection =\"list\" item=\"item\" separator =\",\"> " +
            "(#{item.userId}, #{item.roleId}) " +
            "</foreach >" +
            "</script>")
    int batchInsert(List<UserRole> userRoles);
}
