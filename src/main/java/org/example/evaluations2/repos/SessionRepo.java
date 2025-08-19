package org.example.evaluations2.repos;

import org.example.evaluations2.models.Session;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SessionRepo {
    private final Map<UUID, Session> sessionMap;

    public SessionRepo() {
        sessionMap = new HashMap<>();
    }

    public Session save(Session session) {
        sessionMap.put(session.getId(),session);
        return sessionMap.get(session.getId());
    }

    public Session findSessionByToken(String token) {
        List<Session> sessions =  sessionMap.values().stream()
                .filter(session -> session.getToken().equals(token))
                .collect(Collectors.toList());

        if(sessions.isEmpty()) return null;
        else return sessions.get(0);
    }
}
