package com.chat.liveon.auth.service;

import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import com.chat.liveon.chat.service.ChatRoomService;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person findByPersonId(Long personId) {
        return personRepository.findById(personId).orElseThrow();
    }
}
