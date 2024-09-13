package com.chat.liveon.auth.service;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import static java.util.Objects.hash;

@Component
public class PasswordEncoder {

    public String encrypt(final String personId, final String password) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), getSalt(personId), 85319, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException |
                 InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean matches(final String personId, final String rawPassword, final String encodedPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String hashedRawPassword = encrypt(personId, rawPassword);
        return hashedRawPassword.equals(encodedPassword);
    }

    private byte[] getSalt(final String personId) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] keyBytes = personId.getBytes(StandardCharsets.UTF_8);

        return digest.digest(keyBytes);
    }
}
