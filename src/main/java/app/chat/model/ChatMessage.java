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

    @ManyToOne()
    @JoinColumn(name = "id_room", insertable = false, updatable = false)
    private Room room;

    @Column(name = "id_room")
    private Long id_room;

    @ManyToOne()
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
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
    public Long getId_room() {
        return id_room;
    }
    public void setId_room(Long id_room) {
        this.id_room = id_room;
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
