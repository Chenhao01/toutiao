package com.newcoder.toutiao.service;

import com.newcoder.toutiao.dao.MassageDAO;
import com.newcoder.toutiao.model.Massage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 12274 on 2017/12/21.
 */
@Service
public class MassageService {
    @Autowired
    MassageDAO massageDAO;

    public int addMassage(Massage massage){
        return massageDAO.addMassage(massage);
    }

    public List<Massage> getConversationDetail(String conversationId,int offset,int limit){
        return massageDAO.selectByConversationId(conversationId,offset,limit);
    }

    public List<Massage> getConversationList(int userId, int offset, int limit){
        return massageDAO.selectByGroup(userId,offset,limit);
    }
    public int getCount(String conversationId){
        return massageDAO.selectByCount(conversationId);
    }
    public int getUnreadCount(int userId,String conversationId){
        return massageDAO.selectByGroupAndNuread(userId,conversationId);
    }
}
