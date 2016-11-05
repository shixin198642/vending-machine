package com.mjitech.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
// 开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private WebSocketHandler webSocketHandler;
	@Value("${enable_websocket}")
	private boolean enableWebSocket;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		if(enableWebSocket){
			System.out.println("init websocket...");
			registry.addHandler(webSocketHandler, "/serversocket").addInterceptors(
					new HandshakeInterceptor());
		}
		
	}

}
