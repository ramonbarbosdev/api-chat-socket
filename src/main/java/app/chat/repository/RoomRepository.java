package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.chat.dto.RoomDTO;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface  RoomRepository extends CrudRepository<Room, Long>  {


   @Query("""
    SELECT ru.room
    FROM RoomUsuario ru
        WHERE ru.usuario.id = :id_usuario
    """)
    List<Room> findSalasCompartilhadasComUsuario(@Param("id_usuario") Long id_usuario);

     @Query("""
    SELECT cast(1 as boolean) as fl_responsavel
    FROM Room ru
        WHERE ru.id_usuario = :id_usuario
        and ru.id_room = :id_room
    """)
	Boolean findVerificarResponsavelSala(@Param("id_usuario") Long id_usuario, @Param("id_room") Long id_room);

//    @Query("""
//     select cast(1 as boolean) as fl_existe 
//     from Room r 
//     join RoomUsuario ru on r.id_room  = ru.id_room
//     where ru.id_room = :id_room 
//     and ru.id_usuario = :id_usuario
//     """)
//     List<Room> findVerificarUsuarioSala(@Param("id_room") Long id_room, @Param("id_usuario") Long id_usuario);
	
	
}
