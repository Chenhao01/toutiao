package com.newcoder.toutiao.service;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.dao.LoginTicketDAO;
import com.newcoder.toutiao.dao.UserDAO;
import com.newcoder.toutiao.model.LoginTicket;
import com.newcoder.toutiao.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by 12274 on 2017/11/16.
 */
@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    /*public void addUser(User user){
        userDAO.addUser(user);
    }*/

    public Map<String,Object> register(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为为空");
            return map;
        }
        User user=userDAO.selectByName(username);
        if(user!=null){
            map.put("msgname","用户已存在");
            return map;
        }
        //密码强度
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dm.png", new Random().nextInt(1000)));
        user.setPassword(toutiaoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        //登陆
        map.put("userId",user.getId());
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,Object> login(String username,String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if(StringUtils.isBlank(username)){
            map.put("msgname","用户名不能为为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为为空");
            return map;
        }
        User user=userDAO.selectByName(username);
        if(user==null){
            map.put("msgname","用户名不存在");
            return map;
        }
        if(!toutiaoUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","密码不正确");
            return map;
        }
        map.put("userId",user.getId());
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        //登陆
        return map;
    }

    private String addLoginTicket(int userId){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(userId);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDAO.update(ticket,1);
    }

    public User getUser(int id){
        return userDAO.selectById(id);
    }
}
