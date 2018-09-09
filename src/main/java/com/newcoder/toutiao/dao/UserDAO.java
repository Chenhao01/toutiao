package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by rainday on 16/6/30.
 */
@Mapper
public interface UserDAO {

    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password, salt, head_url ";

    String SELECT_FIELDS = " id, name, password, salt, head_url ";

    @Insert({
            "insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") Values (#{name}, #{password}, #{salt}, #{headUrl})"
    })
    int addUser(User user);

   @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{username}"})
    User selectByName(String username);

    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ",TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
