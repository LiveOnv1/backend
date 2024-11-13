package com.chat.liveon.chat.controller;

import com.chat.liveon.chat.dto.request.ChatMessageRequest;
import com.chat.liveon.chat.dto.response.ChatMessageResponse;
import com.chat.liveon.chat.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/chatroom/{roomId}")
    public ChatMessageResponse sendMessage(@DestinationVariable("roomId") String roomId, ChatMessageRequest message) {
        Long roomIdLong = Long.parseLong(roomId);

        return chatMessageService.sendMessage(roomIdLong, message);
    }

    @Operation(summary = "이전 채팅 메시지 조회 API")
    @GetMapping("/chat-room/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable("roomId") Long roomId, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        List<ChatMessageResponse> messages = chatMessageService.getMessagesByRoomId(roomId, page, size);

        return ResponseEntity.ok(messages);
    }
}
