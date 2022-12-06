package com.example.WebSocketAndKafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private static LinkedHashSet<WebSocketSession> numSet = new LinkedHashSet<>();

    private final Producer producer;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        if(numSet.size()>=3){
            WebSocketSession oldSession = numSet.iterator().next();
            oldSession.sendMessage(new TextMessage("채팅이 종료되었습니다."));
            numSet.remove(numSet.iterator().next());
        }

        boolean isSessionAlive = false;

        for(WebSocketSession sess: numSet) {
            if(sess.getId().equals(session.getId())){
                isSessionAlive = true;
            }
        }
        if(isSessionAlive){
            producer.produceMessage("kafkaTest", payload);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        numSet.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        numSet.remove(session);
    }

    public void sendMessage(String payload) throws Exception {
        for(WebSocketSession sess: numSet) {
            TextMessage msg = new TextMessage(payload);
            sess.sendMessage(msg);
        }
    }
}