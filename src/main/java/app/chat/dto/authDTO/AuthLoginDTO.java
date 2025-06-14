package app.chat.dto.authDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Autenticar um  usuário")
public class AuthLoginDTO {
    
   @Schema(description = "Login do usuário", example = "admin123", required = true)
    private String login;

    @Schema(description = "Senha do usuário", example = "senhaSegura123", required = true)
    private String senha;

    // Getters e Setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }


    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}