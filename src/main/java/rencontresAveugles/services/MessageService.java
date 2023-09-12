package rencontresAveugles.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rencontresAveugles.entities.Match;
import rencontresAveugles.entities.Message;
import rencontresAveugles.entities.User;
import rencontresAveugles.exceptions.MatchException;
import rencontresAveugles.exceptions.MessageException;
import rencontresAveugles.repositories.MessageRepository;

@Service
public class MessageService {
	
	@Autowired
	private Validator validator;
	@Autowired
	private MessageRepository messageRepo;
	
	public Message create(Message message) {
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		if (violations.isEmpty()) {
			messageRepo.save(message);
			return message;
		} else {
			throw new MessageException();
		}
	}

	public List<Message> getAll() {
		return messageRepo.findAll();
	}

	public Message getById(Long id) {
		return messageRepo.findById(id).orElseThrow(MessageException::new);
	}

	public List<Message> getByDestinataire(Long id) {
		User user = new User();
		user.setId(id);
		return messageRepo.findByDestinataire(user);
	}
	
	public List<Message> getByEmetteur(Long id) {
		User user = new User();
		user.setId(id);
		return messageRepo.findByEmetteur(user);
	}
	
	public List<Message> getByEmetteurAndDestinataire(Long idEmetteur, Long idDestinataire) {
		User emetteur = new User();
		User destinataire = new User();
		emetteur.setId(idEmetteur);
		destinataire.setId(idDestinataire);
		List<Message> messagesDestinataireEmetteur = new ArrayList<>();
		messagesDestinataireEmetteur = messageRepo.findByDestinataireAndEmetteur(emetteur, destinataire);
		List<Message> messagesEmetteurDestinataire = new ArrayList<>();
		messagesEmetteurDestinataire = messageRepo.findByEmetteurAndDestinataire(emetteur, destinataire);
		List<Message> messagesTotaux = new ArrayList<>();
		messagesDestinataireEmetteur.forEach((m) -> messagesTotaux.add(m));
		messagesEmetteurDestinataire.forEach((m) -> messagesTotaux.add(m));
		return messagesTotaux;
	}
	
	public Message update(Message message) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Message>> violations = validator.validate(message);
		if (violations.isEmpty()) {
			Message messageEnBase = getById(message.getId());
			return messageRepo.save(messageEnBase);
		} else {
			throw new MessageException();
		}
	}

	public void delete(Message message) {
		messageRepo.delete(message);
	}
	
	public void deleteById(Long Id) {
	    Message messageEnBase = messageRepo.findById(Id).orElseThrow(MessageException::new);
	    messageRepo.delete(messageEnBase);
	}

}
