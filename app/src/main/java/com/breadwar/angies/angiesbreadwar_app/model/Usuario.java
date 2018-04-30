package com.breadwar.angies.angiesbreadwar_app.model;


public class Usuario {
    private String username;
    private String password;
    private String gandType;
    private String clientId;
    private String clientSecret;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGandType() {
        return gandType;
    }

    public void setGandType(String gandType) {
        this.gandType = gandType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
