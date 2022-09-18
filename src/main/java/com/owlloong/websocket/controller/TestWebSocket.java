package com.owlloong.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.owlloong.websocket.entity.User;
import com.owlloong.websocket.service.IWebSocketService;
import com.owlloong.websocket.ws.ArcticFoxServerEndpoint;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tqf
 * @Description
 * @Version 1.0
 * @since 2022-04-12 15:44
 */
@RestController
@RequestMapping("/web")
public class TestWebSocket {
    @Resource
    private ArcticFoxServerEndpoint webSocketServer;

    @Resource(name = "webSocketService")
    private IWebSocketService webSocketService;

    /**
     * 消息发送测试
     */
    @GetMapping("/test")
    public void test() {
        for (int i = 1; i < 10; i++) {
            WebsocketResponse response = new WebsocketResponse();
            response.setUserId(String.valueOf(i));
            response.setUserName("姓名" + i);
            response.setAge(i);
            webSocketServer.sendMessage(JSONObject.toJSONString(response), Long.valueOf(String.valueOf(i)));
        }


    }

    /**
     * 群发消息测试(给当前连接用户发送)
     */
    @GetMapping("/sendMassMessage")
    public void sendMassMessage() {
        WebsocketResponse response = new WebsocketResponse();
        response.setUserName("群发消息模板测试");
        webSocketServer.sendMassMessage(JSONObject.toJSONString(response));
    }

    @GetMapping("/sendMessageToWeb/{userId}")
    public void sendMessageToWeb(@PathVariable String userId) {
        for (int i = 1; i < 5; i++) {
            User user = webSocketService.getUser(i);
            webSocketServer.sendMessage(JSONObject.toJSONString(user), Long.valueOf(userId));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Data
    @Accessors(chain = true)
    public static class WebsocketResponse {
        private String userId;
        private String userName;
        private int age;
    }

}