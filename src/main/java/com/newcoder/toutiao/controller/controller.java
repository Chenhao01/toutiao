package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventProducer;
import com.newcoder.toutiao.async.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by 12274 on 2017/11/14.
 */
@Controller
public class controller {

    @Autowired
    EventProducer eventProducer;


    @RequestMapping(path={"/"})
    @ResponseBody
    public String Controller(HttpSession session){
        return "hellow controller,"+session.getAttribute("msg");
    }

    @RequestMapping(value={"/index"})
    @ResponseBody
    public String indexController(){
        return "hellow index";
    }

    @RequestMapping(value={"/profile/{groupID}/{userID}"})
    @ResponseBody
    public String profile(@PathVariable("groupID") String groupID,
                          @PathVariable("userID") int userID,
                          @RequestParam(value="type",defaultValue="1") int type,
                          @RequestParam(value="key",defaultValue ="chenhao") String key){

        return String.format("groupID={%s},userID={%d},type={%d},key={%s}",groupID,userID,type,key);
    }

    @RequestMapping(value={"/vm/{pv1}/{pv2}"})
    public String news(Model model,
                       @PathVariable("pv1")String pv1,
                       @PathVariable("pv2")String pv2,
                       @RequestParam(value = "rp1",defaultValue = "1")int rp1,
                       @RequestParam(value = "rp2",defaultValue = "requestParam")String rp2){
        List<String> list= Arrays.asList(new String[]{"red","yellow","blue"});
        Map<String,String> map=new HashMap<String,String>();
        for(int i=0;i<3;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("list",list);
        model.addAttribute("map",map);
        Date date=new Date();
        model.addAttribute("time",date);
        model.addAttribute("path",pv1+pv2+rp2+String.valueOf(rp1));
        return "news";
    }

    /*@RequestMapping(path={"/test"})
    public String test(Model model,ModelAndView mav,HttpServletRequest request,HttpServletResponse response){
        User user=new User();
        user.setName("chenhao");
        user.setPassword("111111");
        mav.addObject("user",user);
        model.addAttribute("users",user);
        return "news";
    }*/
    @RequestMapping(path={"/test"})
    public ModelAndView testModel(ModelAndView mav){
        Map<String,Object> map=new HashMap<String,Object>();
        List<String> list= Arrays.asList(new String[]{"red","yellow","blue"});
        map.put("list",list);
        map.put("key","KEY");
        map.put("value","VALUE");
        mav.setViewName("news");
        mav.addObject("name","chenhao");
        mav.addObject("map",map);
        return mav;
    }


    @RequestMapping(value={"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sbr=new StringBuilder();
        Enumeration<String> headerNames=request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            sbr.append(headerNames.nextElement()+"</br>");
        }
        for(Cookie cookie :request.getCookies()) {
            sbr.append(cookie);
        }
        return sbr.toString();
    }

    @RequestMapping(value={"/response"})
    @ResponseBody
    public String response(@CookieValue(value="newcoderID",defaultValue = "a") String newcoderID,
                           @RequestParam(value="key",defaultValue = "key") String key,
                           @RequestParam(value="value",defaultValue = "value") String value,
                           HttpServletResponse response){
        response.addCookie(new Cookie(key,value));
        response.addHeader(key,value);
        return "Cookie:"+newcoderID;
    }

    @RequestMapping(value = {"/redirect/{code}"})
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession session){
        session.setAttribute("msg","jump from redirect");
        RedirectView red=new RedirectView("/",true);
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }

    @RequestMapping(value = {"/redirect1"})
    public String redirect1(){
        return "redirect:/";
    }

    @RequestMapping(value = {"/admin"})
    @ResponseBody
    public String admin(@RequestParam(value = "key",required = false) String key){
        if(key.equals("admin")){
            return "hello admin";
        }
        throw new IllegalArgumentException("key error");
    }
    @ExceptionHandler
    @ResponseBody
    public String error(Exception e){
        return e.getMessage();
    }




    @RequestMapping(value = {"/Mail"})
    public String mail(){
        return "mail";
    }

    @RequestMapping(value = {"/sendMail"})
    public String sendmail(){
        for(int i=0;i<100;i++){
            eventProducer.fireEvent(new EventModel(EventType.MAIL).setExts("email","1174909269@qq.com"));
        }
        return "redirect:/Mail";
    }
}
