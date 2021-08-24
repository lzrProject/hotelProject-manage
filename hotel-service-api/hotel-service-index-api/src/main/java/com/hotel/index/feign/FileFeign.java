package com.hotel.index.feign;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Role;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@FeignClient(value = "file")
@RequestMapping(value = "/newFile")
public interface FileFeign {

    /**
     * 角色分页列表
     * 角色查询列表
     */
    @PostMapping("/page")
    Result<PageInfo> findPage(@RequestBody File file, @RequestParam int page, @RequestParam int limit);

    /**
     * 文件上传
     */
    @PostMapping(value = "uploadFile",consumes = "multipart/form-data")
    Result upload(@RequestPart MultipartFile file);


    /**
     * 根据file模糊查询
     * @param file
     * @return
     */
    @PostMapping("findByOne")
    List<File> findFile(@RequestBody File file);

    /**
     * 文件下载
     * @param file
     * @return
     */
    @PostMapping("downloadFile")
    feign.Response download(@RequestBody File file);

    /**
     * 根据id主键删除
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    Result delete(@RequestParam Integer id);

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @DeleteMapping("batchDelete")
    Result batchRemove(@RequestBody List<Integer> ids);
}
