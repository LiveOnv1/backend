package com.chat.liveon.chat.controller;

import com.chat.liveon.chat.dto.request.ChatMessageRequest;
import com.chat.liveon.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/public/{roomId}")
    public ChatMessageRequest sendMessage(@DestinationVariable Long roomId, ChatMessageRequest message) {
        return message;
    }

}
