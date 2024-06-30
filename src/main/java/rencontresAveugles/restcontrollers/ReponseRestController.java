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
import rencontresAveugles.entities.Reponse;
import rencontresAveugles.entities.User;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.QuestionService;
import rencontresAveugles.services.ReponseService;
import rencontresAveugles.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reponse")
public class ReponseRestController {
	
	@Autowired
	private ReponseService reponseSrv;
	@Autowired
	private UserService userSrv;
	@Autowired
	private QuestionService questionSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.Reponse.class)
	public List<Reponse> getAll(){
		return reponseSrv.getAll();
	}
	
	@GetMapping("/user/{id}")
	@JsonView(JsonViews.Reponse.class)
	public List<Reponse> getByIdWithUser(@PathVariable Long id) {
		List<Reponse> reponses = null;
		User user = new User();
		user = userSrv.getById(id);
		reponses = reponseSrv.getByUser(user);
		return reponses;
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.Simple.class)
	public Reponse getById(@PathVariable Long id) {
		Reponse reponse = null;
		reponse = reponseSrv.getById(id);
		return reponse;
	}
	
	@GetMapping("/question/{id}")
	@JsonView(JsonViews.Simple.class)
	public List<Reponse> getByQuestion(@PathVariable Long id) {
		List<Reponse> reponses = null;
		Question question = null;
		question = questionSrv.getById(id);
		reponses = reponseSrv.getByQuestion(question);
		return reponses;
	}
	
	@GetMapping("/question/{questionId}/user/{userId}")
	@JsonView(JsonViews.Reponse.class)
	public Reponse getMatchByUsers(@PathVariable Long questionId, @PathVariable Long userId) {
	    return reponseSrv.getByQuestionUserId(questionId, userId);
	}
	
	@PostMapping({"","/submit"})
	@JsonView(JsonViews.Reponse.class)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Reponse create(@Valid @RequestBody Reponse reponse, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		reponseSrv.create(reponse);
		return reponse;
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.Reponse.class)
	public Reponse update(@RequestBody Reponse reponse, @PathVariable Long id) {
		Reponse reponseEnBase = reponseSrv.getById(id);
		reponseEnBase.setNumeroReponse(reponse.getNumeroReponse());
		reponseSrv.update(reponseEnBase);
		return reponseEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    reponseSrv.deleteByReponseId(id);
	}

}
