package com.chat.liveon.auth.controller;

import com.chat.liveon.auth.dto.LoginRequest;
import com.chat.liveon.auth.dto.RegisterRequest;
import com.chat.liveon.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService personService) {
        this.authService = personService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest authRequest) {
        log.info("[가입 요청] 아이디: {} / 이름: {}", authRequest.personId(), authRequest.personName());
        authService.register(authRequest);
        log.info("[가입 성공] 아이디: {} / 이름: {}", authRequest.personId(), authRequest.personName());
        return ResponseEntity.ok("가입 성공");
    }

    /*@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        log.info("[로그인 요청] 이메일: {}", loginRequest.personId());
        authService.login(loginRequest);
        log.info("[로그인 성공] 이메일: {}", loginRequest.personId());
        return ResponseEntity.ok("로그인 성공");
    }*/



}
