package com.chat.liveon.auth.dto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public record LoginRequest(String personId, String personPassword) {
}
