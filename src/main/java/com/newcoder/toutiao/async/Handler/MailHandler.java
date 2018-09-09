package com.newcoder.toutiao.async.Handler;

import com.newcoder.toutiao.Util.MailSender;
import com.newcoder.toutiao.async.EventHandler;
import com.newcoder.toutiao.async.EventModel;
import com.newcoder.toutiao.async.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 12274 on 2018/1/6.
 */
@Component
public class MailHandler implements EventHandler{
    @Autowired
    MailSender mailSender;

    @Override
    public void doHandler(EventModel model) {
        Map<String, Object> map = new HashMap();
        mailSender.sendWithHTMLTemplate(model.getExts("email"), "点开有惊喜",
                "mails/haha.html", map);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.MAIL);
    }
}
