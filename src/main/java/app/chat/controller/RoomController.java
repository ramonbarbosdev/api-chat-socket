package app.chat.controller;

import java.util.List;
import java.util.Map;
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

    @GetMapping(value ="/salas-permitidas/{id_usuario}",  produces = "application/json")
    public ResponseEntity<?> obterSalasPermitidas( @PathVariable Long id_usuario) {
       
        List<Room> list = repository.findSalasCompartilhadasComUsuario(id_usuario);

       return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // @GetMapping("/compartilhamento/{id_usuario}")
    // public ResponseEntity<?> obterUsuarioParaCompartilhamento( @PathVariable Long id_usuario) {
       
    //     List<Room> list = repository.findSalasCompartilhadasComUsuario(id_usuario);

    //    return new ResponseEntity<>(list, HttpStatus.OK);
    // }

    @GetMapping("/varificar-responsavel/{id_usuario}/{id_room}")
    public ResponseEntity<?> verificarResponsavelSala( @PathVariable Long id_usuario,@PathVariable Long id_room) {
       
        Boolean fl_responsavel = repository.findVerificarResponsavelSala(id_usuario, id_room);

        if(fl_responsavel != null)
        {
            return new ResponseEntity<>(Map.of("fl_responsavel",fl_responsavel), HttpStatus.OK);
        }

         return new ResponseEntity<>(Map.of("fl_responsavel",false), HttpStatus.OK); 
    }

    @DeleteMapping(value = "/remover-sala/{id_usuario}/{id_room}")
    public ResponseEntity<?> removerUsuarioSala( @PathVariable Long id_usuario,@PathVariable Long id_room) {
       
        roomUsuarioRepository.deleteByIdRoomIdUsuario(id_usuario, id_room);

       return new ResponseEntity<>(Map.of("message","Usuario Removido"), HttpStatus.OK);
    }



    @Operation(summary = "Criação", description = "")
    @ApiResponses(value = {
        // @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> cadastro(@RequestBody Room objeto) 
    {

        Optional<Usuario> usuario = usuarioRepository.findById(objeto.getId_usuario());

        Room objetoSalvo = repository.save(objeto);

        RoomUsuario salaAssociada = new RoomUsuario();
        salaAssociada.setRoom(objetoSalvo);
        salaAssociada.setUsuario(usuario.get());
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
