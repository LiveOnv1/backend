package com.chat.liveon.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String personId;
    private String personName;
    private String personPassword;
    private Role role;

    public Person(String personId, String personName, String encodedPassword, Role role) {
        this.personId = personId;
        this.personName = personName;
        this.personPassword = encodedPassword;
        this.role = Role.ROLE_USER;
    }

    public Person() {
    }

}
