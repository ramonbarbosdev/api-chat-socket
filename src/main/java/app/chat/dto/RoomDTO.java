
 package app.chat.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDTO {

    
    Long id_room;
    String nm_room;
    String ds_room;
    Long id_usuario;
}