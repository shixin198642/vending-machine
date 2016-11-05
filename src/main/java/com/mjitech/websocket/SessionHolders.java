package com.mjitech.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.common.collect.Collections2;
import org.elasticsearch.common.inject.Singleton;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@Singleton
public class SessionHolders {

	private List<WebSocketSession> clients = Collections.synchronizedList(new ArrayList<WebSocketSession>());

	public synchronized void addClient(WebSocketSession session) {
		this.clients.add(session);
	}

	public synchronized void broadMessages(String msg) {
		for (WebSocketSession client : clients) {
			if (client.isOpen()) {
				try {
					client.sendMessage(new TextMessage(msg));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 

		}
	}

}
