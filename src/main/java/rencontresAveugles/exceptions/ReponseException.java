package rencontresAveugles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ReponseException extends RuntimeException{
	
public ReponseException() {
		
	}
	
	public ReponseException(String message) {
		super (message);
	}

}
