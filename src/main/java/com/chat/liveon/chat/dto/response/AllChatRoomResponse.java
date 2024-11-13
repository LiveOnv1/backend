package com.chat.liveon.chat.dto.response;

public record AllChatRoomResponse(
        Long chatRoomId,
        String chatRoomName,
        Long unreadCount
) {}
