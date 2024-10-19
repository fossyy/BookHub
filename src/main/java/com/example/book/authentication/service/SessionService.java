package com.example.book.authentication.service;

import com.example.book.user.dto.UserSessionDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class SessionService {

    private final HashMap<String, UserSessionDTO> sessions = new HashMap<>();

    public boolean isTokenValid(String token) {
        return sessions.containsKey(token);
    }

    public Optional<UserSessionDTO> getSessionData(String token) {
        return Optional.ofNullable(sessions.get(token));
    }

    public void addSession(String token, UserSessionDTO sessionData) {
        sessions.put(token, sessionData);
    }

    public void removeSession(String token) {
        sessions.remove(token);
    }
}
