package com.newcoder.toutiao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by 12274 on 2018/1/14.
 */
public class test {
    public  static boolean getBit(byte b,int i){
        i-=1;
        return (b & (1 << i))!=0;
    }
    public static void main(String[] args){
        String key="chcc";
        byte[] bytes=key.getBytes();
        for(byte b:bytes){
            System.out.println(getBit(b,1));
        }
        Set<String> set=new HashSet<>();
        set.add("set1");
        set.add("set2");
        set.add("set3");
        set.add("set4");
        set.add("set5");
        Iterator i=set.iterator();
        while (i.hasNext()){
            System.out.println(i.next());
        }
    }
}
