package app.chat.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.chat.config.websocket.PresenceTracker;

@RestController
@RequestMapping("/presence")
public class PresenceController {

    private final PresenceTracker presenceTracker;

    public PresenceController(PresenceTracker presenceTracker) {
        this.presenceTracker = presenceTracker;
    }

    @GetMapping("/online")
    public ResponseEntity<Set<Long>> getOnlineUsers() {
          Set<Long> onlineUsers = presenceTracker.getAllOnlineUsers();
          System.out.println("Usuários online: " + onlineUsers);
        return ResponseEntity.ok(onlineUsers);
    }

    @GetMapping("/online/{id}")
    public ResponseEntity<Boolean> isUserOnline(@PathVariable Long id) {
        return ResponseEntity.ok(presenceTracker.isOnline(id));
    }
}
