package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventProducer;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.service.LikeService;
import com.newcoder.toutiao.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 12274 on 2017/12/25.
 */
@Controller
public class LikeController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;
    @Autowired
    NewsService newsService;
    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path={"/like"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("newsId")int newsId){
        int userId=hostHolder.getUser().getId();
        long likeCount=likeService.like(userId, toutiaoUtil.entityType_NEWS,newsId);
        //newsService.updateLikeCount(newsId,likeCount);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(userId)
                .setEntityType(toutiaoUtil.entityType_NEWS)
                .setEntityId(newsId)
                .setEntityOwnerId(newsService.getById(newsId).getUserId())
                .setExts("likeCount",String.valueOf(likeCount)));
        return toutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path={"/dislike"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("newsId")int newsId){
        int userId=hostHolder.getUser().getId();
        long likeCount=likeService.disLike(userId, toutiaoUtil.entityType_NEWS,newsId);
        newsService.updateLikeCount(newsId,likeCount);
        return toutiaoUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
