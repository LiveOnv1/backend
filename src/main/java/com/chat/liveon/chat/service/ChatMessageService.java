package com.chat.liveon.chat.service;

import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import com.chat.liveon.chat.dto.request.ChatMessageRequest;
import com.chat.liveon.chat.entity.ChatMessage;
import com.chat.liveon.chat.entity.ChatRoom;
import com.chat.liveon.chat.repository.ChatMessageRepository;
import com.chat.liveon.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PersonRepository personRepository;

    @Transactional
    public ChatMessageRequest sendMessage(Long roomId, ChatMessageRequest messageRequest) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("ChatRoom not found"));

        Person sender = personRepository.findByPersonId(messageRequest.senderName())
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .message(messageRequest.message())
                .sender(sender)
                .build();

        chatMessage = chatMessageRepository.save(chatMessage);

        log.info("[메시지 전송 성공] 채팅방: {}, 사용자: {}, 메시지: {}", roomId, sender.getPersonName(), messageRequest.message());

        return new ChatMessageRequest(
                chatMessage.getId(),
                chatMessage.getChatRoom().getId(),
                sender.getPersonName(),
                chatMessage.getMessage(),
                chatMessage.getSendDate()
        );

    }
}
