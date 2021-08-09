package com.hotel.index.feign;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Role;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient(value = "index")
@RequestMapping(value = "/file")
public interface FileFeign {

    /**
     * 角色分页列表
     * 角色查询列表
     */
    @PostMapping("/page")
    Result<PageInfo> findPage(@RequestBody File file, @RequestParam int page, @RequestParam int limit);

    /**
     * Describe: 文件上传
     * Param: File
     * Return: id
     */
    @PostMapping("/add")
    Result addAll(@RequestBody File file);

    /**
     * Describe: 根据 Id 下载文件
     * Param: id
     * Return: IO
     */
    @PostMapping("/findByName")
    List<File> download(@RequestBody File file);

    /**
     * 根据id主键删除
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    Result delete(@RequestParam Integer id);
}
