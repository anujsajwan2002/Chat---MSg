package com.chat.app.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@Document(collection = "messages")
@Data
public class ChatMessage {
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Id
    private String id;
    private String sender;
    private String content;
    private String imageUrl;
    private MessageType type;

    public ChatMessage(String id, String sender, String content, String imageUrl, MessageType type) {
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
