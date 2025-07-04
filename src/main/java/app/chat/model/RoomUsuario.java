package app.chat.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "room_usuario")
public class RoomUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_roomusuario;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonIgnore
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_room")
    @JsonIgnore
    private Room room;

    private String nm_roomperson;


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

    public String getNm_roomperson() {
        return nm_roomperson;
    }
    public void setNm_roomperson(String nm_roomperson) {
        this.nm_roomperson = nm_roomperson;
    }
}