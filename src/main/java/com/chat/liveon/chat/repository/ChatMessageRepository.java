package com.chat.liveon.chat.repository;

import com.chat.liveon.chat.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Page<ChatMessage> findByChatRoom_IdOrderBySendDateDesc(Long roomId, Pageable pageable);
}
