package rencontresAveugles.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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
	@Autowired
	private ReponseService reponseSrv;
	@Autowired
	private UserService userSrv;
	
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
	
	public Match getNewMatch(User user, List<User> users) {
		Match match = new Match();
		List<Reponse> reponsesUser = reponseSrv.getByUser(user);
		Iterator<Reponse> iterator = reponsesUser.iterator();
		while (iterator.hasNext()) {
		    Reponse r = iterator.next();
		    if (r.getQuestion().getId() == 18 || r.getQuestion().getId() == 19) {
		        iterator.remove();
		    }
		}
		List<Match> matchsPotentiels= new ArrayList<>();
		System.out.println("-----------------Creation des matchs potentiels-----------------------");
		for(User u: users) {
			Match matchPotentiel = new Match();
			matchPotentiel.setId(u.getId());
			matchsPotentiels.add(matchPotentiel);
			//System.out.println("le match "+matchPotentiel.getId()+" est ajouté à la liste des matchs potentiels");
		}
		//System.out.println("il y a "+matchsPotentiels.size()+" matchs potentiels dans la liste");
		System.out.println("-----------------Gestion des compatibilités-----------------------");
		for(Reponse r: reponsesUser) {
			//System.out.println("boucle : pour chaque réponse de l'utilisateur");
			if(r.getNumeroReponse() != 1) {
				//System.out.println("SI : le numéro de réponse est différent de 1");
				for(User u: users) {
					//System.out.println("boucle : pour chaque utilisateur de la liste users");
					Reponse reponseAutre = reponseSrv.getByQuestionUserId(r.getQuestion().getId(), u.getId());
					//System.out.println("la réponse à la question numéro "+r.getQuestion().getId()+" de l'utilisateur distant numéro "+u.getId()+" est : "+reponseAutre.getNumeroReponse());
					if(reponseAutre.getNumeroReponse() == r.getNumeroReponse()) {
						//System.out.println("SI : les deux réponses sont égales : "+ r.getNumeroReponse()+" et "+ reponseAutre.getNumeroReponse());
						for (Match m: matchsPotentiels) {
							//System.out.println("boucle : pour chaque match potentiel");
							//System.out.println("l'id du match potentiel est : "+m.getId()+" et l'id du user distant est : "+u.getId());
							if (m.getId() == u.getId()) {
								//System.out.println("SI : l'id du match potentiel est égal à l'id du user distant");
								int newCompatibilite = m.getCompatibilite()+1;
								m.setCompatibilite(newCompatibilite);
								//System.out.println("le match potentiel numéro "+m.getId()+" gagne +1 en compatibilité");
							}
						}
					}
				}
			}
		}
		for (Match m: matchsPotentiels) {
			int compatibilite = m.getCompatibilite();
			int nombreDeReponses = reponsesUser.size();
			int pourcentCompatibilite = (int) (((double) compatibilite / nombreDeReponses) * 100);
			m.setCompatibilite(pourcentCompatibilite);
			System.out.println("le match potentiel numéro : "+m.getId()+" compte : "+m.getCompatibilite()+"% de compatibilité");
		}
		if (!matchsPotentiels.isEmpty()) {
	        Match matchOptimal = matchsPotentiels.get(0);
	        
	        for (int i = 1; i < matchsPotentiels.size(); i++) {
	            Match matchCourant = matchsPotentiels.get(i);

	            if (matchCourant.getCompatibilite() > matchOptimal.getCompatibilite()) {
	                matchOptimal = matchCourant;
	            }
	        }
	        match=matchOptimal;
	        match.setCompatibilite(matchOptimal.getCompatibilite());
	        match.setUser1(userSrv.getById(user.getId()));
	        match.setUser2(userSrv.getById(matchOptimal.getId()));
	        match.setId(null);
	        match.setDate(LocalDate.now());
	        match.setCompteur(0);
		}
		return match;
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


