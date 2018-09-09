package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 12274 on 2017/12/20.
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME="comment";
    String SELECT_FIELDS="id,content,user_id,entity_id,entity_type,created_date,status";
    String INSERT_FIELDS="content,user_id,entity_id,entity_type,created_date,status";

    @Insert({"INSERT INTO",TABLE_NAME,"(",INSERT_FIELDS,
            ") VALUES(#{content},#{userId},#{entityId},#{entityType},#{createdDate},#{status})"})
    int addComment(Comment comment);


    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,
            "WHERE entity_id=#{entityId} AND entity_type=#{entityType} ORDER BY id DESC"})
    List<Comment> selectByEntity(@Param("entityId")int entityId, @Param("entityType")int entityType);

    @Select({"SELECT COUNT(id) FROM",TABLE_NAME,
            "WHERE entity_id=#{entityId} AND entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId")int entityId, @Param("entityType")int entityType);

    @Update({"UPDATE",TABLE_NAME,"SET content=#{content} WHERE id=#{id}"})
    void updateContent(String content);

    @Delete({"DELETE FROM",TABLE_NAME,"WHERE id=#{id}"})
    void deleteById(int id );
}
