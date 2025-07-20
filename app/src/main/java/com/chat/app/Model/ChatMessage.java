package com.chat.app.Model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@NoArgsConstructor
@Data
public class ChatMessage {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    private Long id;
    private String sender;
    private String content;
    private String imageUrl;
    private MessageType type;

    public ChatMessage(Long id, String sender, String content, String imageUrl, MessageType type) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    public enum MessageType {
        CHAT,
        IMAGE
    }
}
