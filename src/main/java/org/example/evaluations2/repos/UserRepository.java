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

    public User findByEmail(String email) {
        return userMap.get(email);
    }

    public User save(User user) {
        userMap.put(user.getEmail(), user);
        return userMap.get(user.getEmail());
    }
}
