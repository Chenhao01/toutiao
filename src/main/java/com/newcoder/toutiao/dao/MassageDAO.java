package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.Massage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by 12274 on 2017/12/21.
 */
@Mapper
public interface MassageDAO {
    String TABLE_NAME="message";
    String SELECT_FIELDDS="id,from_id,to_id,content,created_date,has_read,conversation_id";
    String INSET_FIELDS="from_id,to_id,content,created_date,has_read,conversation_id";
    @Insert({"INSERT INTO",TABLE_NAME,"(",INSET_FIELDS,
            ") VALUES(#{fromId},#{toId},#{content},#{createdDate},#{hasRead},#{conversationId})"})
    int addMassage(Massage massage);

    @Select({"SELECT",SELECT_FIELDDS,"FROM",TABLE_NAME,"WHERE conversation_id=#{conversationId} ORDER BY id DESC LIMIT #{offset},#{limit}"})
    List<Massage> selectByConversationId(@Param("conversationId")String conversationId,
                                         @Param("offset")int offset,
                                         @Param("limit")int limit);

    @Select({"SELECT",SELECT_FIELDDS,"FROM",TABLE_NAME,"WHERE from_id=#{userId} OR to_id=#{userId} GROUP BY conversation_id DESC ORDER BY id DESC LIMIT #{offset},#{limit}"})
    List<Massage> selectByGroup(@Param("userId")int usreId,
                                @Param("offset")int offset,
                                @Param("limit")int limit);

    @Select({"SELECT COUNT(id) FROM",TABLE_NAME,"WHERE conversation_id=#{conversationId}"})
    int selectByCount(@Param("conversationId")String conversationId);

    @Select({"SELECT COUNT(id) FROM",TABLE_NAME,"WHERE to_id=#{userId} AND conversation_id=#{conversationId} AND has_read=1"})
    int selectByGroupAndNuread(@Param("userId")int userId,@Param("conversationId")String conversationId);


}
