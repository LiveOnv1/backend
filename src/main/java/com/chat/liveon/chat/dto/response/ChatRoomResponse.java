package com.chat.liveon.chat.dto.response;

public record ChatRoomResponse(
        Long chatRoomId,
        String chatRoomName,
        Long creatorId
) {}
