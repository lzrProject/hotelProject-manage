package com.hotel.entrance.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.hotel.index.feign.FileFeign;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.Role;
import entity.Result;
import entity.StatusCode;
import feign.Response;
import file.FileUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import servlet.ServletUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Describe: 文件控制器
 */
@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 系 统 文 件
     * */
    private String MODULE_PATH = "system/file/";

    @Autowired
    private FileFeign fileFeign;

    /**
     * Describe: 文件管理页面
     * Param: null
     * Return: ModelAndView
     * */
    @GetMapping("main")
    @PreAuthorize("hasAuthority('sys:file:main')")
    public ModelAndView main(){
        return new Result().jumpPage(MODULE_PATH + "main");
    }

    /**
     * Describe: 文件资源数据
     * Param: PageDomain
     * Return: 文件资源列表
     * */
    @GetMapping("data")
    @PreAuthorize("hasAuthority('sys:file:data')")
    public JSONObject data(File file, @RequestParam int page, @RequestParam int limit){
        Result<PageInfo> result = fileFeign.findPage(file, page, limit);
        if(result.isFlag()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("message",result.getMessage());
            jsonObject.put("count",result.getData().getTotal());
            jsonObject.put("data",result.getData().getList());
            return jsonObject;
        }
        return null;
    }

    /**
     * Describe: 文件上传视图
     * Param: null
     * Return: 执行结果
     * */
    @GetMapping("add")
    @PreAuthorize("hasAuthority('sys:file:add')")
    public ModelAndView add(){
        return new Result().jumpPage(MODULE_PATH + "add");
    }

    /**
     * Describe: 文件获取接口
     * Param: fileName
     * Return: 文件流
     *
    @GetMapping("download/{fileName}")
    public void download(@PathVariable("fileName") String fileName) {
        File file = new File();
        file.setFileName(fileName);
        fileFeign.download(file);
    }*/

    /**
     * Describe: 文件获取接口
     * Param: id
     * Return: 文件流
     * */
    @GetMapping("download1/{id}")
    public void download(@PathVariable("id") Integer id) {
        File file = new File();
        file.setId(id);

        InputStream fileInputStream = null;
//        OutputStream outStream;

        try {
            Response download = fileFeign.download(file);
            Response.Body body = download.body();

            fileInputStream = body.asInputStream();

            FileCopyUtils.copy(fileInputStream, ServletUtil.getResponse().getOutputStream());



/*          outStream = servletResponse.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outStream.write(bytes, 0, len);
            }
            fileInputStream.close();
            outStream.close();
            outStream.flush();
            if(inputStream.read() != -1){
//                InputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(fileInputStream != null) fileInputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }
    


    /**
     * Describe: 文件上传接口
     * Param: SysUser
     * Return: Result
     * */
    @PostMapping(value = "upload")
    public Result upload(@RequestParam("file") MultipartFile file){
        Result result = fileFeign.upload(file);
        if(!result.isFlag()){
            return new Result(false, StatusCode.ERROR,"上传失败");
        }

        return new Result(true , StatusCode.OK,"上传成功",result.getData());
    }


    /**
     * Describe: 文件删除接口
     * Param: id
     * Return: 文件流
     * */
    @DeleteMapping("remove/{id}")
    @PreAuthorize("hasAuthority('sys:file:remove')")
    public Result remove(@PathVariable("id") Integer id){
        Result result = fileFeign.delete(id);
        return result;
    }

    /**
     * Describe: 文件删除接口
     * Param: id
     * Return: 文件流
     * */
    @DeleteMapping("batchRemove/{ids}")
    @PreAuthorize("hasAuthority('sys:file:remove')")
    public Result batchRemove(@PathVariable("ids") String ids){
        List<String> strings = Arrays.asList(ids.split(","));
        List<Integer> integers = strings.stream().map(Integer::parseInt).collect(Collectors.toList());
        Result result = fileFeign.batchRemove(integers);

        return result;
    }


    /*public Result upload(@RequestParam("file") MultipartFile file){

        try {
            String name = file.getOriginalFilename();
            //后缀名
            String suffixName = name.substring(name.lastIndexOf("."));
            String fileName = new Date().getTime() + suffixName;
            String fileDir = LocalDate.now().toString();
            String parentPath = "D:\\home\\uploads\\" + fileDir;
            java.io.File filepath = new java.io.File(parentPath, fileName);
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }

            File fileDomain = new File();
            //上传
            file.transferTo(filepath);
            fileDomain.setFileDesc(name);
            fileDomain.setFileName(fileName);
            fileDomain.setTargetDate(LocalDate.now());
            fileDomain.setFilePath(filepath.getPath());
            fileDomain.setCreateTime(LocalDateTime.now());
            fileDomain.setFileSize(FileUtil.getPrintSize(filepath.length()));
            fileDomain.setFileType(suffixName.replace(".", ""));
            Result result = fileFeign.addAll(fileDomain);


            Map map = new HashMap();
            map.put("id",result.getData());
            map.put("avatar",fileName.split("\\.")[0]);
            if(result.getData() != null){
                return new Result(true,0,"上传成功",map);
            }else{
                return new Result(false, StatusCode.ERROR,"上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }*/

    /*//封装下载方法
    public void creatDownload(File file){

        try {
            if(file.getFilePath() == null){
                List<File> result = fileFeign.download(file);
                java.io.File files = new java.io.File(result.get(0).getFilePath());
                if (files.exists()) {
                    FileCopyUtils.copy(new FileInputStream(result.get(0).getFilePath()), ServletUtil.getResponse().getOutputStream());
                }
            }else {
                java.io.File files = new java.io.File(file.getFilePath());
                if (files.exists()) {
                    FileCopyUtils.copy(new FileInputStream(file.getFilePath()), ServletUtil.getResponse().getOutputStream());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
