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

import rencontresAveugles.entities.Reponse;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.ReponseService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reponse")
public class ReponseRestController {
	
	@Autowired
	private ReponseService reponseSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.Reponse.class)
	public List<Reponse> getAll(){
		return reponseSrv.getAll();
	}
	
	@GetMapping("/user/{id}")
	@JsonView(JsonViews.Reponse.class)
	public Reponse getByIdWithUser(@PathVariable Long id) {
		Reponse reponse = null;
		reponse = reponseSrv.getById(id);
		return reponse;
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.Simple.class)
	public Reponse getById(@PathVariable Long id) {
		Reponse reponse = null;
		reponse = reponseSrv.getById(id);
		return reponse;
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
		reponseSrv.update(reponseEnBase);
		return reponseEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    reponseSrv.deleteByReponseId(id);
	}

}
