package com.hotel.index.dao;

import com.hotel.index.pojo.RolePower;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RolePowerMapper extends Mapper<RolePower> {
    @Insert("<script>" +
            "insert into tb_role_power " +
            "(role_id, power_id ) " +
            "values " +
            "<foreach collection =\"list\" item=\"item\" separator =\",\"> " +
            "(#{item.roleId}, #{item.powerId}) " +
            "</foreach >" +
            "</script>")
    int batchInsert(List<RolePower> rolePowers);

    @Delete("<script>" +
            "delete from tb_role_power where role_id in " +
            "<foreach collection =\"list\" item=\"roleId\" open=\"(\" separator=\",\" close=\")\"> " +
            "#{roleId}" +
            "</foreach>" +
            "</script>")
    void deleteByRoleIds(List<Integer> ids);

    @Select("SELECT trp.role_id as roleId,trp.power_id as powerId FROM `tb_user_role` tur,`tb_role_power` trp where tur.user_id = #{userId} AND tur.role_id = trp.role_id")
    List<RolePower> findPowerId(Integer userId);
}
