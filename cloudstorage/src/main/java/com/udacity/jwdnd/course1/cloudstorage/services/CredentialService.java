package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Slf4j
@Service
public class CredentialService {

    public final static String TAG_ = "CredentialServic";
    private final UserService userService;
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    @Autowired
    public CredentialService(UserService userService, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllUserCredentials(int userId){
        return this.credentialMapper.getAll(userId);
    }

    public Credential getCredentialById(int credentialId){
        Credential credential = this.credentialMapper.getItemByItemId(credentialId);
        return credential;

    }
    public int editCredential(Credential credential){
        if(credential.getKey()==null || credential.getKey().equals("")){
            String key = this.credentialMapper.getKey(credential.getCredentialId());
            log.debug(TAG_ +  "-> getKey: " + key);
            credential.setPassword(encryptionService.encryptValue(credential.getPassword(),key));
        }
        return this.credentialMapper.update(credential);
    }

    public int deleteCredential(int credentialId){
        return this.credentialMapper.delete(credentialId);
    }

    public int deleteAll(){
        return this.credentialMapper.deleteAll();
    }

    public int addCredential(Credential add){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String password = add.getPassword();
        String encyptpassword = this.encryptionService.encryptValue(password, encodedSalt);
        add.setKey(encodedSalt);
        add.setPassword(encyptpassword);
        return this.credentialMapper.insert(add);
    }


}
