package rencontresAveugles.restcontrollers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.entities.User;
import rencontresAveugles.jsonviews.JsonViews;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthentificationRestController {
	
	@GetMapping("")
	@JsonView(JsonViews.User.class)
	public User authentification(@AuthenticationPrincipal User user) {
		return user;
	}
	
//	@PostMapping("") // Change to PostMapping
//    @JsonView(JsonViews.User.class)
//    public User authentification(@AuthenticationPrincipal User user) {
//        return user;
//    }

}
