package com.newcoder.toutiao.model;

/**
 * Created by 12274 on 2017/12/3.
 */
public class Student {
    private int id;
    private String name;
    private int age;
    private String birth;

    public Student() {
    }

    public Student(String name, int age, String birth) {
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    public Student(int id, String name, int age, String birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
