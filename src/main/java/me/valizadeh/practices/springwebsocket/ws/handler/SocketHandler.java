package me.valizadeh.practices.springwebsocket.ws.handler;

import com.google.gson.Gson;
import me.valizadeh.practices.springwebsocket.exception.SendMessageException;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class SocketHandler extends TextWebSocketHandler {

	private static SocketHandler INSTANCE;
	public static Gson JSON_CONVERTER;
	private List<WebSocketSession> sessions;


	public SocketHandler() {
		INSTANCE = this;
		JSON_CONVERTER = new Gson();
		sessions = new CopyOnWriteArrayList<>();
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws InterruptedException, IOException {

		for(WebSocketSession webSocketSession : sessions) {
			Map value = new Gson().fromJson(message.getPayload(), Map.class);
			webSocketSession.sendMessage(new TextMessage("Hello " + value.get("name") + " !"));
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		sessions.remove(session);
	}

	private List<WebSocketSession> getSessions() {
		return sessions;
	}

	public static <T> void sendMessage(T payLoad) {
		try {
			final String json = JSON_CONVERTER.toJson(payLoad);
			for(WebSocketSession webSocketSession : INSTANCE.getSessions()) {
				webSocketSession.sendMessage(new TextMessage(json));
			}
		} catch (IOException e) {
			throw new SendMessageException(e);
		}
	}
}