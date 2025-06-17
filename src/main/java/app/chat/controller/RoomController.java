package app.chat.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Consulta", description = "")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Requisição feita com sucesso"),
    })
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<?>> obterTodos( ) 
    {
        List<Room> entidades = (List<Room>) repository.findAll();

        return new ResponseEntity<>(entidades, HttpStatus.OK);
    }

    @GetMapping("/salas-permitidas/{id_usuario}")
    public ResponseEntity<?> obterSalasPermitidas( @PathVariable Long id_usuario) {
       
        List<Room> list = repository.findSalasCompartilhadasComUsuario(id_usuario);

       return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id_room}/compartilhar/{id_usuario}")
    public ResponseEntity<?> compartilharSala(@PathVariable Long id_room, @PathVariable Long id_usuario)
    {
        Optional<Room> roomOpt = repository.findById(id_room);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id_usuario);

        if (roomOpt.isEmpty() || usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RoomUsuario compartilhamento = new RoomUsuario();
        compartilhamento.setId_room(id_room);
        compartilhamento.setId_usuario(id_usuario);

        roomUsuarioRepository.save(compartilhamento);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Criação", description = "")
    @ApiResponses(value = {
        // @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> cadastro(@RequestBody Room objeto) 
    {

       

        Room objetoSalvo = repository.save(objeto);

        RoomUsuario salaAssociada = new RoomUsuario();
        salaAssociada.setId_room(objetoSalvo.getId_room());
        salaAssociada.setId_usuario(objetoSalvo.getId_usuario());
        roomUsuarioRepository.save(salaAssociada);
    
        return new ResponseEntity<>(objetoSalvo, HttpStatus.CREATED);
    }

    @Operation(summary = "Exclusão", description = "")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tudo certo")
    })
    @DeleteMapping(value = "/{id}", produces = "application/text" )
	public ResponseEntity<?> delete (@PathVariable("id") Long id) throws Exception
	{   
        chatMessageRepository.deleteByIdChatMessage(id);
        roomUsuarioRepository.deleteByIdRoomUsuario(id);
        repository.deleteById( id);
			
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Registro deletado!\"}");

	}
}
