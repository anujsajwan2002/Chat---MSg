package com.chat.app.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String username;
    private String password;
    private boolean online;

    public User() {}

    public User(String username, String password, boolean online) {
        this.username = username;
        this.password = password;
        this.online = online;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isOnline() { return online; }
    public void setOnline(boolean online) { this.online = online; }
}

