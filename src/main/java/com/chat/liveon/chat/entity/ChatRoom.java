package com.chat.liveon.chat.entity;

import com.chat.liveon.auth.entity.Person;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @Column(nullable = false)
    private String chatRoomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToMany
    @JoinTable(name = "chat_room_participants",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private Set<Person> participants = new HashSet<>();

    @Builder
    public ChatRoom(String chatRoomName, Person person) {
        this.chatRoomName = chatRoomName;
        this.person = person;
    }

    public void addParticipant(Person person) {
        participants.add(person);
        person.getChatRooms().add(this);
    }
}
