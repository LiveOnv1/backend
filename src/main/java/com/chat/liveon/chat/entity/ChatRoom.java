package com.chat.liveon.chat.entity;

import com.chat.liveon.auth.entity.Person;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String chatRoomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @Builder
    public ChatRoom(String chatRoomName, Person person) {
        this.chatRoomName = chatRoomName;
        this.person = person;
    }
}