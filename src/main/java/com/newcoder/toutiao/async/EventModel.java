package com.newcoder.toutiao.async;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 12274 on 2018/1/2.
 */
//异步事件模型
public class EventModel {
    private EventType type;
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;
    private Map<String,String> exts=new HashMap<String,String>();//封装需要后续异步处理的数据

    public EventModel(){

    }
    public EventModel(EventType type){
        this.type=type;
    }

    public EventModel setExts(String key,String value){
        exts.put(key,value);
        return this;
    }
    public String getExts(String key){
        return exts.get(key);
    }
    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
