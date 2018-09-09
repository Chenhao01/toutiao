package com.newcoder.toutiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 12274 on 2017/11/16.
 */
@Controller
public class settingController {
    @RequestMapping(path="/setting")
    @ResponseBody
    public String setting(){
        return "setting:ok";
    }

    @RequestMapping(path={"/new"})
    public String news(){
        return "news";
    }

    @RequestMapping(path={"/testParam"})
    @ResponseBody
    public String test(@RequestParam("userId")String userId){
        return userId;
    }

}
