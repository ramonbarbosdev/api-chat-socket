package app.chat.config.websocket;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import app.chat.model.Usuario;
import app.chat.repository.UsuarioRepository;
import app.chat.service.PresenceService;

@Component
public class WebSocketPresenceEventListener {

    // @Autowired
    // private PresenceService presenceService;

    // @Autowired
    // private UsuarioRepository usuarioRepository;

    //  @Autowired
    // private SimpMessagingTemplate messagingTemplate;


    // @EventListener
    // public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    //     // Principal principal = event.getUser();
    //     // if (principal != null) {
    //     //     String username = principal.getName();
    //     //     Usuario usuario = usuarioRepository.findUserByLogin(username);
    //     //     Long userId = usuario.getId();
    //     //     presenceService.userConnected(userId);
    //     //     System.out.println("Conectou: " + userId + " - Total online: " + presenceService.getOnlineUserIds().size());

    //     // }
    //         messagingTemplate.convertAndSend("/topic/amigos", "update");

    // }

    // @EventListener
    // public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    //     // StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    //     // Map<String, Object> attributes = accessor.getSessionAttributes();

    //     // if (attributes != null && attributes.containsKey("userId")) {
    //     //     Long userId = (Long) attributes.get("userId");
    //     //     presenceService.userDisconnected(userId);
    //     //     System.out.println("Desconectado: " + userId + " - Total online: " + presenceService.getOnlineUserIds().size());

    //     // }
    //         messagingTemplate.convertAndSend("/topic/amigos", "update");

    // }
}
