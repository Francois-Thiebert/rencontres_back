package rencontresAveugles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class MatchException extends RuntimeException{
	
public MatchException() {
		
	}
	
	public MatchException(String message) {
		super (message);
	}


}
