package com.leyou.upload;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("upload")
public class ImageUpload {

    private static final List<String> CONTENT_TYPES = Arrays.asList("jpg","gif");

    @Value("${user.httpImageYuming}")
    String HttpImage;

    @RequestMapping("image")
    public String uploadImage(MultipartFile file){

        String fileName = file.getOriginalFilename();

        String filetype = fileName.substring(fileName.lastIndexOf(".")+1);


        // 校验文件的类型
        if (!CONTENT_TYPES.contains(filetype)){
            // 文件类型不合法，直接返回null
            return null;
        }

        try {
            // 校验文件的内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null){
                return null;
            }

            String filepath=UUID.randomUUID().toString()+fileName;
            //保存到服务器
            file.transferTo(new File("E:\\photo\\" + filepath));
//            //加载客户端配置文件，配置文件中指明了tracker服务器的地址
//            ClientGlobal.init("fastdfs.conf");
//            //验证配置文件是否加载成功
//            System.out.println(ClientGlobal.configInfo());
//
//            //创建TrackerClient对象，客户端对象
//            TrackerClient trackerClient = new TrackerClient();
//
//            //获取到调度对象，也就是与Tracker服务器取得联系
//            TrackerServer trackerServer = trackerClient.getConnection();
//
//            //创建存储客户端对象
//            StorageClient storageClient = new StorageClient(trackerServer,null);
//
//
//
//            String[] file1 = storageClient.upload_appender_file(file.getBytes(),filetype, null);
//
//            for (String file2:file1){
//                System.out.println(file2);
//            }
//            // 生成url地址，返回
//            return HttpImage +file1[0]+"/"+file1[1];
            //返回路径
            return HttpImage+filepath;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    }
