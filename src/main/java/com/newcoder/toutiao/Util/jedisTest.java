package com.newcoder.toutiao.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Created by 12274 on 2018/4/11.
 */
public class jedisTest {
    private static final Logger logger= LoggerFactory.getLogger(jedisTest.class);
    static JedisPool jedisPool=new JedisPool();

    public static void print(String str,Object obj){
        System.out.println(str+":"+obj+"\n");
    }
    public static void print(String str1,Object obj1,String str2,Object obj2){
        System.out.println(str1+":"+obj1+"\n"+str2+":"+obj2+"\n");
    }
    public static void print(Object obj){
        System.out.println(obj.toString()+"\n");
    }
    public static List<String> list(Jedis jedis,String key){
        return jedis.lrange(key,0,jedis.llen(key)-1);
    }
    public static Set<String> set(Jedis jedis, String key){
        return jedis.smembers(key);
    }
    public static void testList(){
        Jedis jedis=jedisPool.getResource();
        String key="list";
        try{
            jedis.del(key);
            String[] str={"0","1","2","3","4","5","6",
                          "7","8","9"};
            print("String[] str={\"0\",\"1\",\"2\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"9\"};",str);
            print("jedis.lpush(key,str);");
            jedis.lpush(key,str);
            print("jedis.llen(key)",jedis.llen(key),"list",list(jedis,key));
            print("jedis.ltrim(key,0,5)",jedis.ltrim(key,0,5),"list",list(jedis,key));
            print("jedis.lpop(key)",jedis.lpop(key),"list",list(jedis,key));
            print("jedis.lrem(key,5,\"7\")",jedis.lrem(key,5,"7"),"list",list(jedis,key));
            print("jedis.linsert(key,BinaryClient.LIST_POSITION.AFTER,\"8\",\"7\")",jedis.linsert(key,BinaryClient.LIST_POSITION.AFTER,"8","7"),"list",list(jedis,key));
            print("jedis.linsert(key,BinaryClient.LIST_POSITION.BEFORE,\"8\",\"9\")",jedis.linsert(key,BinaryClient.LIST_POSITION.BEFORE,"8","9"),"list",list(jedis,key));
            print("jedis.lindex(key,0)",jedis.lindex(key,0),"list",list(jedis,key));
            print("jedis.lset(key,0,\"6\")",jedis.lset(key,0,"6"),"list",list(jedis,key));
        }catch(Exception e){
            logger.error(e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    public static void testSet(){
        Jedis jedis=jedisPool.getResource();
        String key="set1";
        String key2="set2";
        try{
            jedis.del(key);
            jedis.del(key2);
            String[] str={"0","1","2","3","4","5","6",
                    "7","8","9"};
            String[] str2={"3","4","5","6",
                    "7","8","9","11","12"};
            print("jedis.sadd(key,str)",jedis.sadd(key,str),"set",jedis.smembers(key));
            print("jedis.sadd(key2,str2)",jedis.sadd(key2,str2),"set2",jedis.smembers(key2));
            print("jedis.smembers(key)",jedis.smembers(key));
            print("jedis.smembers(key2)",jedis.smembers(key2));
            print("jedis.sinter(key,key2)",jedis.sinter(key,key2));
            print("jedis.sdiff(key,key2)",jedis.sdiff(key,key2));
            print("jedis.sunion(key,key2)",jedis.sunion(key,key2));
            print("jedis.smove(key2,key,\"12\")",jedis.smove(key2,key,"12"),"set",jedis.smembers(key));
            print("set2",jedis.smembers(key2));
            print("jedis.srem(key,\"12\")",jedis.srem(key,"12"),"set",jedis.smembers(key));
            print("jedis.scard(key)",jedis.scard(key),"set",jedis.smembers(key));
            print("jedis.sismember(key,\"5\")",jedis.sismember(key,"5"),"set",jedis.smembers(key));
        }catch(Exception e){
            logger.error(e.getMessage());
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }
    public static void main(String[] args){
        //testList();
        testSet();
    }
}
