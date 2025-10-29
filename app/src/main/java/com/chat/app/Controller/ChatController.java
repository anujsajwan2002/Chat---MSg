package com.chat.app.Controller;

import com.chat.app.Model.ChatMessage;
import com.chat.app.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // ✅ Handle WebSocket messages (text + image metadata)
    @MessageMapping("/sendMessages")
    public void sendMessage(ChatMessage message) {
        System.out.println("📩 Received: " + message.getSender() + " -> " + message.getContent());

        try {
            message.setTimestamp(System.currentTimeMillis());
            messageRepository.save(message);
            System.out.println("✅ Saved message to DB");
        } catch (Exception e) {
            System.err.println("❌ Failed to save: " + e.getMessage());
        }

        // Send to specific chat room
        if (message.getRoomId() != null && !message.getRoomId().isEmpty()) {
            messagingTemplate.convertAndSend("/topic/messages/" + message.getRoomId(), message);
        } else {
            messagingTemplate.convertAndSend("/topic/messages", message);
        }
    }

    // ✅ Upload image API (returns URL)
    @PostMapping("/api/chat/upload")
    @ResponseBody
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // ✅ Ensure upload folder exists in project root
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // ✅ Unique file name
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filepath = Paths.get(uploadDir, filename);

            // ✅ Save file to local filesystem
            file.transferTo(filepath.toFile());

            // ✅ Public URL to serve the image
            String imageUrl = "/images/" + filename;

            System.out.println("✅ Image uploaded to: " + filepath);
            System.out.println("🌍 Accessible at: " + imageUrl);

            return ResponseEntity.ok(imageUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload image: " + e.getMessage());
        }
    }


    // ✅ Serve chat page
    @GetMapping("/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping("/")
    public String redirectToChat() {
        return "redirect:/chat";
    }
}
