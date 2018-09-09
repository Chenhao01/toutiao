package com.newcoder.toutiao.async;

import java.util.List;

/**
 * Created by 12274 on 2018/1/2.
 */
public interface EventHandler {
    void doHandler(EventModel model);
    List<EventType> getSupportEventType();
}
