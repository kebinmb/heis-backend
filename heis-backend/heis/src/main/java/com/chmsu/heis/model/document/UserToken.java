package com.chmsu.heis.model.document;

public class UserToken {
    private User user;
    private String token;

    public UserToken(User user, String token) {
        this.user = user;
        this.token = token;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
