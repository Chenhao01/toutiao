package com.newcoder.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.newcoder.toutiao.Util.JedisAdapter;
import com.newcoder.toutiao.Util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 12274 on 2018/1/2.
 */
@Service
public class EventProducer {
    private Logger logger= LoggerFactory.getLogger(EventProducer.class);
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model){
        try {
            String json = JSON.toJSONString(model);
            String eventKey = RedisKeyUtil.getEVENTQueueKey();
            jedisAdapter.lpush(eventKey, json);
            return true;
        }catch (Exception e){
            logger.error("错误"+e.getMessage());
            return false;
        }
    }
}

