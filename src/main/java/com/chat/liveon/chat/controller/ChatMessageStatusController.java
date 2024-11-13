package com.chat.liveon.chat.controller;

import com.chat.liveon.chat.dto.response.UnreadMessagesResponse;
import com.chat.liveon.chat.service.ChatMessageStatusService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatMessageStatusController {
    private final ChatMessageStatusService chatMessageStatusService;

    @PostMapping("/chat-room/{chatRoomId}/read-messages")
    public ResponseEntity<Void> markMessagesAsRead(@PathVariable("chatRoomId") Long chatRoomId, HttpSession session) {
        String personId = (String) session.getAttribute("personId");
        chatMessageStatusService.markMessagesAsRead(chatRoomId, personId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat-room/unread-messages")
    public ResponseEntity<List<UnreadMessagesResponse>> getUnreadMessages(HttpSession session) {
        String personId = (String) session.getAttribute("personId");
        List<UnreadMessagesResponse> response = chatMessageStatusService.getUnreadMessages(personId);

        return ResponseEntity.ok(response);
    }
}
