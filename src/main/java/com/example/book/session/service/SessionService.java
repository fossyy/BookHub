package com.example.book.session.service;

import com.example.book.user.dto.UserSessionDTO;

import java.util.Optional;

public interface SessionService {
    boolean isTokenValid(String token);

    Optional<UserSessionDTO> getSessionData(String token);

    void addSession(String token, UserSessionDTO sessionData);

    void removeSession(String token);
}
