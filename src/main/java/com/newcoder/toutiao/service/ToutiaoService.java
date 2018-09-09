package com.newcoder.toutiao.service;

import com.newcoder.toutiao.dao.NewsDAO;
import com.newcoder.toutiao.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nowcoder on 2016/6/26.
 */
@Service
public class ToutiaoService {
    public String say() {
        return "This is from ToutiaoService";
    }

    @Autowired
    private NewsDAO newsDAO;

    public List<News> getLatestNews(int userId, int offset, int limit) {
        return newsDAO.selectByUserIdAndOffset(userId, offset, limit);
    }
}
