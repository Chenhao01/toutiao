package com.newcoder.toutiao.Util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * Created by 12274 on 2017/12/27.
 */
public class redis {
    public static void print(int index,Object obj){
        System.out.println(index+":"+obj);
    }
    public static void main(String[] args){
        Jedis jedis=new Jedis();
        /*jedis.set("key1","106");
        jedis.set("key2","106");
        jedis.set("key3","106");
        jedis.set("key4","106");

        jedis.decr("key1");
        print(1,jedis.get("key1"));
        jedis.decrBy("key1",5);
        print(2,jedis.get("key1"));
        //jedis.del("key1","key2");
        //jedis.flushAll();*/

       //List
       /*String ListName="listA";
        for(int i=0;i<10;i++){
            jedis.lpush(ListName,"l"+String.valueOf(i));
        }
        print(1,jedis.lrange(ListName,0,5));
        print(2,jedis.llen(ListName));
        print(3,jedis.lpop(ListName));
        print(4,jedis.llen(ListName));
        print(5,jedis.lindex(ListName,3));
        print(6,jedis.linsert(ListName, BinaryClient.LIST_POSITION.BEFORE,"l4","xx"));
        print(7,jedis.linsert(ListName, BinaryClient.LIST_POSITION.AFTER,"l4","xx"));
        print(8,jedis.lrange(ListName,0,12));*/

        /*String userkey="user11";
        jedis.hset(userkey,"name","chenhao");
        jedis.hsetnx(userkey,"name","xiaoming");
        jedis.hsetnx(userkey,"name","xiaoming");
        jedis.hset(userkey,"age","24");
        jedis.hset(userkey,"tel","18605246566");
        print(1111,jedis.hlen(userkey));
        print(111,jedis.hincrBy(userkey,"age",1));
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
        print(8,jedis.hgetAll(userkey));*/

        /*String set1="set1";
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
        print(9,jedis.smembers(set2));*/

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
    }
}
