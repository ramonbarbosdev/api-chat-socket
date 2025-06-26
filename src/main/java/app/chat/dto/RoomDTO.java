
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
    String nm_usuario;


    public RoomDTO() {
        this.id_room = null;
        this.nm_room = null;
        this.ds_room = null;
        this.id_usuario = null;
        this.nm_usuario = null;
    }
}