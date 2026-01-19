package FrameworkRevision.CustomExceptions;

public class InsufficientFundsException extends Exception {
	
	private String message = null;

	public InsufficientFundsException() {
		super("Insufficient funds available");
	}

	public InsufficientFundsException(String message) {
		super(message);
		this.message = message;
	}

	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public InsufficientFundsException(Throwable cause) {
		super(cause);
	}
	
	public String getMessage() {
		return this.message;
	}

}
