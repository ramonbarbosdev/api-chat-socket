package app.chat.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import app.chat.model.RoomUsuario;
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
    @Query("DELETE FROM RoomUsuario i WHERE  i.room.id_room = :id_room")
    void deleteByIdRoomUsuario(@Param("id_room") Long id_room);


}