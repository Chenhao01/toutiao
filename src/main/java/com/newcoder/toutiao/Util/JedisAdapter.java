package com.newcoder.toutiao.Util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Created by 12274 on 2017/12/23.
 */
@Component
public class JedisAdapter implements InitializingBean{
    private Logger logger= LoggerFactory.getLogger(JedisAdapter.class);
    private JedisPool jedisPool=null;
    public static void print(int index,Object obj){
        System.out.println(index+":"+obj);
    }
   /* public static void main(String[] args){
        Jedis jedis=new Jedis();
        jedis.flushAll();
        jedis.set("key1","hello");
        jedis.rename("key1","key2");
        jedis.setex("key3",15,"hello!");
        print(1,jedis.get("key1"));
        print(1,jedis.get("key2"));
        print(1,jedis.get("key3"));

        jedis.set("pv","100");
        jedis.incr("pv");

        print(1,jedis.get("pv"));
        jedis.incrBy("pv",5);
        print(2,jedis.get("pv"));

        String ListName="listA";
        for(int i=0;i<10;i++){
            jedis.lpush(ListName,"l"+String.valueOf(i));
        }
        print(1,jedis.lrange(ListName,0,12));
        print(2,jedis.llen(ListName));
        print(3,jedis.lpop(ListName));
        print(4,jedis.llen(ListName));
        print(5,jedis.lindex(ListName,3));
        print(6,jedis.linsert(ListName, BinaryClient.LIST_POSITION.BEFORE,"l4","xx"));
        print(7,jedis.linsert(ListName, BinaryClient.LIST_POSITION.AFTER,"l4","xx"));
        print(8,jedis.lrange(ListName,0,12));

        String userkey="user11";
        jedis.hset(userkey,"name","chenhao");
        jedis.hset(userkey,"age","24");
        jedis.hset(userkey,"tel","18605246566");
        print(1,jedis.hget(userkey,"name"));
        print(2,jedis.hgetAll(userkey));
        jedis.hdel(userkey,"tel");
        print(3,jedis.hgetAll(userkey));
        print(4,jedis.hkeys(userkey));
        print(5,jedis.hvals(userkey));
        print(6,jedis.hexists(userkey,"email"));
        print(7,jedis.hexists(userkey,"age"));
        jedis.hsetnx(userkey,"school","just");
        jedis.hsetnx(userkey,"age","24");
        print(8,jedis.hgetAll(userkey));

        String set1="set1";
        String set2="set2";
        for(int i=0;i<10;i++){
            jedis.sadd(set1,String.valueOf(i));
            jedis.sadd(set2,String.valueOf(i*i));
        }
        print(1,jedis.smembers(set1));
        print(2,jedis.smembers(set2));
        print(3,jedis.sinter(set1,set2));//求交集
        print(3,jedis.sunion(set1,set2));//求并集
        print(3,jedis.sdiff(set1,set2));//求不同
        print(4,jedis.sismember(set1,"5"));
        jedis.srem(set1,"5");
        print(5,jedis.sismember(set1,"5"));
        print(6,jedis.scard(set1));
        jedis.smove(set2,set1,"16");
        print(7,jedis.scard(set1));
        print(8,jedis.smembers(set1));
        print(9,jedis.smembers(set2));

        String rankKey="rankkey";
        jedis.zadd(rankKey,80,"jim");
        jedis.zadd(rankKey,90,"beng");
        jedis.zadd(rankKey,85,"job");
        jedis.zadd(rankKey,86,"caty");
        jedis.zadd(rankKey,96,"keni");
        jedis.zadd(rankKey,100,"lucy");
        print(1,jedis.zcard(rankKey));
        print(2,jedis.zcount(rankKey,90,100));
        print(3,jedis.zscore(rankKey,"lucy"));
        jedis.zincrby(rankKey,2,"keni");
        print(4,jedis.zscore(rankKey,"keni"));
        print(5,jedis.zrange(rankKey,0,2));//后三名
        print(6,jedis.zrevrange(rankKey,0,2));//前三名
        for(Tuple tuple:jedis.zrevrangeByScoreWithScores(rankKey,100,0)){
            print(7,tuple.getElement()+":"+tuple.getScore());
        }
        print(8,"beng排在倒数第"+(jedis.zrank(rankKey,"beng").intValue()+1)+"名");
        print(9,"beng排在正数第"+(jedis.zrevrank(rankKey,"beng").intValue()+1)+"名");

        JedisPool jedisPool=new JedisPool();//默认八条链接
        for(int i=0;i<100;i++){
            Jedis j=jedisPool.getResource();
            print(i,"POOL"+i);
            j.close();
        }*/

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool=new JedisPool("localhost",6379);
    }

    public Jedis getJedis(){
        return jedisPool.getResource();
    }

    public void set(String key,String value) {
        Jedis j = null;
        try {
            j = jedisPool.getResource();
             j.set(key,value);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }
    public void setex(String key, String value) {
        // 验证码, 防机器注册，记录上次注册时间，有效期3天
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex(key, 10, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public String get(String key) {
        Jedis j = null;
        try {
            j = jedisPool.getResource();
            return j.get(key);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
            return null;
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }

    public long lpush(String key,String value){
        Jedis j = null;
        try {
            j = jedisPool.getResource();
            return j.lpush(key,value);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
            return 0;
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }
    public List<String> brpop(int timeout, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    public long sadd(String key,String value) {
        Jedis j = null;
        try {
            j = jedisPool.getResource();
            return j.sadd(key, value);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
            return 0;
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }

    public long srem(String key,String value) {
        Jedis j = null;
        try {
            j = jedisPool.getResource();
            return j.srem(key, value);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
            return 0;
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }

    public boolean sismember(String key,String value){
        Jedis j = null;
        try {
            j = jedisPool.getResource();
            return j.sismember(key,value);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
            return false;
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }

    public  long scard(String key){
        Jedis j = null;
        try {
            j = jedisPool.getResource();
            return j.scard(key);
        } catch (Exception e) {
            logger.error("错误:" + e.getMessage());
            return 0;
        } finally {
            if (j != null) {
                j.close();
            }
        }
    }

    public void setObject(String key,Object obj){
        set(key, JSON.toJSONString(obj));
    }

    public <T> T getObject(String key,Class<T> objClass){
        String value=get(key);
        return JSON.parseObject(value, objClass);
    }

}
