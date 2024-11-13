package com.chat.liveon.chat.dto.response;

public record UnreadMessagesResponse(
        Long chatRoomId,
        String chatRoomName,
        Long unreadCount
) {}