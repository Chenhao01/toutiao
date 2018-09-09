package com.newcoder.toutiao.async.Handler;

import com.newcoder.toutiao.async.EventHandler;
import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.model.Massage;
import com.newcoder.toutiao.service.LikeService;
import com.newcoder.toutiao.service.MassageService;
import com.newcoder.toutiao.service.NewsService;
import com.newcoder.toutiao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by 12274 on 2018/1/2.
 */
@Component
public class LikeHandler implements EventHandler {
    @Autowired
    MassageService massageService;
    @Autowired
    NewsService newsService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;
    @Override
    public void doHandler(EventModel model) {
        Massage massage=new Massage();
        massage.setHasRead(1);
        massage.setCreatedDate(new Date());
        massage.setToId(model.getEntityOwnerId());
        massage.setFromId(model.getActorId());
        massage.setConversationId(model.getActorId() < model.getEntityOwnerId() ?
                String.format("%d-%d",model.getActorId(),model.getEntityOwnerId()):
                String.format("%d-%d",model.getEntityOwnerId(),model.getActorId()));
        String content=userService.getUser(model.getActorId()).getName()
                +"赞了您的资讯#"+newsService.getById(model.getEntityId()).getTitle()+"#"
                +"link:http://127.0.0.1:8080/news/"+model.getEntityId();
        massage.setContent(content);
        massageService.addMassage(massage);

        newsService.updateLikeCount(model.getEntityId(),Integer.valueOf(model.getExts("likeCount")).longValue());
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
