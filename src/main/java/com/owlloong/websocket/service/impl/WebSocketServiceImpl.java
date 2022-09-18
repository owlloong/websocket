package com.owlloong.websocket.service.impl;

import com.owlloong.websocket.entity.User;
import com.owlloong.websocket.service.IWebSocketService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author owl-loong Yuan <br/>
 * @version 1.0.0 <br/>
 * @ClassName WebSocketImpl.java <br/>
 * @Description TODO <br/>
 * @CreateTime 2022-09-18 19:35:00
 */
@Service("webSocketService")
public class WebSocketServiceImpl implements IWebSocketService {

    private static final Map<Integer, User> map = new HashMap<>();

    static {
        User user = new User();
        user.setUserId(1);
        user.setName("张三");
        user.setAge(18);
        user.setAddress("紫禁城");
        map.put(user.getUserId(), user);
        user = new User();
        user.setUserId(2);
        user.setName("李四");
        user.setAge(18);
        user.setAddress("开封府");
        map.put(user.getUserId(), user);
        user = new User();
        user.setUserId(3);
        user.setName("王五");
        user.setAge(41);
        user.setAddress("六扇门");
        map.put(user.getUserId(), user);
        user = new User();
        user.setUserId(4);
        user.setName("赵六");
        user.setAge(35);
        user.setAddress("哒哒哒");
        map.put(user.getUserId(), user);
    }

    @Override
    public User getUser(Integer userId) {
        return map.get(userId);
    }


}
