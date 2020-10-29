package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class Credential {
    private int credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private int userId;

    public Credential(int credentialId, String url, String username, String key, String password, int userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Credential(String url, String username, String key, String password, int userId) {
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Credential(int credentialId, String url, String username, String password, int userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

    public Credential(String url, String username, String password, int userId) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
    }

}
