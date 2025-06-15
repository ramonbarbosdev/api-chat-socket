package app.chat.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_chatmessage;

    private String roomId;

    @ManyToOne()
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "id_usuario")
	private Long id_usuario;

    private String message;

    private LocalDateTime timestamp;

    public Long getId_chatmessage() {
        return id_chatmessage;
    }
    public void setId_chatmessage(Long id_chatmessage) {
        this.id_chatmessage = id_chatmessage;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public Long getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
}
