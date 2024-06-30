package rencontresAveugles.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rencontresAveugles.entities.Match;
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
	private MatchService matchSrv;
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
	
	//Retourne la liste des users impliqués en match avec le user en paramètre
	public List<User> getByMatchs(User user){
		List<User> usersMatchs = new ArrayList<>();
		List<User> usersMatchs1 = new ArrayList<>();
		List<User> usersMatchs2 = new ArrayList<>();
		List<Match> matchUser1 = matchSrv.getAllMatchUser1(user.getId());
		List<Match> matchUser2 = matchSrv.getAllMatchUser2(user.getId());
		for (Match m: matchUser1) {
			User user2 = m.getUser2();
			usersMatchs2.add(user2);
		}
		for (Match m: matchUser2) {
			User user1 = m.getUser1();
			usersMatchs1.add(user1);
		}
		usersMatchs.addAll(usersMatchs1);
		usersMatchs.addAll(usersMatchs2);
		return usersMatchs;
	}
	
	//Retourne la liste des ID des users impliqués en match avec le user en paramètre
		public List<Long> getIDsByMatchs(User user){
			List<User> usersMatchs = new ArrayList<>();
			List<User> usersMatchs1 = new ArrayList<>();
			List<User> usersMatchs2 = new ArrayList<>();
			List<Match> matchUser1 = matchSrv.getAllMatchUser1(user.getId());
			List<Match> matchUser2 = matchSrv.getAllMatchUser2(user.getId());
			for (Match m: matchUser1) {
				User user2 = m.getUser2();
				usersMatchs2.add(user2);
			}
			for (Match m: matchUser2) {
				User user1 = m.getUser1();
				usersMatchs1.add(user1);
			}
			usersMatchs.addAll(usersMatchs1);
			usersMatchs.addAll(usersMatchs2);
			List<Long> userIds = usersMatchs.stream()
		            .map(User::getId)
		            .collect(Collectors.toList());

		    return userIds;
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
			userEnBase.setPhotos(user.getPhotos());
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
