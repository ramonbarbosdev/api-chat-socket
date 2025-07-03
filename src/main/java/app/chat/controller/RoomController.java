package app.chat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.chat.dto.RoomDTO;
import app.chat.dto.UsuarioDTO;
import app.chat.dto.authDTO.AuthLoginDTO;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import app.chat.repository.ChatMessageRepository;
import app.chat.repository.RoomRepository;
import app.chat.repository.RoomUsuarioRepository;
import app.chat.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/room")
@Tag(name = "Salas")
public class RoomController {

    @Autowired
    private RoomRepository repository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private RoomUsuarioRepository roomUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Operation(summary = "Consulta", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição feita com sucesso"),
    })
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<?>> obterTodos() {
        List<Room> entidades = (List<Room>) repository.findAll();

        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    @Operation(summary = "Consulta por ID", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição feita com sucesso"),
    })
    @GetMapping(value = "/{id_room}/{id_usuario}", produces = "application/json")
    public ResponseEntity<?> obterInformacaoSala(@PathVariable Long id_room, @PathVariable Long id_usuario) {

        Optional<Room> objeto = repository.findById(id_room);

        if (objeto.isPresent()) {

            Optional<Usuario> usuario = usuarioRepository.findById(id_usuario);
            RoomUsuario roomUsuario = roomUsuarioRepository.findUsuariosByidUsuario(id_usuario, id_room);

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId_room(id_room);

            roomDTO.setNm_room(
                    objeto.get().getId_usuario() == null
                            ? roomUsuario.getNm_roomperson()
                            : objeto.get().getNm_room());

            roomDTO.setDs_room(objeto.get().getDs_room());
            roomDTO.setId_usuario(objeto.get().getId_usuario());
            roomDTO.setNm_usuario(usuario.get().getNome());

            return new ResponseEntity<>(roomDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/salas-permitidas/{id_usuario}", produces = "application/json")
    public ResponseEntity<?> obterSalasPermitidas(@PathVariable Long id_usuario) {

        List<Room> list = repository.findSalasCompartilhadasComUsuario(id_usuario);
        List<RoomDTO> dtos = new ArrayList<>();

        for (Room objeto : list) {
            RoomUsuario roomUsuario = roomUsuarioRepository.findUsuariosByidUsuario(id_usuario, objeto.getId_room());

            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId_room(objeto.getId_room());
            roomDTO.setNm_room(
                    objeto.getId_usuario() == null
                            ? roomUsuario.getNm_roomperson()
                            : objeto.getNm_room());

            roomDTO.setDs_room(objeto.getDs_room());
            roomDTO.setId_usuario(objeto.getId_usuario());
            // roomDTO.setNm_usuario(usuario.get().getNome());
            dtos.add(roomDTO);
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/varificar-responsavel/{id_usuario}/{id_room}")
    public ResponseEntity<?> verificarResponsavelSala(@PathVariable Long id_usuario, @PathVariable Long id_room) {

        Boolean fl_responsavel = repository.findVerificarResponsavelSala(id_usuario, id_room);

        if (fl_responsavel != null) {
            return new ResponseEntity<>(Map.of("fl_responsavel", fl_responsavel), HttpStatus.OK);
        }

        return new ResponseEntity<>(Map.of("fl_responsavel", false), HttpStatus.OK);
    }

    @Operation(summary = "Criação", description = "")
    @ApiResponses(value = {
    // @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> cadastro(@RequestBody Room objeto) {

        Optional<Usuario> usuario = usuarioRepository.findById(objeto.getId_usuario());

        Room objetoSalvo = repository.save(objeto);

        RoomUsuario salaAssociada = new RoomUsuario();
        salaAssociada.setRoom(objetoSalvo);
        salaAssociada.setUsuario(usuario.get());
        roomUsuarioRepository.save(salaAssociada);

        messagingTemplate.convertAndSend("/topic/salas", "update");

        return new ResponseEntity<>(objetoSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Criação sala individual", description = "")
    @ApiResponses(value = {
    // @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/individual/{id_usuario}/{id_amigo}", produces = "application/json")
    public ResponseEntity<?> cadastroIndividual(@PathVariable Long id_usuario, @PathVariable Long id_amigo) {

        Usuario usuario = usuarioRepository.findById(id_usuario).orElseThrow();
        Usuario amigo = usuarioRepository.findById(id_amigo).orElseThrow();

        String nomeSala = gerarNomeSala(id_usuario, id_amigo);

        Optional<Room> salaOptional = repository.findByNome(nomeSala);
        Room sala;
        if (salaOptional.isPresent()) {
            sala = salaOptional.get();
        } else {
            Room nova = new Room();
            nova.setNm_room(nomeSala);
            sala = repository.save(nova);
        }
        System.out.println(usuario.getNome());
        System.out.println(amigo.getNome());

        criarRoomUsuario(sala, usuario, amigo.getNome());
        criarRoomUsuario(sala, amigo, usuario.getNome());

        messagingTemplate.convertAndSend("/topic/salas", "update");

        return new ResponseEntity<>(Map.of("message", "Gerando bate-papo", "room", sala), HttpStatus.OK);

    }

    public String gerarNomeSala(Long id1, Long id2) {
        return "sala_" + Math.min(id1, id2) + "_" + Math.max(id1, id2);
    }

    private void criarRoomUsuario(Room sala, Usuario usuario, String nomeExibido) {
        if (!roomUsuarioRepository.existsByRoomAndUsuario(sala, usuario)) {
            RoomUsuario ru = new RoomUsuario();
            ru.setRoom(sala);
            ru.setUsuario(usuario);
            ru.setNm_roomperson(nomeExibido);
            roomUsuarioRepository.save(ru);
        }
    }

    @DeleteMapping(value = "/remover-sala/{id_usuario}/{id_room}")
    public ResponseEntity<?> removerUsuarioSala(@PathVariable Long id_usuario, @PathVariable Long id_room) {

        Optional<Room> objeto = repository.findById(id_room);

        if (objeto.get().getId_usuario() != null) {
            roomUsuarioRepository.deleteByIdRoomIdUsuario(id_usuario, id_room);

        } else {
            chatMessageRepository.deleteByIdChatMessage(id_room);
            roomUsuarioRepository.deleteByIdRoomUsuario(id_room);
            repository.deleteById(id_room);
        }

        messagingTemplate.convertAndSend("/topic/salas", "update");
        messagingTemplate.convertAndSend("/topic/salas/delete", id_room);

        return new ResponseEntity<>(Map.of("message", "Usuario Removido"), HttpStatus.OK);
    }

    @Operation(summary = "Exclusão", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tudo certo")
    })
    @DeleteMapping(value = "/{id_room}", produces = "application/text")
    public ResponseEntity<?> delete(@PathVariable("id_room") Long id_room) throws Exception {

        chatMessageRepository.deleteByIdChatMessage(id_room);
        roomUsuarioRepository.deleteByIdRoomUsuario(id_room);
        repository.deleteById(id_room);

        messagingTemplate.convertAndSend("/topic/salas/delete", id_room);

        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Registro deletado!\"}");

    }
}
