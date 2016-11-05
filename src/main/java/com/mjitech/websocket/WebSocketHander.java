package com.mjitech.websocket;

import org.elasticsearch.common.inject.Singleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Singleton
public class WebSocketHander implements WebSocketHandler {
	
	@Autowired
	private SessionHolders sessionHolders;
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus arg1)
			throws Exception {
		

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		System.out.println("a new session established");
		sessionHolders.addClient(session);

	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> msg)
			throws Exception {
		System.out.println("a new msg gotten:"+msg.getPayload());
		
	}

	@Override
	public void handleTransportError(WebSocketSession arg0, Throwable t)
			throws Exception {
		t.printStackTrace();
		//System.err.println(t.getMessage());

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
