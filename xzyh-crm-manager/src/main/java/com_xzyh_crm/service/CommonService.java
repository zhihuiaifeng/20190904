package com_xzyh_crm.service;

import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.util.Config;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Service
public class CommonService {
    @Autowired
    private Config config;

    public String uploadPhoto(MultipartFile file) {
        String Result;
        try {
            String contentType = Util.getFileRealType(file.getInputStream());
            if (!contentType.contains("image")) {
                return Result= "noType";}
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = file.getOriginalFilename();
        // 得到原来的文件名称
        String newFileName = System.nanoTime() * 1000 + fileName.substring(fileName.lastIndexOf("."));
        File dest = null;
        /*获得什么系统*/
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            /*文件的上传路径*/
            String path = config.getUploadPath()  + config.getProductImg();
            File file2 = new File(path); // 创建本地文件流对象
            if (!file2.exists()) {
                file2.mkdirs();
            }
            dest= new File(path + newFileName);
        }else {
            String path = "/webapps/img/";
            File file3 = new File(path); // 创建本地文件流对象
            if (!file3.exists()) {
                file3.mkdirs();
            }
            dest= new File(path + newFileName);
        }
        try {
            file.transferTo(dest);
            //访问照片的网络地址


            String webAddress=config.getServerUrl() + config.getProductImg().replaceAll("\\\\\\\\", "/")+newFileName;
//            String webAddress = config.getServerUrl() + ISysConsts.FILE_SEPARATOR + this.config.getResourceFolder() + ISysConsts.FILE_SEPARATOR + uploadFolder + ISysConsts.FILE_SEPARATOR + fileName;
            return webAddress ;
        } catch (IOException e) {
            throw new CustomException("图片上传失败,请检查类型和网络", PublicDictUtil.ERROR_DOT_VALUE);
        }

    }
    //删除图片
    public boolean delFile(String imgname) {
        String path = config.getUploadPath() + config.getProductImg() + imgname;
        File file = new File(path);
//	文件存在
        if (file.exists() || file.isFile()) {

            file.delete();
            return true;
        }
        //return file.delete();
        return false;
    }

}
