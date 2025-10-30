package com.chat.app.Controller;

import com.chat.app.Model.ChatMessage;
import com.chat.app.Repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ImageUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private ChatMessageRepository messageRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("sender") String sender,
            @RequestParam("roomId") String roomId) {

        try {
            // 1️⃣ Ensure upload directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 2️⃣ Generate unique file name
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR, fileName);

            // 3️⃣ Save image file locally
            Files.copy(file.getInputStream(), filePath);

            // 4️⃣ Create public URL
            String imageUrl = "/images/" + fileName;

            // 5️⃣ Build and save ChatMessage
            ChatMessage message = new ChatMessage();
            message.setSender(sender);
            message.setImageUrl(imageUrl);
            message.setContent(null); // no text
            message.setType(ChatMessage.MessageType.IMAGE);
            message.setRoomId(roomId);
            message.setTimestamp(System.currentTimeMillis());

            messageRepository.save(message);

            // 6️⃣ Notify WebSocket subscribers (real-time update)
            messagingTemplate.convertAndSend("/topic/messages", message);

            // 7️⃣ Return URL to frontend
            return ResponseEntity.ok(imageUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error uploading image: " + e.getMessage());
        }
    }
}
