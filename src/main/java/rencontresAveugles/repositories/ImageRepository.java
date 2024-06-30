package rencontresAveugles.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rencontresAveugles.entities.Image;
import rencontresAveugles.entities.Match;
import rencontresAveugles.entities.User;

public interface ImageRepository extends JpaRepository<Image, Long>{
	
	List<Image> findByUser(User user);
	
	@Query("SELECT i FROM Image i WHERE i.numero = :photoNumero AND i.user = :user")
	Image findImage1ByUser(@Param("photoNumero") int numero, @Param("user") User user);

}
