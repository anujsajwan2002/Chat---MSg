package com.chat.app.Config;

import com.chat.app.Service.OnlineUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthEventListener implements LogoutSuccessHandler {

    @Autowired
    private OnlineUserService onlineUserService;

    @EventListener
    public void handleLoginSuccess(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails user) {
            onlineUserService.addUser(user.getUsername());
        }
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails user) {
            onlineUserService.removeUser(user.getUsername());
        }
    }
}
