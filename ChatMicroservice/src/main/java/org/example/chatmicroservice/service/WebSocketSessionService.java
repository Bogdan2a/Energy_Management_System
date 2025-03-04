package org.example.chatmicroservice.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketSessionService {

    // Track the active sessions (userId -> sessionId)
    private final Map<String, String> activeUserSessions = new HashMap<>();

    public void addUserSession(String userId, String sessionId) {
        activeUserSessions.put(userId, sessionId);
    }

    public String getUserSession(String userId) {
        return activeUserSessions.get(userId);
    }

    public void removeUserSession(String userId) {
        activeUserSessions.remove(userId);
    }
}
