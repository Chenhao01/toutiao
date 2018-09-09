package com.newcoder.toutiao;

import com.newcoder.toutiao.dao.CommentDAO;
import com.newcoder.toutiao.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 12274 on 2017/12/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class testComment {
    @Autowired
    CommentDAO commentDAO;

    @Test
    public void Comment(){
        Comment comment=new Comment();
        comment.setContent("5555555555");
        comment.setCreatedDate(new Date());
        comment.setEntityId(10);
        comment.setEntityType(1);
        comment.setUserId(9);
        comment.setStatus(0);
        commentDAO.addComment(comment);
        List<Comment> list=new ArrayList<Comment>();
        list=commentDAO.selectByEntity(1,1);
        int i=commentDAO.getCommentCount(1,1);
    }
}
