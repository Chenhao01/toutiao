package com.newcoder.toutiao.dao;

import com.newcoder.toutiao.model.Student;
import org.apache.ibatis.annotations.*;

/**
 * Created by 12274 on 2017/12/3.
 */
@Mapper
public interface StudentDAO {
    String TABLE_NAME="student";
    String INSET_FIELDS="name,age,birth";
    String SELECT_FIELDS="id"+INSET_FIELDS;

    @Insert({"INSERT INTO",TABLE_NAME,"(",INSET_FIELDS,") VALUES(#{name},#{age},#{birth})"})
    int addStudent(Student student);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE id=#{id}"})
    Student selectById(int id);

    @Select({"SELECT",SELECT_FIELDS,"FROM",TABLE_NAME,"WHERE name=#{name}"})
    Student selectByName(String name);

    @Update({"UPDATE",TABLE_NAME,"SET age=#{age} WHERE id=#{id}"})
    void updateStudent(Student student);

    @Delete({"DELETE FROM ",TABLE_NAME, " WHERE id = #{id}"})
    void deleteStudent(int id);
}
