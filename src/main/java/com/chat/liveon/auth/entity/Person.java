package com.chat.liveon.auth.entity;

import com.chat.liveon.chat.entity.ChatRoom;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String personId;
    private String personName;
    private String personPassword;
    private Role role;
    private String profilePicture;

    @ManyToMany(mappedBy = "participants")
    private Set<ChatRoom> chatRooms = new HashSet<>();

    public Person(String personId, String personName, String encodedPassword, Role role, String profilePicture) {
        this.personId = personId;
        this.personName = personName;
        this.personPassword = encodedPassword;
        this.role = Role.ROLE_USER;
        this.profilePicture = profilePicture;
    }
}
