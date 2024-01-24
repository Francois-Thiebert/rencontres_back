package rencontresAveugles.restcontrollers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;

import rencontresAveugles.entities.Image;
import rencontresAveugles.entities.User;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.ImageService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/image")
public class ImageRestController {
	
	@Autowired
	private ImageService imageSrv;
	
	@GetMapping("")
	@JsonView(JsonViews.Image.class)
	public List<Image> getAll(){
		return imageSrv.getAll();
	}
	
	@GetMapping("/{id}")
	@JsonView(JsonViews.Image.class)
	public Image getImage(@PathVariable Long id){
		return imageSrv.getById(id);
	}
	
	@GetMapping("/user/{id}")
	@JsonView(JsonViews.Image.class)
	public List<Image> getImageByUserId(@PathVariable Long id){
		return imageSrv.getByUser(id);
	}
	
	@GetMapping("/user_image1/{id}")
	@JsonView(JsonViews.Image.class)
	public Image getImage1ByUserId(@PathVariable Long id){
		return imageSrv.getPhoto1ByUser(id);
	}
	
	@PostMapping({""})
	@JsonView(JsonViews.Image.class)
	@ResponseStatus(code = HttpStatus.CREATED)
	public Image create(@Valid @RequestBody Image image, BindingResult br) {
		if (br.hasErrors()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		imageSrv.create(image);
		return image;
	}
	
	@PutMapping("/{id}")
	@JsonView(JsonViews.Image.class)
	public Image update(@RequestBody Image image, @PathVariable Long id) {
		Image imageEnBase = imageSrv.getById(id);
		imageSrv.update(imageEnBase);
		return imageEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    imageSrv.deleteById(id);
	}

}
