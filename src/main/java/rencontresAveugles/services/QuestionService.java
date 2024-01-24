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
import rencontresAveugles.exceptions.QuestionException;
import rencontresAveugles.exceptions.ReponseException;
import rencontresAveugles.repositories.QuestionRepository;

@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepo;
	@Autowired
	private Validator validator;
	
	public Question create(Question question) {
		Set<ConstraintViolation<Question>> violations = validator.validate(question);
		if (violations.isEmpty()) {
			questionRepo.save(question);
			return question;
		} else {
			throw new QuestionException();
		}
	}

	public List<Question> getAll() {
		return questionRepo.findAll();
	}

	public Question getById(Long id) {
		return questionRepo.findById(id).orElseThrow(QuestionException::new);
	}
	
	public Question update(Question question) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Question>> violations = validator.validate(question);
		if (violations.isEmpty()) {
			Question questionEnBase = getById(question.getId());
			questionEnBase.setIntitule(question.getIntitule());
			questionEnBase.setReponse1(question.getReponse1());
			questionEnBase.setReponse2(question.getReponse2());
			questionEnBase.setReponse3(question.getReponse3());
			questionEnBase.setReponse4(question.getReponse4());
			questionEnBase.setReponse5(question.getReponse5());
			questionEnBase.setReponse6(question.getReponse6());
			questionEnBase.setReponse7(question.getReponse7());
			questionEnBase.setReponse8(question.getReponse8());
			return questionRepo.save(questionEnBase);
		} else {
			throw new QuestionException();
		}
	}

	public void delete(Question question) {
		questionRepo.delete(question);
	}
	
	public void deleteByQuestion(Question question) {
		Question questionEnBase = questionRepo.findById(question.getId()).orElseThrow(QuestionException::new);
		questionRepo.delete(questionEnBase);
	}
	public void deleteByQuestionId(Long questionId) {
	    Question questionEnBase = questionRepo.findById(questionId).orElseThrow(QuestionException::new);
	    questionRepo.delete(questionEnBase);
	}

}
