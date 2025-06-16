package app.chat.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_room;

    @NotBlank(message = "O nome é obrigatorio!")
	@Column(unique = true, nullable = false)
    private String nm_room;

    private String ds_room;

    @ManyToOne()
    @JoinColumn(name = "id_usuario", insertable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "id_usuario")
	private Long id_usuario;


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomUsuario> compartilhamentos = new ArrayList<>();

    public List<RoomUsuario> getCompartilhamentos() {
        return compartilhamentos;
    }

    public void setCompartilhamentos(List<RoomUsuario> compartilhamentos) {
        this.compartilhamentos = compartilhamentos;
}

    public String getDs_room() {
        return ds_room;
    }
    public void setDs_room(String ds_room) {
        this.ds_room = ds_room;
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
    public String getNm_room() {
        return nm_room;
    }
    public void setNm_room(String nm_room) {
        this.nm_room = nm_room;
    }
  

}
