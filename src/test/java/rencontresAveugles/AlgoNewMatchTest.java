package rencontresAveugles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import rencontresAveugles.entities.User;
import rencontresAveugles.services.MatchService;

public class AlgoNewMatchTest {
	
	@Autowired
	MatchService matchSrv;
	
	public static void main(String[] args) {
		
		User user1 = new User();
		User user2 = new User();
		User user3 = new User();
		User user4 = new User();
		List<User> users = new ArrayList<>();
		
		

        System.out.println("Bonjour, ceci est ma classe principale Java !");
    }

}
