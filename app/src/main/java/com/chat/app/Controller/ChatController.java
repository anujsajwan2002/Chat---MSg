package com.chat.app.Controller;

import com.chat.app.Model.ChatMessage;
import com.chat.app.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository messageRepository; // ✅ Inject repository

    @MessageMapping("/sendMessages")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage message) {
        System.out.println("Received on backend: " + message.getSender() + " - " + message.getContent());

        // ✅ Save the message to MongoDB
        try {
            messageRepository.save(message);
            System.out.println("✅ Message saved to MongoDB: " + message);
        } catch (Exception e) {
            System.err.println("❌ Failed to save message: " + e.getMessage());
        }

        return message; // Still return it for real-time broadcast
    }

    @GetMapping("chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/")
    public String redirectToChat() {
        return "redirect:/chat";
    }
}
