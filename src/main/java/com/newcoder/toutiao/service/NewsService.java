package com.newcoder.toutiao.service;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.dao.NewsDAO;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.News;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by 12274 on 2017/11/17.
 */
@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;
    @Autowired
    HostHolder hostHolder;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int index=file.getOriginalFilename().lastIndexOf(".");
        if(index<0){
            return null;
        }
        String ext=file.getOriginalFilename().substring(index+1).toLowerCase();
        if(!toutiaoUtil.isFileAllowed(ext)){
            return null;
        }
        String fileName= UUID.randomUUID().toString().replaceAll("-","")+"."+ext;
        Files.copy(file.getInputStream(), new File(toutiaoUtil.IMAGE_DIR+fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return toutiaoUtil.TOUTIAO_DOMAIN+"image?name="+fileName;
    }

    public News getById(int newsId){
        News news=newsDAO.selectById(newsId);
        return news;
    }

    public void addNews(String image,String link,String title){
        News news=new News();
        if(hostHolder.getUser()==null){
            news.setUserId(10);
        }else{
            news.setUserId(hostHolder.getUser().getId());
        }
        news.setImage(image);
        news.setLink(link);
        news.setTitle(title);
        news.setCreatedDate(new Date());
        newsDAO.addNews(news);
    }

    public void updateLikeCount(int newsId,long likeCount){
        newsDAO.updateLikeCount(newsId,likeCount);
    }

}
