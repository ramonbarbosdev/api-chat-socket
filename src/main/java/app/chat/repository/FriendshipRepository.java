package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.chat.dto.RoomDTO;
import app.chat.model.Friendship;
import app.chat.model.Room;
import app.chat.model.RoomUsuario;
import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface  FriendshipRepository extends CrudRepository<Friendship, Long>  {


   
	
}
