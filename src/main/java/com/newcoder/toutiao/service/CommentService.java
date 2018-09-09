package com.newcoder.toutiao.service;

import com.newcoder.toutiao.dao.CommentDAO;
import com.newcoder.toutiao.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 12274 on 2017/12/20.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;
    public List<Comment> getCommentByENtity(int entityId,int entityType){
        return commentDAO.selectByEntity(entityId,entityType);
    }
    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }
    public int addComment(Comment comment){
        return commentDAO.addComment(comment);
    }
}
