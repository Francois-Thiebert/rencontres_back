package rencontresAveugles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rencontresAveugles.entities.Image;
import rencontresAveugles.entities.User;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
	List<Image> findByUser(User user);

}
