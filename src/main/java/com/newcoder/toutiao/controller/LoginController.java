package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventProducer;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by 12274 on 2017/11/23.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;
    @Autowired
    EventProducer eventProducer;
    @RequestMapping(path={"/reg/"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String reg(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="rember",defaultValue = "0") int rember,
                      HttpServletResponse response ){
        try{
            Map<String,Object> map= userService.register(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if (rember > 0) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return toutiaoUtil.getJSONString(0,"注册成功");
            }else{
                return toutiaoUtil.getJSONString(1,map);
            }
        }catch(Exception e){
            logger.error("注册异常"+e.getMessage());
            return toutiaoUtil.getJSONString(1,"注册异常");
        }
    }


    @RequestMapping(path={"/login/"},method={RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String log(Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value="rember",defaultValue = "0") int rember,
                      HttpServletResponse response){
        try{
            Map<String,Object> map= userService.login(username,password);
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                if (rember > 0) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                eventProducer.fireEvent(new EventModel(EventType.LOGIN).setActorId((int)map.get("userId"))
                                            .setExts("email","1329685951@qq.com")
                                            .setExts("username",username));
                return toutiaoUtil.getJSONString(0,"登陆成功");
            }else{
                return toutiaoUtil.getJSONString(1,map);
            }
        }catch(Exception e){
            logger.error("登陆异常"+e.getMessage());
            return toutiaoUtil.getJSONString(1,"登录异常");
        }
    }

    @RequestMapping(path={"/logout/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
