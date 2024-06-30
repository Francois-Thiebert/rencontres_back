package rencontresAveugles.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rencontresAveugles.entities.Question;
import rencontresAveugles.entities.Reponse;
import rencontresAveugles.entities.User;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {
	
	@Transactional
	@Modifying
	@Query("delete from Reponse r where r.question.id = :questionId")
	void deleteReponseByQuestion(@Param("questionId") Long questionId);
	
	@Query("SELECT r FROM Reponse r WHERE (r.user.id = :userId AND r.question.id = :questionId)")
	Reponse findByQuestionUser(@Param("questionId") Long questionId, @Param("userId") Long userId);
	
	List<Reponse> findByUser(User user);
	List<Reponse> findByQuestion(Question question);

}