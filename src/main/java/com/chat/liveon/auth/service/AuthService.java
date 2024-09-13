package com.chat.liveon.auth.service;

import com.chat.liveon.auth.dto.LoginRequest;
import com.chat.liveon.auth.dto.RegisterRequest;
import com.chat.liveon.auth.entity.Person;
import com.chat.liveon.auth.exception.AuthenticationFailureException;
import com.chat.liveon.auth.repository.PersonRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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

    @Transactional
    public void login(LoginRequest loginRequest) {
        boolean isAuthenticated = authenticate(loginRequest.personId(), loginRequest.personPassword());
        if (!isAuthenticated) {
            throw new AuthenticationFailureException("로그인에 실패했습니다.");
        }
            HttpSession session = loginRequest.request().getSession(true);
            session.setAttribute("personId", loginRequest.personId());

            String sessionId = session.getId();

            jakarta.servlet.http.Cookie sessionCookie = new jakarta.servlet.http.Cookie("SESSIONID", sessionId);
            sessionCookie.setHttpOnly(true);
            sessionCookie.setPath("/");
            sessionCookie.setMaxAge(60 * 60);

            loginRequest.response().addCookie(sessionCookie);
            loginRequest.response().setStatus(HttpServletResponse.SC_OK);
    }

    @Transactional
    public void logout(HttpSession session) {
        session.invalidate();
    }

    private boolean authenticate(final String personId, final String personPassword) {
        return personRepository.findByPersonId(personId)
                .map(user -> {
                    try {
                        return passwordEncoder.matches(personId, personPassword, user.getPersonPassword());
                    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(false);
    }
}
