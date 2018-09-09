package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.Massage;
import com.newcoder.toutiao.model.User;
import com.newcoder.toutiao.model.ViewObject;
import com.newcoder.toutiao.service.MassageService;
import com.newcoder.toutiao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 12274 on 2017/12/21.
 */
@Controller
public class MassageController {
    private Logger logger= LoggerFactory.getLogger(MassageController.class);
    @Autowired
    MassageService massageService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path={"/msg/addMassage"},method = RequestMethod.POST)
    @ResponseBody
    public String addMassage(@RequestParam("fromId")int fromId,
                             @RequestParam("toId")int toId,
                             @RequestParam("content")String content){
        try{
            Massage massage=new Massage();
            massage.setFromId(fromId);
            massage.setToId(toId);
            massage.setContent(content);
            massage.setCreatedDate(new Date());
            massage.setHasRead(0);
            massage.setConversationId(fromId < toId ? String.format("%d-%d",fromId,toId):String.format("%d-%d",toId,fromId));
            massageService.addMassage(massage);
            return toutiaoUtil.getJSONString(massage.getId());
        }catch(Exception e){
            logger.error("失败"+e.getMessage());
            return toutiaoUtil.getJSONString(1,"失败");
        }
    }

    @RequestMapping(path={"/msg/detail"},method = RequestMethod.GET)
    public String massageDetail(@RequestParam("conversationId")String conversationId,Model model){
        List<Massage> list=new ArrayList<Massage>();
        List<ViewObject> vos=new ArrayList<ViewObject>();
        list=massageService.getConversationDetail(conversationId,0,10);
        for(Massage msg:list){
            ViewObject vo=new ViewObject();
            vo.set("message",msg);
            User user=userService.getUser(msg.getFromId());
            if(user==null){
                continue;
            }
            vo.set("user",user);
            vo.set("headUrl",user.getHeadUrl());
            vo.set("userId",user.getId());
            vos.add(vo);
        }
        model.addAttribute("messages",vos);
        return "letterDetail";
    }

    @RequestMapping(path={"/msg/list"},method = RequestMethod.GET)
    public String getConversation(Model model){
        List<Massage> list=new ArrayList<Massage>();
        List<ViewObject> vos=new ArrayList<ViewObject>();
        int userId=hostHolder.getUser().getId();
        list=massageService.getConversationList(userId,0,10);
        for(Massage msg:list){
            ViewObject vo=new ViewObject();
            vo.set("conversation",msg);
            vo.set("count",massageService.getCount(msg.getConversationId()));
            vo.set("unreadCount",massageService.getUnreadCount(userId,msg.getConversationId()));
            int id=userId==msg.getFromId()?userId:msg.getFromId();
            vo.set("user",userService.getUser(id));
            vos.add(vo);
        }
        model.addAttribute("conversations",vos);
        return "letter";
    }
}
