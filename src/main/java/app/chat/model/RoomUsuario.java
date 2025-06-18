package app.chat.model;


import jakarta.persistence.*;

@Entity
@Table(name = "room_usuario")
public class RoomUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_roomusuario;

   @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_room")
    private Room room;

    // Getters e Setters
    public Long getId_roomusuario() {
        return id_roomusuario;
    }

    public void setId_roomusuario(Long id) {
        this.id_roomusuario = id_roomusuario;
    }

    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}