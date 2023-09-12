package rencontresAveugles.restcontrollers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import rencontresAveugles.entities.User;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserRestController {
	
	@Autowired
	private UserService userSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.User.class)
	public List<User> getAll(){
		return userSrv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.User.class)
	public User getById(@PathVariable Long id) {
		User user = null;
		user = userSrv.getById(id);
		return user;
	}
	
	@GetMapping("/all/{id}")
	@JsonView(JsonViews.UsertWithAll.class)
	public User getUserAllById(@PathVariable Long id) {
		User user = null;
		user = userSrv.getById(id);
		return user;
	}
	
	@GetMapping("/login/check/{login}")
	public boolean loginExist(@PathVariable String login) {
		return userSrv.loginExist(login);
	}
	
	@PostMapping({"","/inscription"})
	@JsonView(JsonViews.User.class)
	@ResponseStatus(code = HttpStatus.CREATED)
	public User create(@Valid @RequestBody User user, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		userSrv.create(user);
		return user;
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.UsertWithAll.class)
	public User update(@RequestBody User user, @PathVariable Long id) {
		User userEnBase = userSrv.getById(id);
		if (user.getPrenom() != null) {
			userEnBase.setPrenom(user.getPrenom());
		}
		if (user.getLogin() != null) {
			userEnBase.setLogin(user.getLogin());
		}
		if (user.getPassword() != null) {
			userEnBase.setPassword(user.getPassword());
		}
		if (user.getReponse() != null) {
			userEnBase.setReponse(user.getReponse());
		}
		userEnBase.setAge(user.getAge());
		userSrv.update(userEnBase);
		return userEnBase;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    userSrv.deleteByUserId(id);
	}


}
