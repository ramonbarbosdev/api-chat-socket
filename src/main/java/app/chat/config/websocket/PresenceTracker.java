package app.chat.config.websocket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class PresenceTracker {

    private final Set<Long> onlineUsers  = ConcurrentHashMap.newKeySet();

     public void addUser(Long userId) {
        onlineUsers .add(userId);
    }

    public void removeUser(Long userId) {
        onlineUsers .remove(userId);
    }

    public boolean isOnline(Long userId) {
        return onlineUsers .contains(userId);
    }

    public Set<Long> getAllOnlineUsers() {
        return onlineUsers ;
    }
}
