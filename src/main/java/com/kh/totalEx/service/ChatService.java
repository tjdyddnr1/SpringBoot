package com.kh.totalEx.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.totalEx.DTO.ChatMessageDTO;
import com.kh.totalEx.DTO.ChatRoomResDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    private Map<String, ChatRoomResDTO> chatRooms; // 채팅방 정보를 담을 맵

    @PostConstruct // 의존성 주입 이후 초기화를 수행하는 메소드
    private void init() { // 채팅방 정보를 담을 맵을 초기화
        chatRooms = new LinkedHashMap<>(); // 채팅방 정보를 담을 맵
    }

    public List<ChatRoomResDTO> findAllRoom() { // 채팅방 리스트 반환
        return new ArrayList<>(chatRooms.values());
    }

    public ChatRoomResDTO findRoomById(String roomId) {
        return chatRooms.get(roomId);
    }
    // 방 개설하기
    public ChatRoomResDTO createRoom(String name) {
        String randomId = UUID.randomUUID().toString();
        log.info("UUID : " + randomId);
        ChatRoomResDTO chatRoom = ChatRoomResDTO.builder() // 채팅방 생성
                .roomId(randomId)
                .name(name)
                .regDate(LocalDateTime.now())
                .build();
        chatRooms.put(randomId, chatRoom);  // 방 생성, 키를 UUID로 하고 방 정보를 값으로 저장
        return chatRoom;
    }


    // 방 삭제
    public void removeRoom(String roomId) {
        ChatRoomResDTO room = chatRooms.get(roomId); // 방 정보 가져오기
        if (room != null) { // 방이 존재하면
            if (room.isSessionEmpty()) { // 방에 세션이 없으면
                chatRooms.remove(roomId); // 방 삭제
            }
        }
    }
    // 채팅방에 입장한 세션 추가
    public void addSessionAndHandleEnter(String roomId, WebSocketSession session, ChatMessageDTO chatMessage) {
        ChatRoomResDTO room = findRoomById(roomId);
        if (room != null) {
            room.getSessions().add(session); // 채팅방에 입장한 세션 추가
            if (chatMessage.getSender() != null) { // 채팅방에 입장한 사용자가 있으면
                chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
                sendMessageToAll(roomId, chatMessage); // 채팅방에 입장 메시지 전송
            }
            log.debug("New session added: " + session);
        }
    }
    // 채팅방에서 퇴장한 세션 제거
    public void removeSessionAndHandleExit(String roomId, WebSocketSession session, ChatMessageDTO chatMessage) {
        ChatRoomResDTO room = findRoomById(roomId); // 채팅방 정보 가져오기
        if (room != null) {
            room.getSessions().remove(session); // 채팅방에서 퇴장한 세션 제거
            if (chatMessage.getSender() != null) { // 채팅방에서 퇴장한 사용자가 있으면
                chatMessage.setMessage(chatMessage.getSender() + "님이 퇴장했습니다.");
                sendMessageToAll(roomId, chatMessage); // 채팅방에 퇴장 메시지 전송
            }
            log.debug("Session removed: " + session);
            if (room.isSessionEmpty()) {
                removeRoom(roomId);
            }
        }
    }

    // 메세지 보내기
    public void sendMessageToAll(String roomId, ChatMessageDTO message) {
        ChatRoomResDTO room = findRoomById(roomId);
        if (room != null) {
            for (WebSocketSession session : room.getSessions()) {
                sendMessage(session, message);
            }
        }
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch(IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
