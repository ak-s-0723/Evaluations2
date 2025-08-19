package org.example.evaluations2.repos;

import org.example.evaluations2.models.Session;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<Session> findActiveSessionsByUserId(UUID userId) {
        //Add implementation here
        return null;
    }
}
