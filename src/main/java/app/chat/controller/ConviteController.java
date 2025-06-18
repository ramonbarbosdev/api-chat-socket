package app.chat.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.chat.dto.UsuarioDTO;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import app.chat.repository.RoomRepository;
import app.chat.repository.RoomUsuarioRepository;
import app.chat.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping(value = "/convite")
@Tag(name = "Salas")
public class ConviteController {
    

     @Autowired
    private RoomRepository repository;

    @Autowired
    private RoomUsuarioRepository roomUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    
    @GetMapping("/usuario-disponivel/{id_usuario}/{id_room}")
    public ResponseEntity<List<?>> obterUsuarioDisponivelConvite( @PathVariable Long id_usuario,@PathVariable Long id_room )
    {
        List<Usuario> usuarios =  usuarioRepository.findUsuarioDisponivelConvite(id_room, id_usuario);

       List<UsuarioDTO> usuariosDTO = usuarios.stream()
				.map(usuario -> new UsuarioDTO(usuario)) // Usando o construtor para mapear
				.collect(Collectors.toList()); // Coleta todos os DTOs em uma lista
		
        
        return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
    }

    
    @GetMapping("/enviar-convite/{id_room}/{id_usuario}")
    public ResponseEntity<?> compartilharSala(@PathVariable Long id_room, @PathVariable Long id_usuario)
    {

        Optional<Room> roomOpt = repository.findById(id_room);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id_usuario);

        if (roomOpt.isEmpty() || usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RoomUsuario compartilhamento = new RoomUsuario();
        compartilhamento.setRoom(roomOpt.get());
        compartilhamento.setUsuario(usuarioOpt.get());

        roomUsuarioRepository.save(compartilhamento);

        return ResponseEntity.ok().build();
    }
}
