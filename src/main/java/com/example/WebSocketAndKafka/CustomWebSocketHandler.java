package com.example.WebSocketAndKafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;

@Slf4j
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String msg = message.getPayload();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getId(), session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }

    private void sendMessage(WebSocketSession session, String payload){
        TextMessage message = new TextMessage(payload);
        try {
            session.sendMessage(message);
        }catch (IOException ie){
            ie.printStackTrace();
        }
    }

    public void sendMessageToAll(String payload) {
        sessionMap.forEach((s, webSocketSession) -> this.sendMessage(webSocketSession,payload));
    }
}