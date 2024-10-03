package com.chat.liveon.chat.repository;

import com.chat.liveon.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    @NonNull
    Optional<ChatRoom> findById(Long roomId);
}