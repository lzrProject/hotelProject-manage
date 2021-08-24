package com.hotel.file.util;


import com.hotel.file.pojo.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * FastDFS文件管理
 *
 */
public class FastDFSUtil {
    /**
     * 加载Tracker信息
     */
    static {
        try {
            String filePath = new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker信息
            ClientGlobal.init(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     */
    public static String[] upload(FastDFSFile fastDFSFile) {
        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author",fastDFSFile.getAuthor());

        //创建一个Tracker访问的客户端对象TrackerClient
        try {
            TrackerServer trackerServer = getTrackerServer();
            StorageClient storageClient = new StorageClient(trackerServer,null);

            //upload_file【】会返回两个参数
            //  upload[0]:  文件上传存储的Storage的组名字 group1
            //  upload[1]:  文件存储的路径
            String[] upload_file = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), null);
            return upload_file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件信息
     * groupName: 文件所在的组名
     * remoteFileName:  文件存储的路径名
     * @return
     */
    public static FileInfo getFileInfo(String groupName , String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            FileInfo file_info = storageClient.get_file_info(groupName, remoteFileName);
            return file_info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * 文件下载
     * @return
     */
    public static InputStream downloadFile(String groupName , String remoteFileName) {
        try {
            StorageClient storageClient = getStorageClient();
            byte[] bytes = storageClient.download_file(groupName, remoteFileName);

            return new ByteArrayInputStream(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 文件删除
     * @return
     */
    public static int deleteFile(String groupName , String remoteFileName){
        try {
            StorageClient storageClient = getStorageClient();
            int i = storageClient.delete_file(groupName, remoteFileName);

            return i;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * 获取Storage组信息
     * @return
     */
    public StorageServer getStorage(String groupName){
        try {
            TrackerClient trackerClient = new TrackerClient();

            TrackerServer trackerServer = trackerClient.getConnection();

            StorageServer storeStorage = trackerClient.getStoreStorage(trackerServer, groupName);
            return storeStorage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件组名和文件存储路径获取Storage服务的IP、端口信息
     * groupName :组名
     * remoteFileName ：文件存储完整名
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            //获取服务信息
            return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取Tracker服务地址
     */
    public static String getTrackerUrl(){
        try {
            //创建TrackerClient对象
            TrackerClient trackerClient = new TrackerClient();
            //通过TrackerClient获取TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();

            //Tracker域名
            String hostString = trackerServer.getInetSocketAddress().getHostString();
            //Tracker域名端口
            int trackerHttpPort = ClientGlobal.getG_tracker_http_port();

            //获取Tracker地址
            return "http://"+hostString+":"+trackerHttpPort;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取TrackerServer
     */
    public static TrackerServer getTrackerServer() throws Exception{
        //创建TrackerClient对象
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /***
     * 获取StorageClient
     * @return
     */
    public static StorageClient getStorageClient() throws Exception {
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        //通过TrackerServer创建StorageClient
        StorageClient storageClient = new StorageClient(trackerServer,null);
        return storageClient;
    }
}
