package com.chat.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OnlineUserService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final Set<String> onlineUsers = ConcurrentHashMap.newKeySet();

    public void addUser(String username) {
        onlineUsers.add(username);
        broadcastOnlineUsers();
    }

    public void removeUser(String username) {
        onlineUsers.remove(username);
        broadcastOnlineUsers();
    }

    public void broadcastOnlineUsers() {
        messagingTemplate.convertAndSend("/topic/onlineUsers", onlineUsers);
    }

    public Set<String> getOnlineUsers() {
        return onlineUsers;
    }
}

