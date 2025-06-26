package app.chat.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.yaml.snakeyaml.events.Event.ID;

import app.chat.dto.ChatMessageDTO;
import app.chat.model.ChatMessage;
import app.chat.repository.ChatMessageRepository;

@Controller
public class WebSocketController {

    @Autowired
    private ChatMessageRepository repository;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable Long roomId, ChatMessageDTO objeto)
    {

        ChatMessage entity = new ChatMessage();
        entity.setId_room(roomId);
        entity.setId_usuario(objeto.getId_usuario());
        entity.setMessage(objeto.getMessage());
        entity.setTimestamp(objeto.getTimestamp());
        
        repository.save(entity);
        
        return entity;
    }

    @GetMapping("/history/{id_room}")
    public  ResponseEntity<?> getHistory(@PathVariable Long id_room) {
        List<ChatMessage> objeto  = repository.findByRoomIdOrderByTimestampAsc(id_room);
        return new ResponseEntity<>( objeto, HttpStatus.OK);
    }
    


  
 
}
