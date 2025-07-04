package app.chat.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


@Repository
@Transactional
public interface RoomUsuarioRepository extends CrudRepository<RoomUsuario, Long> {
    List<RoomUsuario> findByUsuarioId(Long id_usuario);

    @Modifying
    @Query("DELETE FROM RoomUsuario i WHERE i.room.id_room = :id_room ")
    void deleteByIdRoomUsuario(@Param("id_room") Long id_room);

    @Modifying
    @Query("""
         DELETE FROM RoomUsuario i 
             WHERE  i.room.id_room = :id_room
             and  i.usuario.id = :id_usuario
            
        """)
    void deleteByIdRoomIdUsuario(@Param("id_usuario") Long id_usuario, @Param("id_room") Long id_room);

    boolean existsByRoomAndUsuario(Room room, Usuario usuario);

     @Query("""
            SELECT ru
            FROM RoomUsuario ru
            WHERE ru.usuario.id = :id_usuario
            AND ru.room.id_room = :id_room
            """)
    RoomUsuario findUsuariosByidUsuario(@Param("id_usuario") Long id_usuario, @Param("id_room") Long id_room);
}