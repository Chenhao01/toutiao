package com.newcoder.toutiao.configuration;

import com.newcoder.toutiao.interceptor.LoginRequireInterceptor;
import com.newcoder.toutiao.interceptor.PassportInterceptor;
import com.newcoder.toutiao.interceptor.testInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 12274 on 2017/12/4.
 */
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequireInterceptor loginRequireInterceptor;

    @Autowired
    testInterceptor testI;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        //registry.addInterceptor(testI).addPathPatterns("/vm");
        registry.addInterceptor(loginRequireInterceptor).addPathPatterns("/setting*");
        super.addInterceptors(registry);
    }
}
