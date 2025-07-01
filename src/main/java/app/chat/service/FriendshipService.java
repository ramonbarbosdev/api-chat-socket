package app.chat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.chat.dto.UsuarioPublicDTO;
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
    private FriendshipRepository repository;

   

    public void enviarConvite(Long id_requester, Long id_receiver) {
        if (id_requester.equals(id_receiver))
            throw new IllegalArgumentException("Você não pode enviar convite para si mesmo.");

        Usuario solicitante = usuarioRepository.findById(id_requester)
                .orElseThrow(() -> new EntityNotFoundException("Usuário autenticado não encontrado"));

        Usuario destinatario = usuarioRepository.findById(id_receiver)
                .orElseThrow(() -> new EntityNotFoundException("Usuário destinatário não encontrado"));

        boolean jaExiste = repository.existeRelacionamento(
                solicitante, destinatario);

        System.out.println(jaExiste);

        if (jaExiste)
            throw new IllegalStateException("Convite já enviado ou amizade existente.");

        Friendship objeto = new Friendship();
        objeto.setId_requester(solicitante);
        objeto.setId_receiver(destinatario);
        objeto.setTp_status(FriendshipStatus.PENDENTE);
        objeto.setDt_createdat(LocalDateTime.now());

        repository.save(objeto);

    }

//     public List<UsuarioPublicDTO> obterAmigosOnline(Long id_usuario) {
//         Usuario usuario = usuarioRepository.findById(id_usuario)
//                 .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

//         List<Friendship> amizades = repository.findByStatusAndRequesterOrReceiver(
//                 FriendshipStatus.ACEITO, usuario);

//         Set<Usuario> amigos = amizades.stream()
//                 .map(f -> f.getId_requester().equals(usuario) ? f.getId_receiver() : f.getId_requester())
//                 .collect(Collectors.toSet());

//         // Filtra só os online
//         return amigos.stream()
//                 .filter(amigo -> presenceTracker.isOnline(amigo.getId()))
//                 .map(UsuarioPublicDTO::new)
//                 .toList();
//     }
}
