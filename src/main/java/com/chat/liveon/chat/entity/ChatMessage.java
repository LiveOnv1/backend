package com.chat.liveon.chat.entity;

import com.chat.liveon.auth.entity.Person;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Person sender;

    private String message;

    private LocalDateTime sendDate;

    @Builder
    public ChatMessage(Person sender, String message, ChatRoom chatRoom) {
        this.sender = sender;
        this.message = message;
        this.chatRoom = chatRoom;
        this.sendDate = LocalDateTime.now();
    }

    public static ChatMessage createChatMessage(Person sender, String message, ChatRoom chatRoom) {
        ChatMessage chatMessage = ChatMessage.builder()
                .sender(sender)
                .message(message)
                .chatRoom(chatRoom)
                .build();
        return chatMessage;
    }
}
