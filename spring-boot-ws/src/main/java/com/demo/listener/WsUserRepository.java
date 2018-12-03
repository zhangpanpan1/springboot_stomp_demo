package com.demo.listener;

import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by genghz on 18/4/17.
 */
@Component
public class WsUserRepository {
    private static final Map<String, Principal> repo = new ConcurrentHashMap<String, Principal>();

    public boolean addUser(Principal principal) {
        if (principal == null) return false;
        repo.put(principal.getName(), principal);
        return true;
    }

    public boolean delUser(Principal principal) {
        if (principal == null) return false;
        repo.remove(principal.getName());
        return true;
    }

    public Map<String, Principal> getUsers() {
        return repo;
    }

    public int countUsers() {
        return repo.size();
    }
}
