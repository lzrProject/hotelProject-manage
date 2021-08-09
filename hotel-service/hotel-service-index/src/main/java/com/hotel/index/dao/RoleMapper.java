package com.hotel.index.dao;

import com.hotel.index.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface RoleMapper extends Mapper<Role> {
    @Delete("<script>" +
            "delete from tb_role where id in " +
            "<foreach item=\"id\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">" +
            "#{id} " +
            "</foreach>" +
            "</script>")
    void batchRemove(List<Integer> ids);
}
