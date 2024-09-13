package com.chat.liveon.auth.controller;

import com.chat.liveon.auth.dto.LoginRequest;
import com.chat.liveon.auth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        session = new MockHttpSession();
        session.setAttribute("personId", "testUser123");
    }

/*    @InjectMocks
    private AuthController authController;*/

/*    public AuthControllerTest() {
        MockitoAnnotations.openMocks(this);
    }*/

/*    @Test
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
    }*/

    @Test
    @DisplayName("로그인 성공")
    void loginTest() throws Exception {
        // Given
        doNothing().when(authService).login(any(LoginRequest.class));

        // When
        mockMvc.perform(post("/api/login")
                        .contentType("application/json")
                        .content("{\"personId\":\"testPersonId\", \"personPassword\":\"testPassword\"}"))
                // Then
                .andExpect(status().isOk());

        // Verify
        verify(authService).login(any(LoginRequest.class));
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logoutTest() throws Exception {
        // Arrange
        doNothing().when(authService).logout(any(HttpSession.class));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/logout")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("로그아웃 성공"));

        // Verify
        verify(authService, times(1)).logout(any(HttpSession.class));
    }
}