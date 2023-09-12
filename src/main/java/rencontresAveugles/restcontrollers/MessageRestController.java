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

import rencontresAveugles.entities.Message;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.MessageService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/message")
public class MessageRestController {
	
	@Autowired
	private MessageService messageSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.Message.class)
	public List<Message> getAll(){
		return messageSrv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.Message.class)
	public Message getMessage(@PathVariable Long id){
		return messageSrv.getById(id);
	}
	
	@GetMapping("/emetteur/{id}")
	@JsonView(JsonViews.Message.class)
	public List<Message> getEmetteurMessage(@PathVariable Long id){
		return messageSrv.getByEmetteur(id);
	}
	
	@GetMapping("/destinataire/{id}")
	@JsonView(JsonViews.Message.class)
	public List<Message> getDestinataireMessage(@PathVariable Long id){
		return messageSrv.getByDestinataire(id);
	}
	
	@GetMapping("/user1/{idUser1}/user2/{idUser2}")
	@JsonView(JsonViews.Message.class)
	public List<Message> getMessagesByUsers(@PathVariable Long idUser1, @PathVariable Long idUser2) {
	    return messageSrv.getByEmetteurAndDestinataire(idUser1, idUser2);
	}
	
	@GetMapping("/user/{id}")
	@JsonView(JsonViews.Message.class)
	public Message getByIdWithUser(@PathVariable Long id) {
		Message message = null;
		message = messageSrv.getById(id);
		return message;
	}
	
	@PostMapping("")
	@JsonView(JsonViews.Message.class)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Message create(@Valid @RequestBody Message message, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		messageSrv.create(message);
		return message;
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.Message.class)
	public Message update(@RequestBody Message message, @PathVariable Long id) {
		Message messageEnBase = messageSrv.getById(id);
		messageSrv.update(messageEnBase);
		return messageEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    messageSrv.deleteById(id);
	}

}
