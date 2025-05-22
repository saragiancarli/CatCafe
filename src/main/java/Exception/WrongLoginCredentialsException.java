package Exception;

public class WrongLoginCredentialsException extends RuntimeException {
	  public WrongLoginCredentialsException(String message) {
	    super(message);
	  }
	}