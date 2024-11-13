package com.chat.liveon.chat.service;

import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import com.chat.liveon.chat.dto.NotificationMessage;
import com.chat.liveon.chat.dto.request.ChatMessageRequest;
import com.chat.liveon.chat.dto.response.ChatMessageResponse;
import com.chat.liveon.chat.entity.ChatMessage;
import com.chat.liveon.chat.entity.ChatMessageStatus;
import com.chat.liveon.chat.entity.ChatRoom;
import com.chat.liveon.chat.repository.ChatMessageRepository;
import com.chat.liveon.chat.repository.ChatMessageStatusRepository;
import com.chat.liveon.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final PersonRepository personRepository;
    private final ChatMessageStatusRepository chatMessageStatusRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public ChatMessageResponse sendMessage(Long roomId, ChatMessageRequest messageRequest) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("채팅방이 없음: " + roomId));
        Person sender = personRepository.findByPersonId(messageRequest.personId())
                .orElseThrow(() -> new RuntimeException("사용자가 없음: " + messageRequest.personId()));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .message(messageRequest.message())
                .sender(sender)
                .build();

        chatMessage = chatMessageRepository.save(chatMessage);
        log.info("[메시지 전송 성공] 채팅방: {}, 사용자: {}, 메시지: {}", roomId, sender.getPersonName(), messageRequest.message());

        Set<Person> participants = chatRoom.getParticipants();
        for (Person person : participants) {
            if (!person.getPersonId().equals(sender.getPersonId())) {
                ChatMessageStatus messageStatus = ChatMessageStatus.builder()
                        .chatMessage(chatMessage)
                        .person(person)
                        .build();
                chatMessageStatusRepository.save(messageStatus);
                long unreadCount = chatMessageStatusRepository.countByChatMessage_ChatRoom_IdAndPerson_PersonIdAndReadFalse(roomId, person.getPersonId());
                NotificationMessage notification = new NotificationMessage(roomId, unreadCount);
                messagingTemplate.convertAndSend("/topic/notifications/" + person.getPersonId(), notification);
            }
        }

        return new ChatMessageResponse(
                chatMessage.getChatRoom().getId(),
                sender.getPersonName(),
                chatMessage.getMessage(),
                chatMessage.getSendDate()
        );
    }

    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessagesByRoomId(Long roomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ChatMessage> messagesPage = chatMessageRepository.findByChatRoom_IdOrderBySendDateDesc(roomId, pageable);

        return messagesPage.stream()
                .map(message -> new ChatMessageResponse(
                        message.getChatRoom().getId(),
                        message.getSender().getPersonName(),
                        message.getMessage(),
                        message.getSendDate()))
                .collect(Collectors.toList());
    }
}
