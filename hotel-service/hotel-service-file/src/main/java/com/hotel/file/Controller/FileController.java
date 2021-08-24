package com.hotel.file.Controller;

import com.github.pagehelper.PageInfo;
import com.hotel.file.pojo.FastDFSFile;
import com.hotel.file.service.FileService;
import com.hotel.file.util.FastDFSUtil;
import com.hotel.index.pojo.File;
import com.hotel.index.pojo.HotelFile;
import entity.Result;
import entity.StatusCode;
import file.FileUtil;
import org.assertj.core.util.Arrays;
import org.csource.common.MyException;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import servlet.ServletUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "newFile")
@CrossOrigin
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
     * 文件上传
     */
    @PostMapping("uploadFile")
    public Result upload(@RequestPart MultipartFile file)  {
        //文件名
        String name = file.getOriginalFilename();

        //文件扩展名(获取文件后缀）
        String suffixName = StringUtils.getFilenameExtension(file.getOriginalFilename());



//        String fileName = new Date().getTime()+ "/" + suffixName;

        FastDFSFile fastDFSFile = null;
        try {
            //file.getBytes()       文件字节数组
            fastDFSFile = new FastDFSFile(name,file.getBytes(),suffixName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //上传
        String[] upload = FastDFSUtil.upload(fastDFSFile);

        if(Arrays.isNullOrEmpty(upload)){
            return new Result(false, StatusCode.ERROR,"上传失败");
        }
        FileInfo serverInfo = FastDFSUtil.getFileInfo(upload[0], upload[1]);
        String fileSize = FileUtil.getPrintSize(serverInfo.getFileSize());


//        String path = upload[1].substring(upload[1].indexOf("/"));
//        String filePath = "http://118.25.242.174:8080"+path;
        String filePath = "http://118.25.242.174:8080/"+upload[0]+"/"+upload[1];
        LocalDateTime createTime = serverInfo.getCreateTimestamp().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        String fileName = upload[1].substring(0,upload[1].lastIndexOf("."));
        Result result = addFile(name, fileName, filePath, createTime, fileSize, suffixName,upload[0]);
        if(!result.isFlag()){
            return new Result(false,StatusCode.ERROR,"添加失败");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("id",result.getData());
        map.put("avatar",result.getData());
        return new Result(true, StatusCode.OK,"上传成功",map);
    }

    public Result addFile(String name,String fileName,String filePath,LocalDateTime createTime,String fileSize,String fileType,String groupName){
        File fileDomain = new File();

        fileDomain.setFileDesc(name);
        fileDomain.setFileName(fileName);
        fileDomain.setTargetDate(LocalDate.now());
        fileDomain.setFilePath(filePath);
        fileDomain.setCreateTime(createTime);
        fileDomain.setFileSize(fileSize);
        fileDomain.setFileType(fileType);
        fileDomain.setGroupName(groupName);
        fileService.addAll(fileDomain);
        if (fileDomain.getId() != null) {
            return new Result(true,StatusCode.OK,"添加成功",fileDomain.getId());
        }
        return new Result(false,StatusCode.ERROR,"添加失败");
    }


    /**
     * 根据file模糊查询
     * @param file
     * @return
     */
    @PostMapping("findByOne")
    public List<File> findFile(@RequestBody File file){
        List<File> byName = fileService.findByName(file);

        return byName;
    }
    /**
     * 文件下载
     * @param file
     * @return
     */
    @PostMapping("downloadFile")
    public void download(@RequestBody File file, HttpServletResponse response) {

//        String trackerUrl = FastDFSUtil.getTrackerUrl();

        List<File> byName = fileService.findByName(file);
        String groupName = byName.get(0).getGroupName();
        String remoteFileName = byName.get(0).getFileName()+"."+byName.get(0).getFileType();
//        String groupName = "group1";
//        String remoteFileName = "M00/00/00/rBEQBGEhBv6AAcjDAAcRNZLHM7o368.jpg";
        InputStream inputStream = null;
        OutputStream outStream = null;
        try {
            inputStream = FastDFSUtil.downloadFile(groupName, remoteFileName);
            outStream = response.getOutputStream();

            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outStream.write(bytes, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream != null){
                    inputStream.close();
                }
                if (outStream != null){
                    outStream.flush();
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


//        return inputStream;

    }

    /**
     * 根据id主键删除
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    public Result delete(@RequestParam Integer id){
        File byId = fileService.findById(id);
        HotelFile byFileId = fileService.findByFileId(id);
        if(byId != null && byId.getFileName() != null){
            if(byFileId == null){
                int i = FastDFSUtil.deleteFile(byId.getGroupName(), byId.getFileName()+"."+byId.getFileType());
                if(i == -1){
                    return new Result(false,StatusCode.ERROR,"删除失败fastFDS");
                }
            }
            fileService.delById(id);
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @DeleteMapping("batchDelete")
    public Result batchRemove(@RequestBody List<Integer> ids){

        fileService.batchRemove(ids);
        return new Result(true,StatusCode.OK,"删除成功");
    }

}
