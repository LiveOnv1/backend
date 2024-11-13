package com.chat.liveon.chat.repository;

import com.chat.liveon.chat.dto.response.UnreadMessagesResponse;
import com.chat.liveon.chat.entity.ChatMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageStatusRepository extends JpaRepository<ChatMessageStatus, Long> {
    Long countByChatMessage_ChatRoom_IdAndPerson_PersonIdAndReadFalse(@Param("chatRoomId") Long chatRoomId, @Param("personId") String personId);

    @Query("SELECT cms.chatMessage.chatRoom.id, cms.chatMessage.chatRoom.chatRoomName, COUNT(cms) " +
            "FROM ChatMessageStatus cms " +
            "WHERE cms.person.personId = :personId AND cms.read = false " +
            "GROUP BY cms.chatMessage.chatRoom.id, cms.chatMessage.chatRoom.chatRoomName")
    List<UnreadMessagesResponse> countUnreadMessagesByChatRoomAndPerson(@Param("personId") String personId);

    List<ChatMessageStatus> findByChatMessage_ChatRoom_IdAndPerson_PersonIdAndReadFalse(Long chatRoomId, String personId);

}

