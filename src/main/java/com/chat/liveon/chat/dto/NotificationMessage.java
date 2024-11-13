package com.chat.liveon.chat.dto;

public record NotificationMessage(
        Long chatRoomId,
        Long unreadCount
) {}
