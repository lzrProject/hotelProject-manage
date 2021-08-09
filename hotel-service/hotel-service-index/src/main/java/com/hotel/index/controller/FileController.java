package com.hotel.index.controller;

import com.github.pagehelper.PageInfo;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Role;
import com.hotel.index.service.FileService;
import entity.Result;
import entity.StatusCode;
import file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import servlet.ServletUtil;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/file")
@CrossOrigin //跨域
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 角色分页列表
     * 角色查询列表
     */
    @PostMapping("/page")
    public Result<PageInfo> findPage(@RequestBody File file, @RequestParam int page, @RequestParam int limit){
        PageInfo<File> result = fileService.findPage(file, page, limit);
        return new Result<PageInfo>(true, StatusCode.OK,"查询成功",result);
    }

    /**
     * Describe: 文件上传
     * Param: File
     * Return: id
     */
    @PostMapping("/add")
    public Result addAll(@RequestBody File file) {
            fileService.addAll(file);
            if (file.getId() != null) {
                return new Result(true,StatusCode.OK,"添加成功",file.getId());
            }
            return new Result(false,StatusCode.ERROR,"添加失败");
    }

    /**
     * Describe: 根据 Id 下载文件
     * Param: id
     * Return: IO
     */
    @PostMapping("/findByName")
    public List<File> download(@RequestBody File file) {
        List<File> byOtherName = fileService.findByName(file);
        return byOtherName;
    }

    /**
     * 根据id主键删除
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    public Result delete(@RequestParam Integer id){
        fileService.delById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
