package rencontresAveugles.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class QuestionException extends RuntimeException{
	
public QuestionException() {
		
	}
	
	public QuestionException(String message) {
		super (message);
	}

}
