package app.chat.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.chat.dto.ChatMessageDTO;
import app.chat.model.ChatMessage;
import app.chat.repository.ChatMessageRepository;

@Controller
public class WebSocketController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessageDTO objeto)
    {

        ChatMessage entity = new ChatMessage();
        entity.setRoomId(roomId);
        entity.setId_usuario(objeto.getId_usuario());
        entity.setMessage(objeto.getMessage());
        entity.setTimestamp(objeto.getTimestamp());
        
        chatMessageRepository.save(entity);
        
        return entity;
    }

    @GetMapping("/history/{roomId}")
    public  ResponseEntity<List<ChatMessage>> getHistory(@PathVariable String roomId) {
         List<ChatMessage> mensagens = chatMessageRepository.findByRoomIdOrderByTimestampAsc(roomId);
        return ResponseEntity.ok(mensagens);
    }
    
 
}
