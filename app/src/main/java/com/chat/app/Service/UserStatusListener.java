package com.chat.app.Service;

import com.chat.app.Repository.UserRepository;
import com.chat.app.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserStatusListener {

    @Autowired
    private UserRepository userRepo;

    // 👇 Used to send messages to subscribed WebSocket topics
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 🔥 Utility method to broadcast online users
    private void broadcastOnlineUsers() {
        List<String> onlineUsers = userRepo.findAll().stream()
                .filter(User::isOnline)
                .map(User::getUsername)
                .collect(Collectors.toList());

        messagingTemplate.convertAndSend("/topic/onlineUsers", onlineUsers);
    }

    // ✅ Called automatically on login success
    @EventListener
    public void handleLogin(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        userRepo.findByUsername(username).ifPresent(user -> {
            user.setOnline(true);
            userRepo.save(user);
        });

        // Broadcast updated online users list
        broadcastOnlineUsers();
    }

    // ✅ Called automatically on logout or session timeout
    @EventListener
    public void handleLogout(HttpSessionDestroyedEvent event) {
        event.getSecurityContexts().forEach(ctx -> {
            Authentication auth = ctx.getAuthentication();
            if (auth != null) {
                String username = auth.getName();
                userRepo.findByUsername(username).ifPresent(user -> {
                    user.setOnline(false);
                    userRepo.save(user);
                });
            }
        });

        // Broadcast updated list
        broadcastOnlineUsers();
    }
}
