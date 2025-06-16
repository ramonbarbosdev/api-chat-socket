package app.chat.model;


import jakarta.persistence.*;

@Entity
@Table(name = "room_usuario")
public class RoomUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_roomusuario;

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

    // Getters e Setters
    public Long getId_roomusuario() {
        return id_roomusuario;
    }

    public void setId_roomusuario(Long id) {
        this.id_roomusuario = id_roomusuario;
    }

    public Long getId_room() {
        return id_room;
    }

    public void setId_room(Long id_room) {
        this.id_room = id_room;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
}