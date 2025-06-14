package app.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping(value = "/hello")
@Tag(name = "Teste de funcionamento")
public class HelloController {
    
    @Operation(description = "Testar comunicação com API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna uma olá mundo")
    })
    @GetMapping(value = "/", produces = "application/json")
    public String heloword()
    {
        return "Olá Mundo";
    }
}
