package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.News;
import com.newcoder.toutiao.model.ViewObject;
import com.newcoder.toutiao.service.LikeService;
import com.newcoder.toutiao.service.ToutiaoService;
import com.newcoder.toutiao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12274 on 2017/11/17.
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ToutiaoService toutiaoService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(@RequestParam(value = "userId", defaultValue = "0") int userId,
                        @RequestParam(value = "pop",defaultValue = "0")int pop,
                        Model model) {
        model.addAttribute("vos", getNews(userId, 0, 10));
        model.addAttribute("pop",pop);
        return "home";
    }

    @RequestMapping(path = {"/user/{userId}/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(@PathVariable("userId") int userId, Model model) {

        model.addAttribute("vos", getNews(userId, 0, 10));
        return "home";
    }

    @RequestMapping(path = {"/Image"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String upload() {
        return "uploadImage";
    }

    private List<ViewObject> getNews(int userId, int offset, int limit) {
        List<News> newsList = toutiaoService.getLatestNews(userId, offset, limit);
        int currentUserId=hostHolder.getUser()==null? 0 : hostHolder.getUser().getId();
        List<ViewObject> vos = new ArrayList<ViewObject>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            if(currentUserId==0){
                vo.set("like",0);
            }else{
                vo.set("like",likeService.getLikeStatus(currentUserId, toutiaoUtil.entityType_NEWS,news.getId()));
            }
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
