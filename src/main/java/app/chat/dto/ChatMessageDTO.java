
 package app.chat.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageDTO {

    
    Long id_usuario;
    String message;
    Long id_room;
    LocalDateTime timestamp;
}