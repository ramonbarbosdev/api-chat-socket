package app.chat.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController 
@RequestMapping(value = "/status")
@Tag(name = "Status da api")
public class StatusController {
    
    @Operation(description = "Status ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna o status ativo")
    })
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<?> verificarStatus()
    {
        return new ResponseEntity<>(Map.of("status", true), HttpStatus.OK);
    }
}