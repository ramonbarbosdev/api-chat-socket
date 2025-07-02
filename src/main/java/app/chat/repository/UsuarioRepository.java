package app.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.chat.model.Usuario;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	// consultar usuario por login
	@Query("select u from Usuario u where u.login = ?1")
	Usuario findUserByLogin(String login);

	@org.springframework.transaction.annotation.Transactional
	@Modifying
	@Query(nativeQuery = true, value = "update usuario set token = ?1 where login = ?2")
	void atualizarTokenUser(String token, String login);

	@Query("""
				SELECT u
				FROM Usuario u
				WHERE u.id <> :id_usuario
				AND u.id NOT IN (
					SELECT ru.usuario.id
					FROM RoomUsuario ru
					WHERE ru.room.id_room = :id_room
				)
			""")
	List<Usuario> findUsuarioDisponivelConvite(@Param("id_room") Long id_room, @Param("id_usuario") Long id_usuario);

	@Query("""
			    SELECT u FROM Usuario u
			    WHERE u.id <> :id_usuario
			      AND NOT EXISTS (
			        SELECT 1
			        FROM Friendship f
			        WHERE
			          (f.id_requester.id = :id_usuario AND f.id_receiver.id = u.id)
			          OR
			          (f.id_receiver.id = :id_usuario AND f.id_requester.id = u.id)
			      )
			""")
	List<Usuario> findUsuariosDisponiveisAmizade(@Param("id_usuario") Long id_usuario);
}
