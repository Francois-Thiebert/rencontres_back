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

import rencontresAveugles.entities.Question;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.QuestionService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/question")
public class QuestionRestController {
	
	@Autowired
	private QuestionService questionSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.Simple.class)
	public List<Question> getAll(){
		return questionSrv.getAll();
	}
	
	@GetMapping("/user/{id}")
	@JsonView(JsonViews.Question.class)
	public Question getByIdWithUser(@PathVariable Long id) {
		Question question = null;
		question = questionSrv.getById(id);
		return question;
	}
	
	@GetMapping("/priorite/{prio}")
	@JsonView(JsonViews.Question.class)
	public List<Question> getByPriorite(@PathVariable Long prio) {
		List<Question> questions = null;
		questions = questionSrv.getByPriorite(prio);
		return questions;
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.Simple.class)
	public Question getById(@PathVariable Long id) {
		Question question = null;
		question = questionSrv.getById(id);
		return question;
	}
	
	@PostMapping({"","/submit"})
	@JsonView(JsonViews.Question.class)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Question create(@Valid @RequestBody Question question, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		questionSrv.create(question);
		return question;
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.Question.class)
	public Question update(@RequestBody Question question, @PathVariable Long id) {
		Question questionEnBase = questionSrv.getById(id);
		if (question.getIntitule() != null) {
			questionEnBase.setIntitule(question.getIntitule());
		}
		if (question.getCorrespondance() != null) {
			questionEnBase.setCorrespondance(question.getCorrespondance());
		}
		if (question.getPriorite() != null) {
			questionEnBase.setPriorite(question.getPriorite());
		}
		if (question.getReponse1() != null) {
			questionEnBase.setReponse1(question.getReponse1());
		}
		if (question.getReponse2() != null) {
			questionEnBase.setReponse2(question.getReponse2());
		}
		if (question.getReponse3() != null) {
			questionEnBase.setReponse3(question.getReponse3());
		}
		if (question.getReponse4() != null) {
			questionEnBase.setReponse4(question.getReponse4());
		}
		if (question.getReponse5() != null) {
			questionEnBase.setReponse5(question.getReponse5());
		}
		if (question.getReponse6() != null) {
			questionEnBase.setReponse6(question.getReponse6());
		}
		if (question.getReponse7() != null) {
			questionEnBase.setReponse7(question.getReponse7());
		}
		if (question.getReponse8() != null) {
			questionEnBase.setReponse8(question.getReponse8());
		}
		questionSrv.update(questionEnBase);
		return questionEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    questionSrv.deleteByQuestionId(id);
	}

}
