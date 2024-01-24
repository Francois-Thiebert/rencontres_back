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

import rencontresAveugles.entities.Match;
import rencontresAveugles.entities.User;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.MatchService;
import rencontresAveugles.services.UserService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/match")
public class MatchRestController {
	
	@Autowired
	private MatchService matchSrv;
	@Autowired
	private UserService userSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.Match.class)
	public List<Match> getAll(){
		return matchSrv.getAll();
	}
	
	@GetMapping("/all/{id}")
	@JsonView(JsonViews.Match.class)
	public List<Match> getAllUserMatch(@PathVariable Long id){
		return matchSrv.getAllByUser(id);
	}
	
	@GetMapping("/all_user1/{id}")
	@JsonView(JsonViews.Match.class)
	public List<Match> getAllUser1Match(@PathVariable Long id){
		return matchSrv.getAllMatchUser1(id);
	}
	
	@GetMapping("/all_user2/{id}")
	@JsonView(JsonViews.Match.class)
	public List<Match> getAllUser2Match(@PathVariable Long id){
		return matchSrv.getAllMatchUser2(id);
	}
	
	@GetMapping("/user/{id}")
	@JsonView(JsonViews.Match.class)
	public Match getByIdWithUser(@PathVariable Long id) {
		Match match = null;
		match = matchSrv.getById(id);
		return match;
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.Simple.class)
	public Match getById(@PathVariable Long id) {
		Match match = null;
		match = matchSrv.getById(id);
		return match;
	}
	
//	@GetMapping("/user1/{idUser1}/user2/{idUser2}")
//	@JsonView(JsonViews.Match.class)
//	public List<Match> getAllUserMatch(@PathVariable Long idUser1, Long idUser2){
//		return matchSrv.getMatchByUsersId(idUser1, idUser2);
//	}
	
	@GetMapping("/user1/{idUser1}/user2/{idUser2}")
	@JsonView(JsonViews.Match.class)
	public Match getMatchByUsers(@PathVariable Long idUser1, @PathVariable Long idUser2) {
	    return matchSrv.getMatchByUsers(idUser1, idUser2);
	}
	
//	@GetMapping("/user1/{idUser1}/user2/{idUser2}")
//	@JsonView(JsonViews.Match.class)
//	public List<Match> getMatchByUsers(@PathVariable Long idUser1, @PathVariable Long idUser2) {
//	    return matchSrv.getMatchByUsers(idUser1, idUser2);
//	}

	
	@PostMapping({"","/submit"})
	@JsonView(JsonViews.Match.class)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Match create(@Valid @RequestBody Match match, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		matchSrv.create(match);
		return match;
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.Match.class)
	public Match update(@RequestBody Match match, @PathVariable Long id) {
		Match matchEnBase = matchSrv.getById(id);
		if (match.getCompteur() > 0) {
			matchEnBase.setCompteur(match.getCompteur());
		}
		matchSrv.update(matchEnBase);
		return matchEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    matchSrv.deleteById(id);
	}

}
