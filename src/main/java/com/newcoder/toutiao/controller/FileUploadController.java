package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 12274 on 2017/12/18.
 */
@Controller
public class FileUploadController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /***
     * 上传文件 用@RequestParam注解来指定表单上的file为MultipartFile
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadImage1", method = {RequestMethod.POST})
    public String fileUpload(@RequestParam("file") MultipartFile file) {
        // 判断文件是否为空
        String name = null;
        if (!file.isEmpty()) {
            try {
                int index = file.getOriginalFilename().indexOf(".");
                String ext = file.getOriginalFilename().substring(index + 1);
                String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ext;
                name = fileName;
                // 文件保存路径
                String filePath = "G:/IntelliJ IDE/upload/"
                        + fileName;
                // 转存文件
                file.transferTo(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.error("空文件");
        }
        // 重定向
        return "redirect:/showImage?name=" + name;
    }

    /***
     * 读取上传文件中得所有文件并返回
     *
     * @return
     */
    @RequestMapping("list")
    public ModelAndView list() {
        String filePath = "G:/IntelliJ IDE/upload/";
        ModelAndView mav = new ModelAndView("list");
        File uploadDest = new File(filePath);
        String[] fileNames = uploadDest.list();
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < fileNames.length; i++) {
            //打印出文件名
            map.put("key" + i, fileNames[i]);
        }
        mav.addObject("map", map);
        return mav;
    }

    @RequestMapping(path = {"/showImage"}, method = RequestMethod.GET)
    @ResponseBody
    public void show(@RequestParam("name") String name, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try {
            StreamUtils.copy(new FileInputStream(new File(toutiaoUtil.IMAGE_DIR + name)), response.getOutputStream());
        } catch (Exception e) {
            logger.error("获取文件失败" + e.getLocalizedMessage());
        }
    }
}
