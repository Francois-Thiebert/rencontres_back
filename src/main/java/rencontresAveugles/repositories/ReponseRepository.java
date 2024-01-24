package rencontresAveugles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rencontresAveugles.entities.Question;
import rencontresAveugles.entities.Reponse;
import rencontresAveugles.entities.User;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {
	
	List<Reponse> findByUser(User user);
	List<Reponse> findByQuestion(Question question);

}