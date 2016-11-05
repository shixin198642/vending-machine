package com.mjitech.websocket;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;

public class HandshakeInterceptor implements
		org.springframework.web.socket.server.HandshakeInterceptor {

	@Override
	public void afterHandshake(ServerHttpRequest arg0, ServerHttpResponse arg1,
			WebSocketHandler handler, Exception arg3) {
		
		System.out.println("after hand shake");
	}

	@Override
	public boolean beforeHandshake(ServerHttpRequest arg0,
			ServerHttpResponse arg1, WebSocketHandler arg2,
			Map<String, Object> arg3) throws Exception {
		System.out.println("before hand shake");
		return true;
	}

}
