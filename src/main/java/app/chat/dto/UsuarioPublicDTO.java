package app.chat.dto;

import app.chat.model.Usuario;

public record UsuarioPublicDTO(
    Long id,
    String nome
    
) {
    public UsuarioPublicDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome());
    }
}
