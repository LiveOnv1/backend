package com.chat.liveon.chat.dto.request;

import java.time.LocalDateTime;

public record ChatMessageRequest(
        Long messageId,
        Long chatRoomId,
        String senderName,
        String message,
        LocalDateTime timestamp
) {}