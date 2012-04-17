package manager.files;

public class FileNotAvailableException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FileNotAvailableException() {
		super();
	}

	public FileNotAvailableException(String msg) {
		super(msg);
	}

	public FileNotAvailableException(Throwable t) {
		super(t);
	}

	public FileNotAvailableException(String msg, Throwable t) {
		super(msg, t);
	}

}
