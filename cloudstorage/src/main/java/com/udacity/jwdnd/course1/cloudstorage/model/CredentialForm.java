package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class CredentialForm {
    private String credentialId;
    private String url;
    private String username;
    private String password;

    public CredentialForm(String credentialId, String url, String username, String password) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public CredentialForm() {
    }

}
