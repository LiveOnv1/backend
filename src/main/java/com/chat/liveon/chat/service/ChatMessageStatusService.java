package com.chat.liveon.chat.service;

import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import com.chat.liveon.chat.dto.NotificationMessage;
import com.chat.liveon.chat.dto.response.UnreadMessagesResponse;
import com.chat.liveon.chat.entity.ChatMessageStatus;
import com.chat.liveon.chat.repository.ChatMessageStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageStatusService {

    private final ChatMessageStatusRepository chatMessageStatusRepository;
    private final PersonRepository personRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Transactional
    public void markMessagesAsRead(Long chatRoomId, String personId) {
        Person person = personRepository.findByPersonId(personId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + personId));

        List<ChatMessageStatus> unreadMessages = chatMessageStatusRepository.findByChatMessage_ChatRoom_IdAndPerson_PersonIdAndReadFalse(chatRoomId, personId);
        for (ChatMessageStatus messageStatus : unreadMessages) {
            messageStatus.read();
        }
        chatMessageStatusRepository.saveAll(unreadMessages);

        long updatedUnreadCount = chatMessageStatusRepository
                .countByChatMessage_ChatRoom_IdAndPerson_PersonIdAndReadFalse(chatRoomId, personId);
        NotificationMessage notification = new NotificationMessage(chatRoomId, updatedUnreadCount);
        messagingTemplate.convertAndSend("/topic/notifications/" + person.getPersonId(), notification);
    }

    @Transactional(readOnly = true)
    public List<UnreadMessagesResponse> getUnreadMessages(String personId) {
        return chatMessageStatusRepository.countUnreadMessagesByChatRoomAndPerson(personId);
    }
}

