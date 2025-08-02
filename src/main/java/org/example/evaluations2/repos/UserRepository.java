package org.example.evaluations2.repos;

import org.example.evaluations2.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<String, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
    }

    public User save(User user) {
        return userMap.put(user.getEmail(), user);
    }

    public User findByEmail(String email) {
        if(userMap.containsKey(email)) return userMap.get(email);
        return null;
    }
}
