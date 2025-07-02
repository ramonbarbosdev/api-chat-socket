package app.chat.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PresenceService {


    private final Set<Long> usuariosOnline = ConcurrentHashMap.newKeySet();

    public void userConnected(Long userId) {
        usuariosOnline.add(userId);
    }

    public void userDisconnected(Long userId) {
        usuariosOnline.remove(userId);
    }

    public boolean isOnline(Long userId) {
        return usuariosOnline.contains(userId);
    }

    public Set<Long> getOnlineUserIds() {
        return usuariosOnline;
    }

   
}