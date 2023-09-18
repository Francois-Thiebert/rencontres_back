package rencontresAveugles.services;

import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rencontresAveugles.entities.Image;
import rencontresAveugles.entities.User;
import rencontresAveugles.exceptions.ImageException;
import rencontresAveugles.repositories.ImageRepository;
import rencontresAveugles.repositories.UserRepository;

@Service
public class ImageService {
	
	@Autowired
	private ImageRepository imageRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private Validator validator;
	
	public Image create(Image image) {
		Set<ConstraintViolation<Image>> violations = validator.validate(image);
		if (violations.isEmpty()) {
			Image imageEntity = new Image();
		    imageEntity.setType(image.getType());
		    imageEntity.setImageByte(image.getImageByte());

			imageRepo.save(image);

			return image;
		} else {
			throw new ImageException();
		}
	}

	public List<Image> getAll() {
		return imageRepo.findAll();
	}

	public Image getById(Long id) {
		return imageRepo.findById(id).orElseThrow(ImageException::new);
	}
	
	public List<Image> getByUser(Long id) {
		User user = new User();
		user.setId(id);
		return imageRepo.findByUser(user);
	}
	
	
	public Image update(Image image) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Image>> violations = validator.validate(image);
		if (violations.isEmpty()) {
			Image imageEnBase = getById(image.getId());
			image.setNom(imageEnBase.getNom());
			image.setType(imageEnBase.getType());
			image.setImageByte(imageEnBase.getImageByte());
			return imageRepo.save(imageEnBase);
		} else {
			throw new ImageException();
		}
	}

	public void delete(Image image) {
		imageRepo.delete(image);
	}
	
	public void deleteById(Long Id) {
	    Image imageEnBase = imageRepo.findById(Id).orElseThrow(ImageException::new);
	    imageRepo.delete(imageEnBase);
	}

}
