package com.chat.liveon.chat.entity;

import com.chat.liveon.auth.entity.Person;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class ChatMessageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id", nullable = false)
    private ChatMessage message;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

}
