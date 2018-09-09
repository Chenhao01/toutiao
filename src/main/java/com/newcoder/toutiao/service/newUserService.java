package com.newcoder.toutiao.service;

import com.newcoder.toutiao.dao.LoginTicketDAO;
import com.newcoder.toutiao.dao.UserDAO;
import com.newcoder.toutiao.model.LoginTicket;
import com.newcoder.toutiao.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 12274 on 2018/4/4.
 */
@Service
public class newUserService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    LoginTicketDAO loginTicketDAO;
    public Map<String,String> reg(String username,String passwoed){
        Map<String,String> map=new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名为空！");
            return map;
        }
        if(StringUtils.isBlank(passwoed)){
            map.put("msg","密码为空！");
            return map;
        }
        User user=userDAO.selectByName(username);
        if(user != null){
            map.put("msg","用户名已存在！");
            return map;
        }
        user=new User();
        user.setName(username);
        user.setPassword(passwoed);
        userDAO.addUser(user);
        String ticket=getLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public Map<String,String> login(String username,String passwoed){
        Map<String,String> map=new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名为空！");
            return map;
        }
        if(StringUtils.isBlank(passwoed)){
            map.put("msg","密码为空！");
            return map;
        }
        User user=userDAO.selectByName(username);
        if(user == null){
            map.put("msg","用户不存在！");
            return map;
        }
        if(!passwoed.equals(user.getPassword())){
            map.put("msg","密码不正确！");
            return map;
        }
        String ticket=getLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    public String getLoginTicket(int userId){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setTicket(UUID.randomUUID().toString().substring(0,8));
        loginTicket.setStatus(0);
        Date date=new Date();
        date.setTime(date.getTime()+1000*60*60*24);
        loginTicket.setExpired(date);
        loginTicket.setUserId(userId);
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
