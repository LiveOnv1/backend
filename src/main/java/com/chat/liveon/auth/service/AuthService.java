package com.chat.liveon.auth.service;

import com.chat.liveon.auth.dto.RegisterRequest;
import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    public AuthService(final PasswordEncoder passwordEncoder, final PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Transactional
    public void register(RegisterRequest authRequest) {
        if (personRepository.findByPersonId(authRequest.personId()).isPresent()) {
            throw new RuntimeException("이미 가입된 사용자입니다.");
        }
        String encodedPassword = passwordEncoder.encrypt(authRequest.personId(), authRequest.personPassword());
        Person person = new Person(authRequest.personId(), authRequest.personName(), encodedPassword);
        personRepository.save(person);
    }

    /*@Transactional
    public void login(LoginRequest loginRequest) {
        boolean isAuthenticated = authenticate(loginRequest.personId(), loginRequest.personPassword());
        if (isAuthenticated) {
            HttpSession session = loginRequest.request().getSession(true); // 새 세션을 생성하거나 기존 세션을 반환합니다.
            session.setAttribute("personId", loginRequest.personId());

            // 세션 ID를 쿠키에 설정합니다.
            String sessionId = session.getId();
            javax.servlet.http.Cookie sessionCookie = new javax.servlet.http.Cookie("SESSIONID", sessionId);
            sessionCookie.setHttpOnly(true); // 보안 설정
            sessionCookie.setPath("/"); // 쿠키의 유효 경로 설정
            loginRequest.

            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        }

    private boolean authenticate(String personId, String personPassword) {
        return personRepository.findByPersonName(personId)
                .map(user -> passwordEncoder.matches(personPassword, user.personPassword()))
                .orElse(false);
    }*/

}
