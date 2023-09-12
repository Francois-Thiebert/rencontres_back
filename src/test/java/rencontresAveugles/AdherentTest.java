package rencontresAveugles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import rencontresAveugles.entities.User;
import rencontresAveugles.services.UserService;

@SpringBootTest
public class AdherentTest {
	
	@Autowired
	UserService userSrv;
	
	@Test
	@Commit
	void ajoutUser() {
		User user = new User();
		userSrv.create(user);
		assertNotNull(user.getId());
	}

}
