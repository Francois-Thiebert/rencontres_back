package rencontresAveugles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rencontresAveugles.entities.Question;
import rencontresAveugles.entities.Reponse;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
}
