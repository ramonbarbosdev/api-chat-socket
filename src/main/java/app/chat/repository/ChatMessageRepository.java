package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.chat.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomIdOrderByTimestampAsc(String roomId);
}
