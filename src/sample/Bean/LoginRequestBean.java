package sample.Bean;

import static sample.Http.HttpClient.getSafeUUID;

public class LoginRequestBean {
    private String password;
    private String grant_type;
    private String unicode;
    private String client_secret;
    private String client_id;
    private String safeUniqueCode = getSafeUUID();
    private String username;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGrant_type() {
        return this.grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getUnicode() {
        return this.unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public String getClient_secret() {
        return this.client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_id() {
        return this.client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getSafeUniqueCode() {
        return this.safeUniqueCode;
    }

    public void setSafeUniqueCode(String safeUniqueCode) {
        this.safeUniqueCode = safeUniqueCode;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
