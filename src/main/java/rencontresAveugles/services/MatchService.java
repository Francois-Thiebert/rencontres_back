package rencontresAveugles.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rencontresAveugles.entities.Match;
import rencontresAveugles.entities.Reponse;
import rencontresAveugles.entities.User;
import rencontresAveugles.exceptions.MatchException;
import rencontresAveugles.exceptions.ReponseException;
import rencontresAveugles.repositories.MatchRepository;
import rencontresAveugles.repositories.UserRepository;

@Service
public class MatchService {
	
	@Autowired
	private MatchRepository matchRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private Validator validator;
	
	public Match create(Match match) {
		Set<ConstraintViolation<Match>> violations = validator.validate(match);
		if (violations.isEmpty()) {
			matchRepo.save(match);
			return match;
		} else {
			throw new MatchException();
		}
	}

	public List<Match> getAll() {
		return matchRepo.findAll();
	}

	public Match getById(Long id) {
		return matchRepo.findById(id).orElseThrow(MatchException::new);
	}

	public List<Match> getAllByUser(Long id) {
		User user = new User();
		user.setId(id);
		List<Match> matchs = new ArrayList<>();
		List<Match> matchsUser1 = new ArrayList<>();
		List<Match> matchsUser2 = new ArrayList<>();
		matchsUser1 = matchRepo.findByUser1(user);
		matchsUser2 = matchRepo.findByUser2(user);
		matchsUser1.forEach((m) -> matchs.add(m));
		matchsUser2.forEach((m) -> matchs.add(m));
		return matchs;
	}
	
	public List<Match> getAllMatchUser1(Long id) {
		User user = new User();
		user.setId(id);
		List<Match> matchsUser1 = new ArrayList<>();
		matchsUser1 = matchRepo.findByUser1(user);
		return matchsUser1;
	}
	
	public List<Match> getAllMatchUser2(Long id) {
		User user = new User();
		user.setId(id);
		List<Match> matchsUser2 = new ArrayList<>();
		matchsUser2 = matchRepo.findByUser2(user);
		return matchsUser2;
	}
	
//	public List<Match> getMatchByUsersId(Long idUser1, Long idUser2) {
//	    List<Match> matchs = new ArrayList<>();
//	    List<Long> matchIds = matchRepo.findMatchesByUserIds(idUser1, idUser2);
//
//	    for (Long matchId : matchIds) {
//	        Match match = matchRepo.findById(matchId).orElse(null);
//	        if (match != null) {
//	            matchs.add(match);
//	        }
//	    }
//
//	    return matchs;
//	}

	
	public List<Match> getMatchByUsersId(Long idUser1, Long idUser2) {
		List<Match> matchs = new ArrayList<>();
		matchs = matchRepo.findMatchesByUserIds(idUser1, idUser2);
		return matchs;
	}
	
	public Match getMatchByUsers(Long idUser1, Long idUser2) {
		Match match = new Match();
		Optional<User> optionalUser1 = userRepo.findById(idUser1);
		Optional<User> optionalUser2 = userRepo.findById(idUser2);
		if (optionalUser1.isPresent() && optionalUser2.isPresent()) {
		    User user1 = optionalUser1.get();
		    User user2 = optionalUser2.get();
		    match = matchRepo.findByUser1AndUser2(user1, user2);
		    
		    // Utilisez user1 et user2 comme nécessaire
		} else {
		    // Gérez le cas où l'utilisateur n'a pas été trouvé
		}
		return match;
	    
	}
	
//	public List<Match> getMatchByUsers(Long idUser1, Long idUser2) {
//		List<Match> matchs = new ArrayList<Match>();
//		Optional<User> optionalUser1 = userRepo.findById(idUser1);
//		Optional<User> optionalUser2 = userRepo.findById(idUser2);
//		if (optionalUser1.isPresent() && optionalUser2.isPresent()) {
//		    User user1 = optionalUser1.get();
//		    User user2 = optionalUser2.get();
//		    matchs = matchRepo.findByUser1AndUser2(user1, user2);
//		    
//		    // Utilisez user1 et user2 comme nécessaire
//		} else {
//		    // Gérez le cas où l'utilisateur n'a pas été trouvé
//		}
//		return matchs;
//	    
//	}
	
	
	
	public Match update(Match match) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Match>> violations = validator.validate(match);
		if (violations.isEmpty()) {
			Match matchEnBase = getById(match.getId());
			matchEnBase.setUser1(match.getUser1());
			matchEnBase.setUser2(match.getUser2());
			matchEnBase.setCompatibilite(match.getCompatibilite());
			matchEnBase.setDate(match.getDate());
			matchEnBase.setCompteur(match.getCompteur());
			return matchRepo.save(matchEnBase);
		} else {
			throw new MatchException();
		}
	}

	public void delete(Match match) {
		matchRepo.delete(match);
	}
	
	public void deleteById(Long Id) {
	    Match matchEnBase = matchRepo.findById(Id).orElseThrow(MatchException::new);
	    matchRepo.delete(matchEnBase);
	}

}


