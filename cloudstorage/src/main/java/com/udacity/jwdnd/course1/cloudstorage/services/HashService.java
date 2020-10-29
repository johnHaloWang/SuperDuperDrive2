package com.udacity.jwdnd.course1.cloudstorage.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Slf4j
@Component
public class HashService {
    public final static String TAG_ = "HashService";
    public String getHashedValue(String data, String salt) {
        byte[] hashedValue = null;

        KeySpec spec = new PBEKeySpec(data.toCharArray(), salt.getBytes(), 5000, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedValue = factory.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error(TAG_ + " -> " + e.getMessage());
        }

        return Base64.getEncoder().encodeToString(hashedValue);
    }
}
