package com.owlloong.websocket.service;

import com.owlloong.websocket.entity.User;

/**
 * @author owl-loong Yuan <br/>
 * @version 1.0.0 <br/>
 * @ClassName IWebSocket.java <br/>
 * @Description TODO <br/>
 * @CreateTime 2022-09-18 19:32:00
 */
public interface IWebSocketService {

    User getUser(Integer userId);

}
