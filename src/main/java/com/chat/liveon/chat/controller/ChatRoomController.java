package com.chat.liveon.chat.controller;

import com.chat.liveon.chat.dto.request.ChatRoomRequest;
import com.chat.liveon.chat.dto.response.AllChatRoomResponse;
import com.chat.liveon.chat.dto.response.ChatRoomResponse;
import com.chat.liveon.chat.service.ChatMessageStatusService;
import com.chat.liveon.chat.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ChatRoom API", description = "채팅방 API")
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatMessageStatusService chatMessageStatusService;

    @Operation(summary = "채팅방 생성 API")
    @PostMapping("/chat-room")
    public ResponseEntity<ChatRoomResponse> createChatRoom(@RequestBody ChatRoomRequest chatRoomRequest,
                                                           HttpSession session) {
        String personId = (String) session.getAttribute("personId");
        ChatRoomResponse response = chatRoomService.createChatRoom(chatRoomRequest, personId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "채팅방 조회 API")
    @GetMapping("/chat-room")
    public ResponseEntity<List<AllChatRoomResponse>> getChatRoom(HttpSession session) {
        String personId = (String) session.getAttribute("personId");
        List<AllChatRoomResponse> response = chatRoomService.getAllChatRoom(personId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat-room/{chatRoomId}/enter")
    public ResponseEntity<Void> enterChatRoom(@PathVariable("chatRoomId") Long chatRoomId, HttpSession session) {
        String personId = (String) session.getAttribute("personId");
        chatMessageStatusService.markMessagesAsRead(chatRoomId, personId);
        chatRoomService.addUserToChatRoom(chatRoomId, personId);
        return ResponseEntity.ok().build();
    }
}
