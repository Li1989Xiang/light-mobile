package mobile.controller;

public class StopException extends RuntimeException {
	private static final long serialVersionUID = -6148648905166778571L;

	public StopException() {
		super();
	}

	public StopException(String message, Throwable cause) {
		super(message, cause);
	}

	public StopException(Throwable cause) {
		super(cause);
	}

}
