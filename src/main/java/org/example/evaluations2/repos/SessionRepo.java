package org.example.evaluations2.repos;

import org.example.evaluations2.models.Session;
import org.example.evaluations2.models.State;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class SessionRepo {
    private final Map<UUID, Session> sessionMap;

    public SessionRepo() {
        sessionMap = new HashMap<>();

//        Dummy Test Data
//        Session session = new Session();
//        session.setState(State.ACTIVE);
//        session.setToken("eyJhbGciOiJIUzI1NiJ9.eyJnZW5UIjoxNzU1NjI2MzM3NDg0LCJzZXNzaW9uIjoic2Vzc2lvbl9wcmltYXJ5X2VmOTIyMjRjLTg4MzgtNGFhNC05OTcwLTllYTM3YmFkOGI3YiIsImV4cFQiOjE3NTU2Mjk5Mzc0ODQsInVzZXJJZCI6ImNjYWE0ZjYzLTQ1NDYtNDllMy05YmI3LWJlNGVmNzc4ZmIzOCIsImlzc3VlciI6IlNjYWxlciJ9.qsMnI6nN0Ns5yLrIWW3PsKKkr4UkX7nOEOtz_2xlk1c");
//        session.setId(UUID.randomUUID());
//        session.setUserId(UUID.randomUUID());
//        sessionMap.put(session.getId(),session);
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
