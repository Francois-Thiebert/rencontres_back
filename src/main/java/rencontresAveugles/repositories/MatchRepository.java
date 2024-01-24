package rencontresAveugles.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rencontresAveugles.entities.Match;
import rencontresAveugles.entities.User;


public interface MatchRepository extends JpaRepository<Match, Long>{
	
	@Transactional
	@Modifying
	@Query("delete from Match m where m.user1.id = :userId or m.user2.id = :userId")
	void deleteMatchByUser(@Param("userId") Long userId);
	
	
	@Query("SELECT m FROM Match m WHERE (m.user1.id = :idUser1 AND m.user2.id = :idUser2) OR (m.user1.id = :idUser2 AND m.user2.id = :idUser1)")
	List<Match> findMatchesByUserIds(@Param("idUser1") Long idUser1, @Param("idUser2") Long idUser2);
	
	Match findByUser1AndUser2(User user1, User user2);
	List<Match> findByUser1(User user);
	List<Match> findByUser2(User user);

}
