package com.owlloong.websocket.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @author owl-loong Yuan <br/>
 * @version 1.0.0 <br/>
 * @ClassName WebsocketConfiguration.java <br/>
 * @Description TODO <br/>
 * @CreateTime 2022-09-18 17:21:00
 */
@Configuration
public class WebsocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
