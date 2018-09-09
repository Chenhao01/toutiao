package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * Created by 12274 on 2017/11/27.
 */
@Mapper
public interface LoginTicketDAO {
    String TABLE_NAME="login_ticket";
    String INSERT_FIELDS="user_id,ticket,expired,status";
    String SELECT_FIELDS="id,"+INSERT_FIELDS;

    /*@Insert({"insert into",TABLE_NAME,"(",INSERT_FIELDS,") values(#{userId},#{ticket},#{expired}，#{status})"
            })
    int addTicket(LoginTicket loginTicket);*/

    @Insert({
            "insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") Values (#{userId},#{ticket},#{expired},#{status})"
    })
    int addTicket(LoginTicket loginTicket);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update",TABLE_NAME,"set status=#{status} where ticket=#{ticket}"})
    void update(@Param("ticket") String ticket,@Param("status") int status);

    @Delete({"delete from",TABLE_NAME,"where ticket=#{ticket}"})
    void delete(String ticket);
}
