package rencontresAveugles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ImageException extends RuntimeException{
	
public ImageException() {
		
	}
	
	public ImageException(String message) {
		super (message);
	}

}
