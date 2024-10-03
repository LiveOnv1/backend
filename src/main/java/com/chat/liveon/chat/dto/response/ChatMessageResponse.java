package com.chat.liveon.chat.dto.response;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long chatRoomId,
        String personId,
        String message,
        LocalDateTime timestamp
) {}

