package rencontresAveugles.restcontrollers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import rencontresAveugles.entities.Image;
import rencontresAveugles.entities.User;
import rencontresAveugles.jsonviews.JsonViews;
import rencontresAveugles.services.ImageService;
import rencontresAveugles.services.UserService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/image")
public class ImageRestController {
	
	@Autowired
	private ImageService imageSrv;
	@Autowired
	private UserService userSrv;
	
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
	
	@GetMapping("/user/trie/{id}")
	@JsonView(JsonViews.Image.class)
	public List<Image> getByUserTrie(@PathVariable Long id){
		return imageSrv.getByUserTrie(id);
	}
	
	@GetMapping("/user/{userId}/numero/{imageNumber}")
	@JsonView(JsonViews.Image.class)
	public Image getImageByUserAndNumero(@PathVariable Long userId, @PathVariable int imageNumber){
		return imageSrv.getPhotoByUserAndNumero(userId, imageNumber);
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
		if (image.getUser() != null) {
			imageEnBase.setUser(image.getUser());
		}
		if (image.getImageByte() != null) {
			imageEnBase.setImageByte(image.getImageByte());
		}
		if (image.getType() != null) {
			imageEnBase.setType(image.getType());
		}
			imageEnBase.setNumero(image.getNumero());
		imageSrv.update(imageEnBase);
		return imageEnBase;
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
	    imageSrv.deleteById(id);
	}
	
	@DeleteMapping("/deleteList/{userId}/{imageId}")
    @JsonView(JsonViews.UsertWithAll.class)
    public ResponseEntity<Void> deleteFromList(@PathVariable Long userId, @PathVariable Long imageId) {
        try {
            User user = userSrv.getById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Image image = imageSrv.getById(imageId);
            if (image == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            imageSrv.deleteFromList(user, image);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
//	@PutMapping("deleteList/{UserId}/{ImageId}")
//	@JsonView(JsonViews.UsertWithAll.class)
//	public void deleteFromList(@PathVariable Long userId, @PathVariable Long imageId) {
//		User user = userSrv.getById(userId);
//		Image image = imageSrv.getById(imageId);
//	    imageSrv.deleteFromList(user, image);
//	}

}
