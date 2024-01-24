package rencontresAveugles.services;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rencontresAveugles.entities.Question;
import rencontresAveugles.entities.Reponse;
import rencontresAveugles.entities.User;
import rencontresAveugles.exceptions.ReponseException;
import rencontresAveugles.exceptions.UserException;
import rencontresAveugles.repositories.ReponseRepository;

@Service
public class ReponseService {
	
	@Autowired
	private ReponseRepository reponseRepo;
	@Autowired
	private Validator validator;
	
	public Reponse create(Reponse reponse) {
		Set<ConstraintViolation<Reponse>> violations = validator.validate(reponse);
		if (violations.isEmpty()) {
			reponseRepo.save(reponse);
			return reponse;
		} else {
			throw new ReponseException();
		}
	}

	public List<Reponse> getAll() {
		return reponseRepo.findAll();
	}

	public Reponse getById(Long id) {
		return reponseRepo.findById(id).orElseThrow(ReponseException::new);
	}

	public List<Reponse> getByUser(User user) {
		return reponseRepo.findByUser(user);
	}
	
	public List<Reponse> getByQuestion(Question question) {
		return reponseRepo.findByQuestion(question);
	}
	
	public Reponse update(Reponse reponse) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Reponse>> violations = validator.validate(reponse);
		if (violations.isEmpty()) {
			Reponse reponseEnBase = getById(reponse.getId());
			reponseEnBase.setNumeroReponse(reponse.getNumeroReponse());
			reponseEnBase.setQuestion(reponse.getQuestion());
			reponseEnBase.setUser(reponse.getUser());
			return reponseRepo.save(reponseEnBase);
		} else {
			throw new ReponseException();
		}
	}

	public void delete(Reponse reponse) {
		reponseRepo.delete(reponse);
	}
	
	public void deleteByReponse(Reponse reponse) {
		Reponse reponseEnBase = reponseRepo.findById(reponse.getId()).orElseThrow(ReponseException::new);
		reponseRepo.delete(reponseEnBase);
	}
	public void deleteByReponseId(Long reponseId) {
	    Reponse reponseEnBase = reponseRepo.findById(reponseId).orElseThrow(ReponseException::new);
	    reponseRepo.delete(reponseEnBase);
	}
}
