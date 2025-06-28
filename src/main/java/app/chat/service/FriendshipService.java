package app.chat.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.chat.enums.FriendshipStatus;
import app.chat.model.Friendship;
import app.chat.model.Usuario;
import app.chat.repository.FriendshipRepository;
import app.chat.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FriendshipService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private  FriendshipRepository repository;

    public void enviarConvite(Long id_requester, Long id_receiver)
    {
        if (id_requester.equals(id_receiver))   throw new IllegalArgumentException("Você não pode enviar convite para si mesmo.");

        Usuario solicitante = usuarioRepository.findById(id_requester)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        Usuario destinatario = usuarioRepository.findById(id_receiver)
                .orElseThrow(() -> new EntityNotFoundException("Usuário destinatário não encontrado"));

        boolean jaExiste = repository.existeRelacionamento(
                        solicitante, destinatario
                    );

        System.out.println(jaExiste);

        if (jaExiste) throw new IllegalStateException("Convite já enviado ou amizade existente.");
        
        Friendship objeto = new Friendship();
        objeto.setId_requester(solicitante);
        objeto.setId_receiver(destinatario);
        objeto.setTp_status(FriendshipStatus.PENDENTE);
        objeto.setDt_createdat(LocalDateTime.now());

        repository.save(objeto);
        
    }
}
