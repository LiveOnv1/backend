package com.chat.liveon.auth.controller;

import com.chat.liveon.auth.dto.RegisterRequest;
import com.chat.liveon.auth.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("회원가입 성공")
    void register() {
        // Given
        String personId = "testPersonId";
        String personName = "testPersonName";
        String personPassword = "testPersonPassword";
        RegisterRequest request = new RegisterRequest(personId, personName, personPassword);

        // When
        doNothing().when(authService).register(request);

        // Execute
        ResponseEntity<String> response = authController.register(request);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("가입 성공", response.getBody(), "The response should be '가입 성공'.");

        // Verify that register was called once
        verify(authService, times(1)).register(request);
    }
}