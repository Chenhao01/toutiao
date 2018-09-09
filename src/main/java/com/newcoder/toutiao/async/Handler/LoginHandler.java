package com.newcoder.toutiao.async.Handler;

import com.newcoder.toutiao.Util.MailSender;
import com.newcoder.toutiao.async.EventHandler;
import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventType;
import com.newcoder.toutiao.service.MassageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 12274 on 2018/1/2.
 */
//@Component
public class LoginHandler implements EventHandler {
    @Autowired
    MailSender mailSender;
    @Autowired
    MassageService massageService;
    @Override
    public void doHandler(EventModel model) {
        Map<String, Object> map = new HashMap();
        map.put("username", model.getExts("username"));
        mailSender.sendWithHTMLTemplate(model.getExts("email"), "登陆异常",
                "mails/welcome.html", map);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
