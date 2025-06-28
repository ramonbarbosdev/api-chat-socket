
 package app.chat.dto;

import java.time.LocalDateTime;

import app.chat.enums.AmizadeStatus;
import app.chat.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendshipDTO {

    
    Long id_friendship;
    Usuario id_requester;
    Usuario id_receiver;
    AmizadeStatus tp_status;
    LocalDateTime dt_createdat;

    public FriendshipDTO() {
        this.id_friendship = null;
        this.id_requester = null;
        this.id_receiver = null;
        this.tp_status = null;
        this.dt_createdat = null;
    }
}