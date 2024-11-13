package com.chat.liveon.chat.service;

import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import com.chat.liveon.chat.dto.request.ChatRoomRequest;
import com.chat.liveon.chat.dto.response.AllChatRoomResponse;
import com.chat.liveon.chat.dto.response.ChatRoomResponse;
import com.chat.liveon.chat.entity.ChatRoom;
import com.chat.liveon.chat.repository.ChatMessageStatusRepository;
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
    private final ChatMessageStatusRepository chatMessageStatusRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, PersonRepository personRepository, ChatMessageStatusRepository chatMessageStatusRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.personRepository = personRepository;
        this.chatMessageStatusRepository = chatMessageStatusRepository;
    }

    @Transactional
    public ChatRoomResponse createChatRoom(ChatRoomRequest chatRoomRequest, String person) {
        Person personId = personRepository.findByPersonId(person).orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));
        log.info("[채팅방 생성 요청] 사용자: {}", personId);

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomName(chatRoomRequest.chatRoomName())
                .person(personId)
                .build();
        chatRoom.addParticipant(personId);
        addAllUsersToChatRoom(chatRoom);
        chatRoom = chatRoomRepository.save(chatRoom);

        return new ChatRoomResponse(chatRoom.getId(), chatRoomRequest.chatRoomName(), personId.getId());
    }

    @Transactional(readOnly = true)
    public List<AllChatRoomResponse> getAllChatRoom(String personId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByParticipants_PersonId(personId);
        return chatRooms.stream()
                .map(chatRoom -> {
                    Long unreadCount = chatMessageStatusRepository.countByChatMessage_ChatRoom_IdAndPerson_PersonIdAndReadFalse(chatRoom.getId(), personId);
                    return new AllChatRoomResponse(chatRoom.getId(), chatRoom.getChatRoomName(), unreadCount);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void addUserToChatRoom(Long chatRoomId, String personId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new RuntimeException("채팅방을 찾을 수 없습니다. ID: " + chatRoomId));
        Person person = personRepository.findByPersonId(personId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다. personId: " + personId));

        if (!chatRoom.getParticipants().contains(person)) {
            chatRoom.addParticipant(person);
            chatRoomRepository.save(chatRoom);
        }
    }

    @Transactional
    public void addUserToAllChatRooms(Person user) {
        List<ChatRoom> allChatRooms = chatRoomRepository.findAll();
        for (ChatRoom chatRoom : allChatRooms) {
            if (!chatRoom.getParticipants().contains(user)) {
                chatRoom.addParticipant(user);
            }
        }
        chatRoomRepository.saveAll(allChatRooms);
    }

    private void addAllUsersToChatRoom(ChatRoom chatRoom) {
        List<Person> allUsers = personRepository.findAll();
        for (Person user : allUsers) {
            if (!chatRoom.getParticipants().contains(user)) {
                chatRoom.addParticipant(user);
            }
        }
    }
}
