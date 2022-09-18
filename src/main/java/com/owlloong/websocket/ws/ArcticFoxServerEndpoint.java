package com.owlloong.websocket.ws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author owl-loong Yuan <br/>
 * @version 1.0.0 <br/>
 * @ClassName ArcticFoxServerEndpoint.java <br/>
 * @Description TODO <br/>
 * @CreateTime 2022-09-18 17:24:00
 */
@Slf4j
@Component("webSocketServer")
@ServerEndpoint("/webSocket/{userId}")
public class ArcticFoxServerEndpoint {

    private Session session;

    private String userId;

    private static Integer ONLINE_COUNT = 0;

    private static final CopyOnWriteArraySet<ArcticFoxServerEndpoint> webSocketSet = new CopyOnWriteArraySet<>();

    private static final ConcurrentHashMap<String, ArcticFoxServerEndpoint> webSocketMap = new ConcurrentHashMap<>();

    private final static List<Session> SESSIONS = Collections.synchronizedList(new ArrayList<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        webSocketSet.add(this);
        SESSIONS.add(session);
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);
        } else {
            webSocketMap.put(userId, this);
            addOnlineCount();
        }
        log.info("[连接ID:{}] 建立连接, 当前连接数:{}", this.userId, webSocketMap.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        if (webSocketMap.containsKey(userId)) {
            webSocketMap.remove(userId);
            subOnlineCount();
        }
        log.info("[连接ID:{}] 断开连接, 当前连接数:{}", userId, webSocketMap.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("[连接ID:{}] 错误原因:{}", this.userId, error.getMessage());
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {

        log.info("[连接ID:{}] 收到消息:{}", this.userId, message);

    }

    public void sendMessage(String message, Long userId) {
        ArcticFoxServerEndpoint arcticFoxServerEndpoint = webSocketMap.get(String.valueOf(userId));

        if (null != arcticFoxServerEndpoint) {

            log.info("【websocket消息】推送消息, message={}", message);

            try {
                arcticFoxServerEndpoint.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMassMessage(String message) {

        try {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                    log.info("[连接ID:{}] 发送消息:{}", session.getRequestParameterMap().get("userId"), message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    /**
     * 获取当前连接数
     */
    public static synchronized int getOnlineCount() {
        return ONLINE_COUNT;
    }

    /**
     * 当前连接数加一
     */
    public static synchronized void addOnlineCount() {
        ArcticFoxServerEndpoint.ONLINE_COUNT++;
    }

    /**
     * 当前连接数减一
     */
    public static synchronized void subOnlineCount() {
        ArcticFoxServerEndpoint.ONLINE_COUNT--;
    }

}
