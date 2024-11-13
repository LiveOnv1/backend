package com.chat.liveon.chat.entity;

import com.chat.liveon.auth.entity.Person;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "is_read")
    private boolean read;

    @Builder
    public ChatMessageStatus(ChatMessage chatMessage, Person person) {
        this.chatMessage = chatMessage;
        this.person = person;
        this.read = false;
    }

    public void read() {
        this.read = true;
    }
}
