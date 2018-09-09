package com.newcoder.toutiao.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by 12274 on 2017/11/16.
 */
@Aspect
@Component
public class logAspect {
    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(logAspect.class);
    @Before("execution(* com.newcoder.toutiao.controller.controller.*(..))")
    public void before(JoinPoint joinPoint){
        StringBuilder sbr=new StringBuilder();
        for(Object arg:joinPoint.getArgs()){
            sbr.append("arg:"+arg+"|");
        }
        logger.info("begin time:"+new Date());
        logger.info("before:"+sbr.toString());
    }
    @After("execution(* com.newcoder.toutiao.controller.controller.*(..))")
    public void after(JoinPoint joinPoint){
        logger.info("after:");
        logger.info("end time:"+new Date());
    }
}
