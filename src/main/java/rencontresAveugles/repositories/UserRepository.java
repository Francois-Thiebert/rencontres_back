package rencontresAveugles.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rencontresAveugles.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByLogin(String login);

}
