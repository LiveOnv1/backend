package com.chat.liveon.chat.dto.request;

import java.time.LocalDateTime;

public record ChatMessageRequest(
        Long messageId,
        Long chatRoomId,
        Long senderId,
        String message,
        LocalDateTime timestamp,
        String profileImageUrl
) {}