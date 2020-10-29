package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
public class EncryptionService {
    public final static String TAG_ = "EncryptionService";

    @Autowired
    public EncryptionService() {
    }

    public String encryptValue(String data, String key) {
        byte[] encryptedValue = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedValue = cipher.doFinal(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            log.error(TAG_ + "-> " + e.getMessage());
        }

        return Base64.getEncoder().encodeToString(encryptedValue);
    }

    public String decryptValue(String data, String key) {
        byte[] decryptedValue = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(data));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error(TAG_ + "-> " + e.getMessage());
        }

        return new String(decryptedValue);
    }

    public String decryptPassword(Credential credential){
        byte[] decryptedValue = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(credential.getKey().getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedValue = cipher.doFinal(Base64.getDecoder().decode(credential.getPassword()));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error(TAG_ + "-> " + e.getMessage());
        }

        return new String(decryptedValue);
    }

    public String testing(){
        return "Testing";
    }
}
