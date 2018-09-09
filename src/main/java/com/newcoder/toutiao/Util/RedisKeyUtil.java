package com.newcoder.toutiao.Util;

/**
 * Created by 12274 on 2017/12/25.
 */
public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String LIKE="LIKE";
    private static String DISLIKE="DISLIKE";
    private static String EVENT="ENVENT";

    public static String getLikeKey(int entryType,int entryId){
        return LIKE+SPLIT+String.valueOf(entryType)+SPLIT+String.valueOf(entryId);
    }

    public static String getDisLikeKey(int entryType,int entryId){
        return DISLIKE+SPLIT+String.valueOf(entryType)+SPLIT+String.valueOf(entryId);
    }
    public static String getEVENTQueueKey(){
        return EVENT;
    }
}
