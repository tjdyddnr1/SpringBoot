package com.kh.totalEx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.totalEx.DTO.ChatMessageDTO;
import com.kh.totalEx.DTO.ChatRoomResDTO;
import com.kh.totalEx.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component

public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>();
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload(); // 클라이언트가 전송한 메시지
        log.warn("{}", payload);
        // JSON 문자열을 ChatMessageDto 객체로 변환
        ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);
        String roomId = chatMessage.getRoomId(); // 채팅방 ID
        // 세션과 채팅방 ID를 매핑
        sessionRoomIdMap.put(session, chatMessage.getRoomId()); // 세션과 채팅방 ID를 매핑
        if (chatMessage.getType() == ChatMessageDTO.MessageType.ENTER) { // 메시지 타입이 ENTER이면
            chatService.addSessionAndHandleEnter(roomId, session, chatMessage); // 채팅방에 입장한 세션 추가
        } else if (chatMessage.getType() == ChatMessageDTO.MessageType.CLOSE) {
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        } else {
            chatService.sendMessageToAll(roomId, chatMessage);
        }

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션과 매핑된 채팅방 ID 가져오기
        log.warn("afterConnectionClosed : {}", session);
        String roomId = sessionRoomIdMap.remove(session);
        if (roomId != null) {
            ChatMessageDTO chatMessage = new ChatMessageDTO();
            chatMessage.setType(ChatMessageDTO.MessageType.CLOSE);
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        }
    }
}
