package com.newcoder.toutiao.async;

/**
 * Created by 12274 on 2018/1/2.
 */
public enum EventType {
    //所有异步事件类型
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);
    //hAHA(4);

    private int value;
    EventType(int value) {
       this.value = value;
    }
    public int getValue() {
        return value;
    }
}
