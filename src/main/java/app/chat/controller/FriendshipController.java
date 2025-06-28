package app.chat.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.chat.dto.RoomDTO;
import app.chat.dto.authDTO.AuthLoginDTO;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import app.chat.repository.ChatMessageRepository;
import app.chat.repository.FriendshipRepository;
import app.chat.repository.RoomRepository;
import app.chat.repository.RoomUsuarioRepository;
import app.chat.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/friendship")
@Tag(name = "Amizade")
public class FriendshipController {

    @Autowired
    private FriendshipRepository repository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping(value = "/convidar/{id_usuario}", produces = "application/json")
    public ResponseEntity<?> enviarConvite(@RequestBody  Long id_usuario)
    {
        return null;
    }

    @PostMapping(value = "/aceitar/{id_friendship}", produces = "application/json")
    public ResponseEntity<?> aceitarConvite(@RequestBody  Long id_friendship)
    {
        return null;
    }

    @PostMapping(value = "/rejeitar/{id_friendship}", produces = "application/json")
    public ResponseEntity<?> rejeitarConvite(@RequestBody  Long id_friendship)
    {
        return null;
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> listarAmigos()
    {
        return null;
    }

    @GetMapping(value = "/solicitacao", produces = "application/json")
    public ResponseEntity<?> listarConviteRecebido()
    {
        return null;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> listarConviteRecebido(@RequestBody Long id)
    {
        return null;
    }

    // @MessageMapping("/invite")
    // public void inviteFriend(FriendshipRequest request, Principal principal) {
    //     // lógica de salvar amizade com status PENDING

    //     messagingTemplate.convertAndSendToUser(
    //         request.getReceiverUsername(),
    //         "/queue/friendship"
    //         // new NotificationDTO("Novo convite de amizade de " + principal.getName())
    //     );
    // }

   
}
