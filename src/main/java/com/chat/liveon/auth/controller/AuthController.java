package com.chat.liveon.auth.controller;

import com.chat.liveon.auth.dto.LoginRequest;
import com.chat.liveon.auth.dto.RegisterRequest;
import com.chat.liveon.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
@Tag(name = "Auth API", description = "가입, 로그인, 로그아웃 API")
public class AuthController {
    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @Operation(summary = "회원 가입", description = "가입 요청, 등록")
    public ResponseEntity<String> register(@RequestBody RegisterRequest authRequest) {
        log.info("[가입 요청] 아이디: {} / 이름: {}", authRequest.personId(), authRequest.personName());
        authService.register(authRequest);
        log.info("[가입 성공] 아이디: {} / 이름: {}", authRequest.personId(), authRequest.personName());
        return ResponseEntity.ok("가입 성공");
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인 요청")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        log.info("[로그인 요청] 아이디: {}", loginRequest.personId());
        authService.login(loginRequest, request, response);
        log.info("[로그인 성공] 아이디: {}", loginRequest.personId());
        return ResponseEntity.ok("로그인 성공");
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "로그아웃 요청")
    public ResponseEntity<String> logout(HttpSession session) {
        log.info("[로그아웃 요청] 아이디: {}", session.getAttribute("personId"));
        authService.logout(session);
        return ResponseEntity.ok("로그아웃 성공");
    }

}
