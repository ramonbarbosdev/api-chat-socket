package app.chat.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.chat.dto.RoomDTO;
import app.chat.model.Room;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface  RoomRepository extends CrudRepository<Room, Long>  {

    Room save(RoomDTO objeto);

	
	
}
