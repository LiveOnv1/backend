package com.chat.liveon.chat.service;

import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import com.chat.liveon.chat.dto.request.ChatRoomRequest;
import com.chat.liveon.chat.dto.response.AllChatRoomResponse;
import com.chat.liveon.chat.dto.response.ChatRoomResponse;
import com.chat.liveon.chat.entity.ChatRoom;
import com.chat.liveon.chat.repository.ChatRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final PersonRepository personRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, PersonRepository personRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest chatRoomRequest, String person) {
        Person personId = personRepository.findByPersonId(person).orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
        log.info("[채팅방 생성 요청] 사용자: {}", personId);

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(chatRoomRequest.chatRoomName())
                .person(personId)
                .build();

        chatRoom = chatRoomRepository.save(chatRoom);
        log.info("[채팅방 생성 성공] 사용자: {}, 채팅방명: {}", person, chatRoomRequest.chatRoomName());
        return new ChatRoomResponse(chatRoom.getId(), chatRoomRequest.chatRoomName(), personId.getId());
    }

    @Transactional(readOnly = true)
    public List<AllChatRoomResponse> getAllChatRoom() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        return chatRooms.stream()
                .map(chatRoom -> new AllChatRoomResponse(chatRoom.getId(), chatRoom.getChatRoomName()))
                .collect(Collectors.toList());
    }
}
