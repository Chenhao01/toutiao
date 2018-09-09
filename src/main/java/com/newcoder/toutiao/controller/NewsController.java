package com.newcoder.toutiao.controller;

import com.newcoder.toutiao.Util.toutiaoUtil;
import com.newcoder.toutiao.model.Comment;
import com.newcoder.toutiao.model.HostHolder;
import com.newcoder.toutiao.model.News;
import com.newcoder.toutiao.model.ViewObject;
import com.newcoder.toutiao.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 12274 on 2017/12/12.
 */
@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    NewsService newsService;
    @Autowired
    QiniuService qiniuService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    @RequestMapping(path = {"/uploadImage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile file){
        try{
            String fileUrl=qiniuService.saveImage(file);
            if(fileUrl==null){
                return toutiaoUtil.getJSONString(1,"上传图片失败");
            }
            return  toutiaoUtil.getJSONString(0,fileUrl);
        }catch(Exception e){
            logger.error("上传图片失败"+e.getMessage());
            return toutiaoUtil.getJSONString(1,"上传失败");
        }
    }

    @RequestMapping(path={"/user/addNews/"},method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image")String image,
                        @RequestParam("link")String link,
                        @RequestParam("title")String title){
        try{
            newsService.addNews(image,link,title);
            return toutiaoUtil.getJSONString(0);
        }catch(Exception e){
            logger.error("添加资讯错误"+e.getMessage());
            return toutiaoUtil.getJSONString(1,"发布失败");
        }
    }

    @RequestMapping(path={"/news/{newsId}"},method = {RequestMethod.GET})
    public String newsDetail(@PathVariable("newsId")int newsId, Model model){
        News news=newsService.getById(newsId);
        if(news!=null){
            //评论
        }
        List<Comment> Comments=commentService.getCommentByENtity(newsId,toutiaoUtil.entityType_NEWS);
        List<ViewObject> comments=new ArrayList<ViewObject>();
        for(Comment comment:Comments){
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUser(comment.getUserId()));
            comments.add(vo);
        }
        int currentUserId=hostHolder.getUser()==null ? 0 : hostHolder.getUser().getId();
        if(currentUserId==0){
            model.addAttribute("like",0);
        }else{
            model.addAttribute("like",likeService.getLikeStatus(currentUserId,toutiaoUtil.entityType_NEWS,newsId));
        }
        model.addAttribute("comments",comments);
        news.setCommentCount(commentService.getCommentCount(newsId,1));
        model.addAttribute("news",news);
        model.addAttribute("owner",userService.getUser(news.getUserId()));
        return "detail";
    }

    @RequestMapping(path={"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId")int newsId,@RequestParam("content")String content){
        try{
            //过滤content内容
            content= HtmlUtils.htmlEscape(content);
            Comment comment=new Comment();
            comment.setStatus(0);
            comment.setUserId(hostHolder.getUser().getId());
            comment.setEntityType(toutiaoUtil.entityType_NEWS);
            comment.setEntityId(newsId);
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            commentService.addComment(comment);
        }catch (Exception e){
            logger.error("添加评论失败"+e.getMessage());
        }
        return "redirect:/news/"+newsId;
    }
    /*@RequestMapping(path = {"/image"}, method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response) {
        response.setContentType("image/jpeg");
        try {
            StreamUtils.copy(new FileInputStream(new File(toutiaoUtil.IMAGE_DIR + imageName)), response.getOutputStream());
        }catch(Exception e){
            logger.error("读取图片失败"+e.getMessage());
        }
    }*/
}
