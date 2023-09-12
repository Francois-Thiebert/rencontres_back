package rencontresAveugles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MessageException extends RuntimeException{
	
public MessageException() {
		
	}
	
	public MessageException(String message) {
		super (message);
	}

}
