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
import app.chat.dto.UsuarioPublicDTO;
import app.chat.dto.authDTO.AuthLoginDTO;
import app.chat.enums.FriendshipStatus;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private FriendshipService service;

    @PostMapping(value = "/convidar/{id_requester}/{id_receiver}", produces = "application/json")
    @Operation(summary = "Enviar convites de amizade" )
    public ResponseEntity<?> enviarConvite(@PathVariable Long id_requester, @PathVariable Long id_receiver)
    {
        service.enviarConvite(id_requester, id_receiver);
        messagingTemplate.convertAndSend("/topic/amigos", "update");
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Convite enviado"));
    }

    @PostMapping(value = "/aceitar/{id_usuario}/{id_friendship}", produces = "application/json")
    public ResponseEntity<?> aceitarConvite(@PathVariable Long id_usuario, @PathVariable Long id_friendship)
    {
        Optional<Friendship> objeto = repository.findById(id_friendship);
        if(objeto.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Não existe solicitação pendente"));
        if(objeto.get().getTp_status() == FriendshipStatus.ACEITO) return null;
 
        if(!objeto.get().getId_receiver().getId().equals(id_usuario))
        {
            throw new IllegalStateException("Usuario destinatario incorreto para aceitar.");
        }

        repository.atualizarStatus(id_friendship,  FriendshipStatus.ACEITO.name());

        messagingTemplate.convertAndSend("/topic/amigos", "update");

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Solicitação aceita."));
    }

    @PostMapping(value = "/recusar/{id_usuario}/{id_friendship}", produces = "application/json")
    public ResponseEntity<?> rejeitarConvite(@PathVariable Long id_usuario, @PathVariable Long id_friendship)
    {
        Optional<Friendship> objeto = repository.findById(id_friendship);
        if(objeto.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Não existe solicitação pendente"));
        if(objeto.get().getTp_status() == FriendshipStatus.RECUSADO) return null;


        if(!objeto.get().getId_receiver().getId().equals(id_usuario))
        {
            throw new IllegalStateException("Usuario destinatario incorreto pare recusar.");
        }
    
        repository.atualizarStatus(id_friendship,  FriendshipStatus.RECUSADO.name() );
        
        messagingTemplate.convertAndSend("/topic/amigos", "update");

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Solicitação de amizade recusada"));
    }

    //Amigos online - CONSTRUIR

    // @GetMapping("/amigos-online/{id_usuario}")
    // @Operation(summary = "Listar amigos online do usuário autenticado")
    // public ResponseEntity<List<UsuarioPublicDTO>> listarAmigosOnline(@PathVariable Long id_usuario)
    // {
    //     Long id = usuarioRepository.findById(id_usuario).get().getId(); 

    //     List<UsuarioPublicDTO> online = service.obterAmigosOnline(id);

    //     return ResponseEntity.ok(online);
    // }

    @GetMapping(value = "/amigos/{id_usuario}", produces = "application/json")
    public ResponseEntity<List<?>>  listarAmigos(@PathVariable Long id_usuario)
    {
        List<Friendship> objetos = (List<Friendship>) repository.findAceito(id_usuario);

        List<FriendshipDTO> objetoDTO = objetos.stream()
            .map(objeto -> new FriendshipDTO(objeto)) 
            .collect(Collectors.toList()); 

        return new ResponseEntity<>(objetoDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/pendente/{id_receiver}", produces = "application/json")
    public ResponseEntity<List<?>> listarPendente(@PathVariable Long id_receiver)
    {
        List<Friendship> objetos = (List<Friendship>) repository.findPendente(id_receiver);


        List<FriendshipDTO> objetoDTO = objetos.stream()
				.map(objeto -> new FriendshipDTO(objeto)) 
				.collect(Collectors.toList()); 

        return new ResponseEntity<>(objetoDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> removerAmizade(@PathVariable Long id)
    {
        repository.deleteById(id);
        messagingTemplate.convertAndSend("/topic/amigos", "update");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Solicitação recusada"));
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
