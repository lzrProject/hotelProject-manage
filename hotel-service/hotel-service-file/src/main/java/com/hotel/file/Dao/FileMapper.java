package com.hotel.file.Dao;

import com.hotel.index.pojo.File;
import org.apache.ibatis.annotations.Delete;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FileMapper extends Mapper<File> {
    @Delete("<script>" +
            "delete from tb_file where id in " +
            "<foreach item=\"id\" collection=\"list\" open=\"(\" separator=\",\" close=\")\">" +
            "#{id} " +
            "</foreach>" +
            "</script>")
    void batchRemove(List<Integer> ids);
}
