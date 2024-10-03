package com.chat.liveon.chat.dto.request;

import java.time.LocalDateTime;

public record ChatMessageRequest(
        String personId,
        String message,
        LocalDateTime timestamp
) {}
