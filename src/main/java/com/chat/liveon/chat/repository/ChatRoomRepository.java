package com.chat.liveon.chat.repository;

import com.chat.liveon.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long roomId);

    List<ChatRoom> findByParticipants_PersonId(@Param("personId") String personId);
}