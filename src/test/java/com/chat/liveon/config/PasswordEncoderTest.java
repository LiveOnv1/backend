package com.chat.liveon.config;

import com.chat.liveon.auth.service.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncoderTest {

    @Test
    @DisplayName("암호화 테스트")
    void testEncrypt() {
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String personId = "testPersonId";
        String password = "testPassword";

        String encryptedPassword = passwordEncoder.encrypt(personId, password);

        String expectedEncryptedPassword = "ZrLeDuIwrBFPkuIEGr+Ufg==";
        assertEquals(expectedEncryptedPassword, encryptedPassword, "The encrypted password should match the expected value.");
    }
}