
 package app.chat.dto;

import java.time.LocalDateTime;

import app.chat.enums.FriendshipStatus;
import app.chat.model.Friendship;
import app.chat.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendshipDTO {

    
    Long id_friendship;
    UsuarioPublicDTO id_requester;
    UsuarioPublicDTO id_receiver;
    FriendshipStatus tp_status;
    LocalDateTime dt_createdat;

    public FriendshipDTO(Friendship objeto) {
        this.id_friendship = objeto.getId_friendship();
        this.id_requester =  new UsuarioPublicDTO(objeto.getId_requester());
        this.id_receiver = new UsuarioPublicDTO(objeto.getId_receiver());
        this.tp_status = objeto.getTp_status();
        this.dt_createdat = objeto.getDt_createdat();
    }
}