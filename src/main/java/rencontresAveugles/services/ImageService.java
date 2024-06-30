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
import rencontresAveugles.entities.Match;
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
		    imageEntity.setNumero(image.getNumero());
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
	
	public Image getPhotoByUserAndNumero(Long userId, int imageNumero) {
		User user = new User();
		user.setId(userId);
		return imageRepo.findImage1ByUser(imageNumero, user);
	}
	
	public List<Image> getByUserTrie(Long id) {
		User user = new User();
		user.setId(id);
		List <Image> photos = new ArrayList<>();
		Image photo1 = getPhotoByUserAndNumero(id, 1);
		if(photo1 != null) {
			photos.add(photo1);
		}
		Image photo2 = getPhotoByUserAndNumero(id, 2);
		if(photo2 != null) {
			photos.add(photo2);
		}
		Image photo3 = getPhotoByUserAndNumero(id, 3);
		if(photo3 != null) {
			photos.add(photo3);
		}
		Image photo4 = getPhotoByUserAndNumero(id, 4);
		if(photo4 != null) {
			photos.add(photo4);
		}
		Image photo5 = getPhotoByUserAndNumero(id, 5);
		if(photo5 != null) {
			photos.add(photo5);
		}
		Image photo6 = getPhotoByUserAndNumero(id, 6);
		if(photo6 != null) {
			photos.add(photo6);
		}
		Image photo7 = getPhotoByUserAndNumero(id, 7);
		if(photo7 != null) {
			photos.add(photo7);
		}
		Image photo8 = getPhotoByUserAndNumero(id, 8);
		if(photo8 != null) {
			photos.add(photo8);
		}
		Image photo9 = getPhotoByUserAndNumero(id, 9);
		if(photo9 != null) {
			photos.add(photo9);
		}
		return photos;
	}
	
	
	public Image update(Image image) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Image>> violations = validator.validate(image);
		if (violations.isEmpty()) {
			Image imageEnBase = getById(image.getId());
			imageEnBase.setImageByte(image.getImageByte());
			imageEnBase.setNumero(image.getNumero());
			imageEnBase.setType(image.getType());
			imageEnBase.setUser(image.getUser());
			return imageRepo.save(imageEnBase);
		} else {
			throw new ImageException();
		}
	}

	public void delete(Image image) {
		imageRepo.delete(image);
	}
	
	public void deleteFromList(User user, Image image) {
		List<Image> images = new ArrayList<>();
		images = imageRepo.findByUser(user);
		for(Image i: images) {
			if(i.getNumero() > image.getNumero()) {
				i.setNumero(i.getNumero()-1);
				imageRepo.save(i);
			}
		}
		imageRepo.delete(image);
		
	}
	
	public void deleteById(Long Id) {
	    Image imageEnBase = imageRepo.findById(Id).orElseThrow(ImageException::new);
	    imageRepo.delete(imageEnBase);
	}

}
