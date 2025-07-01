package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.chat.dto.RoomDTO;
import app.chat.enums.FriendshipStatus;
import app.chat.model.Friendship;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface FriendshipRepository extends CrudRepository<Friendship, Long> {

    @Query("""
                SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
                FROM Friendship f
                WHERE (f.id_requester = :u1 AND f.id_receiver = :u2)
                   OR (f.id_requester = :u2 AND f.id_receiver = :u1)
            """)
    boolean existeRelacionamento(@Param("u1") Usuario u1, @Param("u2") Usuario u2);

    @Query("""
                SELECT f  FROM Friendship f
                WHERE f.id_receiver.id = :id_receiver
                AND f.tp_status in ('PENDENTE', 'RECUSADO')
            """)
    List<Friendship> findPendente(Long id_receiver);

    @Query("""
                SELECT f  FROM Friendship f
                WHERE f.id_receiver.id = :id_usuario
                OR f.id_requester.id = :id_usuario
                AND f.tp_status = 'ACEITO'
            """)
    List<Friendship> findAceito(Long id_usuario);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = """
                UPDATE friendship
                SET tp_status = ?2
                WHERE id_friendship = ?1
                  AND tp_status = 'PENDENTE'
            """)
    void atualizarStatus(Long id_friendship, String tp_status);

    @Query("""
        SELECT f FROM Friendship f
        WHERE f.tp_status = :status
        AND (f.id_requester = :usuario OR f.id_receiver = :usuario)
    """)
    List<Friendship> findByStatusAndRequesterOrReceiver(
            @Param("status") FriendshipStatus status,
            @Param("usuario") Usuario usuario);

}
