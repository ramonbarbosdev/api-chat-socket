package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.chat.model.ChatMessage;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Long> {

    @Query("select u from ChatMessage u where u.id_room = ?1")
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(Long id_room);
}
