package manager.files;

public class FileSaveException extends Exception {

	private static final long serialVersionUID = 1L;

	public FileSaveException() {
		super();
	}

	public FileSaveException(String msg) {
		super(msg);
	}

	public FileSaveException(Throwable t) {
		super(t);
	}

	public FileSaveException(String msg, Throwable t) {
		super(msg, t);
	}

}
