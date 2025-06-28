package app.chat.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import app.chat.dto.FriendshipDTO;
import app.chat.dto.RoomDTO;
import app.chat.dto.UsuarioDTO;
import app.chat.dto.authDTO.AuthLoginDTO;
import app.chat.model.Friendship;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import app.chat.repository.ChatMessageRepository;
import app.chat.repository.FriendshipRepository;
import app.chat.repository.RoomRepository;
import app.chat.repository.RoomUsuarioRepository;
import app.chat.repository.UsuarioRepository;
import app.chat.service.FriendshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/friendship")
@Tag(name = "Amizade")
public class FriendshipController {

    @Autowired
    private FriendshipRepository repository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FriendshipService service;

    @PostMapping(value = "/convidar/{id_requester}/{id_receiver}", produces = "application/json")
    @Operation(summary = "Enviar convites de amizade" )
    public ResponseEntity<?> enviarConvite(@PathVariable Long id_requester, @PathVariable Long id_receiver)
    {
        service.enviarConvite(id_requester, id_receiver);
        return ResponseEntity.status(HttpStatus.CREATED).body("Convite enviado.");
    }

    @PostMapping(value = "/aceitar/{id_friendship}", produces = "application/json")
    public ResponseEntity<?> aceitarConvite(@RequestBody Long id_friendship) {
        return null;
    }

    @PostMapping(value = "/rejeitar/{id_friendship}", produces = "application/json")
    public ResponseEntity<?> rejeitarConvite(@RequestBody Long id_friendship) {
        return null;
    }

    //Amigos online - CONSTRUIR

    @GetMapping(value = "/amigos/{id_receiver}", produces = "application/json")
    public ResponseEntity<List<?>>  listarAmigos(@PathVariable Long id_receiver)
    {
        List<Friendship> objetos = (List<Friendship>) repository.findAceito(id_receiver);

        if(objetos.isEmpty()) throw new IllegalStateException("Voce não tem amigos.");
    
        List<FriendshipDTO> objetoDTO = objetos.stream()
            .map(objeto -> new FriendshipDTO(objeto)) 
            .collect(Collectors.toList()); 

        return new ResponseEntity<>(objetoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/pendente/{id_receiver}", produces = "application/json")
    public ResponseEntity<List<?>> listarPendente(@PathVariable Long id_receiver)
    {
        List<Friendship> objetos = (List<Friendship>) repository.findPendente(id_receiver);

        if(objetos.isEmpty()) throw new IllegalStateException("Voce não solicitação de amizade.");


        List<FriendshipDTO> objetoDTO = objetos.stream()
				.map(objeto -> new FriendshipDTO(objeto)) 
				.collect(Collectors.toList()); 

        return new ResponseEntity<>(objetoDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> removerAmizade(@PathVariable Long id)
    {
        return null;
    }


    //Todos

    //pendente



    // @MessageMapping("/invite")
    // public void inviteFriend(FriendshipRequest request, Principal principal) {
    // // lógica de salvar amizade com status PENDING

    // messagingTemplate.convertAndSendToUser(
    // request.getReceiverUsername(),
    // "/queue/friendship"
    // // new NotificationDTO("Novo convite de amizade de " + principal.getName())
    // );
    // }

}
