package com.newcoder.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.newcoder.toutiao.Util.JedisAdapter;
import com.newcoder.toutiao.Util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 12274 on 2018/1/2.
 */
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
    private Logger logger= LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType,List<EventHandler>> config=new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if(beans!=null) {
            for (Map.Entry<String, EventHandler> bean : beans.entrySet()) {
                List<EventType> typeList = bean.getValue().getSupportEventType();
                for (EventType type : typeList) {
                    if(!config.containsKey(type)){
                        config.put(type,new ArrayList<EventHandler>());
                    }
                    config.get(type).add(bean.getValue());
                }
            }
        }

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    String key= RedisKeyUtil.getEVENTQueueKey();
                    List<String> events=jedisAdapter.brpop(0,key);
                    for(String message:events){
                        if(message.equals(key)){
                            continue;
                        }
                        EventModel eventModel= JSON.parseObject(message,EventModel.class);
                        if(!config.containsKey(eventModel.getType())){
                            logger.error("无法识别的事件类型");
                            continue;
                        }
                        for(EventHandler handler:config.get(eventModel.getType())){
                            handler.doHandler(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }



    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
