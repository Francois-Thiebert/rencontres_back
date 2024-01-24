package rencontresAveugles.services;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rencontresAveugles.entities.Role;
import rencontresAveugles.entities.User;
import rencontresAveugles.exceptions.UserException;
import rencontresAveugles.repositories.MatchRepository;
import rencontresAveugles.repositories.MessageRepository;
import rencontresAveugles.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private Validator validator;
	@Autowired
	private MatchRepository matchRepo;
	@Autowired
	private MessageRepository messageRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public List<User> getAll(){
		return userRepo.findAll();
	}
	
	public User getById (Long id) {
		return userRepo.findById(id).orElseThrow(UserException::new);
	}
	
	public User create(User user) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		if (violations.isEmpty()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setRole(Role.ROLE_USER);
			return userRepo.save(user);
		}else {
			throw new UserException();
		}
	}
	
	public User update(User user) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		if (violations.isEmpty()) {
			User userEnBase = getById(user.getId());
			userEnBase.setMatchs1(user.getMatchs1());
			userEnBase.setMatchs2(user.getMatchs2());
			userEnBase.setMessagesEmis(user.getMessagesEmis());
			userEnBase.setMessagesReçus(user.getMessagesReçus());
			userEnBase.setAge(user.getAge());
			userEnBase.setLogin(user.getLogin());
			userEnBase.setPrenom(user.getPrenom());
			userEnBase.setReponses(user.getReponses());
			return userRepo.save(userEnBase);
		} else {
			throw new UserException();
		}
	}
	
	public void deleteByUser(User user) {
		User userEnBase = userRepo.findById(user.getId()).orElseThrow(UserException::new);
		matchRepo.deleteMatchByUser(userEnBase.getId());
		messageRepo.deleteMessageByUser(userEnBase.getId());
		userRepo.delete(userEnBase);
	}
	public void deleteByUserId(Long userId) {
	    User userEnBase = userRepo.findById(userId).orElseThrow(UserException::new);
	    matchRepo.deleteMatchByUser(userEnBase.getId());
	    messageRepo.deleteMessageByUser(userEnBase.getId());
	    userRepo.delete(userEnBase);
	}
	
	public boolean loginExist(String login) {
		return userRepo.findByLogin(login).isPresent();
	}

	
	public User findByLogin(String login) {
		return userRepo.findByLogin(login).orElseThrow(() -> {
			throw new UserException();
		});
	}

}
