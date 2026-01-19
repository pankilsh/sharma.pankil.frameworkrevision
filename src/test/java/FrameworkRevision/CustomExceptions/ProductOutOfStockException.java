package FrameworkRevision.CustomExceptions;

public class ProductOutOfStockException extends Exception {
	
	private String message = null;

	public ProductOutOfStockException() {
		super("Item X is out of stock");
	}

	public ProductOutOfStockException(String message) {
		super(message);
		this.message = message;
	}

	public ProductOutOfStockException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public ProductOutOfStockException(Throwable cause) {
		super(cause);
	}
	
	public String getMessage() {
		return this.message;
	}

}
