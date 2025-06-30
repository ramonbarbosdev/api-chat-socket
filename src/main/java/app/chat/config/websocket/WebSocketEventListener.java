package app.chat.config.websocket;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final PresenceTracker presenceTracker;

    public WebSocketEventListener(PresenceTracker presenceTracker) {
        this.presenceTracker = presenceTracker;
    }
    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (sessionAttributes == null) {
            System.out.println("[WebSocketEventListener] sessionAttributes é null no connect");
            return;
        }

        Object userObj = sessionAttributes.get("userId");
        if (userObj != null) {
            Long userId = Long.valueOf(userObj.toString());
            presenceTracker.addUser(userId);
            System.out.println("[WebSocketEventListener] Usuário conectado, userId: " + userId);
        } else {
            System.out.println("[WebSocketEventListener] userId não encontrado na sessão no connect");
        }
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();

        if (sessionAttributes == null) {
            System.out.println("[WebSocketEventListener] sessionAttributes é null no disconnect");
            return;
        }

        Object userObj = sessionAttributes.get("userId");
        if (userObj != null) {
            Long userId = Long.valueOf(userObj.toString());
            presenceTracker.removeUser(userId);
            System.out.println("[WebSocketEventListener] Usuário desconectado, userId: " + userId);
        } else {
            System.out.println("[WebSocketEventListener] userId não encontrado na sessão no disconnect");
        }
    }

    private Long extractUserId(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
        if (sessionAttributes == null) {
            return null;
        }
        Object user = sessionAttributes.get("userId");
        return user != null ? Long.valueOf(user.toString()) : null;
    }
}
