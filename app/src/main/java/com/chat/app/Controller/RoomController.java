package com.chat.app.Controller;

import com.chat.app.Model.ChatRoom;
import com.chat.app.Repository.ChatRoomRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final ChatRoomRepository roomRepository;

    public RoomController(ChatRoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public List<ChatRoom> getAllRooms() {
        return roomRepository.findAll();
    }

    @PostMapping
    public ChatRoom createRoom(@RequestBody ChatRoom room) {
        return roomRepository.save(room);
    }

    @GetMapping("/{id}")
    public ChatRoom getRoom(@PathVariable String id) {
        return roomRepository.findById(id).orElse(null);
    }
}

