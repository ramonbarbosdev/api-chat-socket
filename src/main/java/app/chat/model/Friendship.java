package app.chat.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.chat.enums.AmizadeStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_friendship;

    @ManyToOne
    private Usuario id_requester;

    @ManyToOne
    private Usuario id_receiver;

    @Enumerated(EnumType.STRING)
    private AmizadeStatus tp_status;

    private LocalDateTime dt_createdat = LocalDateTime.now();

   public Long getId_friendship() {
       return id_friendship;
   }

   public void setId_friendship(Long id_friendship) {
       this.id_friendship = id_friendship;
   }
   public Usuario getId_requester() {
       return id_requester;
   }
   public void setId_requester(Usuario id_requester) {
       this.id_requester = id_requester;
   }
   public Usuario getId_receiver() {
       return id_receiver;
   }
   public void setId_receiver(Usuario id_receiver) {
       this.id_receiver = id_receiver;
   }

    public AmizadeStatus getTp_status() {
        return tp_status;
    }
    public void setTp_status(AmizadeStatus tp_status) {
        this.tp_status = tp_status;
    }
    public LocalDateTime getDt_createdat() {
        return dt_createdat;
    }
    public void setDt_createdat(LocalDateTime dt_createdat) {
        this.dt_createdat = dt_createdat;
    }


   
   

}
