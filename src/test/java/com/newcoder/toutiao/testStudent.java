package com.newcoder.toutiao;

import com.newcoder.toutiao.dao.StudentDAO;
import com.newcoder.toutiao.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

/**
 * Created by 12274 on 2017/12/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
public class testStudent {
    @Autowired
    StudentDAO studentDAO;

    @Test
    public void Student(){
        Random r=new Random();
        for(int i=1;i<10;i++){
            Student student=new Student();
            student.setAge(r.nextInt(20));
            student.setBirth(String.format("%d-%d-%d",r.nextInt(20),r.nextInt(12),r.nextInt(28)));
            student.setName(String.format("测试人员%d",i));

            studentDAO.addStudent(student);
        }
        Student student=new Student();
        student.setId(1);
        student.setAge(20);
        studentDAO.updateStudent(student);
        studentDAO.deleteStudent(2);
    }
}
