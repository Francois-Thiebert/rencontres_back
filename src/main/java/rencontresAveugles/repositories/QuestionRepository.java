package rencontresAveugles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rencontresAveugles.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	
	List<Question> findByPriorite (Long priorite);
	
}
