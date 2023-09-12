package rencontresAveugles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rencontresAveugles.entities.Reponse;
import rencontresAveugles.entities.User;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {
	
	Reponse findByUser(User user);

}