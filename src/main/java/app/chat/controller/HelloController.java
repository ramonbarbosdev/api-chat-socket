package app.chat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping(value = "/hello")
public class HelloController {
    
   @GetMapping(value = "/", produces = "application/json")
    public String heloword()
    {
        return "Olá Mundo";
    }
}
