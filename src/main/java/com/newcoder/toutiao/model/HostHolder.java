package com.newcoder.toutiao.model;

import org.springframework.stereotype.Component;

/**
 * Created by 12274 on 2017/12/4.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<User>();
    public User getUser(){
        return users.get();
    }
    public void setUser(User user){
            users.set(user);
    }
    public void clear(){
        users.remove();
    }
}
