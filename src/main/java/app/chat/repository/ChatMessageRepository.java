package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.chat.model.ChatMessage;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {

    @Query("select u from ChatMessage u where u.id_room = ?1")
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(Long id_room);

    @Query(value = "SELECT * FROM ChatMessage il WHERE il.id_room =?1", nativeQuery = true)
    List<ChatMessage> findByLancamentoId(Long id_room);

    @Modifying
    @Query("DELETE FROM ChatMessage i WHERE i.id_room = :id_room")
    void deleteByIdChatMessage(@Param("id_room") Long id_room);
	  
}
