package app.chat.controller;

import java.util.List;

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
import app.chat.repository.RoomRepository;
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

    @Operation(summary = "Criação", description = "")
    @ApiResponses(value = {
        // @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> cadastro(@RequestBody Room objeto) 
    {
        Room objetoSalvo = repository.save(objeto);
    
        return new ResponseEntity<>(objetoSalvo, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text" )
	public ResponseEntity<?> delete (@PathVariable("id") Long id) throws Exception
	{
        repository.deleteById( id);
			
        return ResponseEntity.status(HttpStatus.OK).body("{\"error\": \"Registro deletado!\"}");

	}
}
