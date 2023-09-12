package rencontresAveugles.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rencontresAveugles.entities.Message;
import rencontresAveugles.entities.User;

public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Message m WHERE m.emetteur.id = :userId OR m.destinataire.id = :userId")
	void deleteMessageByUser(@Param("userId") Long userId);
	
	List<Message> findByEmetteur(User emetteur);
	List<Message> findByDestinataire(User destinataire);
	List<Message> findByEmetteurAndDestinataire(User emetteur, User destinataire);
	List<Message> findByDestinataireAndEmetteur(User emetteur, User destinataire);

}
