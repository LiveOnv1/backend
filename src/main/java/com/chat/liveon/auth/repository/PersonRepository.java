package com.chat.liveon.auth.repository;

import com.chat.liveon.auth.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByPersonId(String personId);
}
