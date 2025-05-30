package com.chat.app.Controller;

import com.chat.app.Model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    @MessageMapping("/sendMessages")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage (ChatMessage message){
        System.out.println("Received on backend: " + message.getSender() + " - " + message.getContent());
        return message;
    }

    @GetMapping("chat")
    public String chat(){
        return "chat";
    }
}
