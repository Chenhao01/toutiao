package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by 12274 on 2018/4/11.
 */
@Controller
public class fileUploadTest {

    @RequestMapping(path="/uploadTest",method = RequestMethod.POST)
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        String fileName=null;
        if(file!=null){
            fileName=file.getOriginalFilename();
            int index=fileName.indexOf(".");
            String ext=fileName.substring(index+1);
            String newFileName= UUID.randomUUID().toString().replaceAll("-","")+"."+ext;
            String dir="G:/IntelliJ IDE/upload/";
            String newFilePath=dir+newFileName;
            file.transferTo(new File(newFilePath));
            return toutiaoUtil.getJSONString(0,newFileName);
        }
        return toutiaoUtil.getJSONString(1,"上传失败");
    }
}
