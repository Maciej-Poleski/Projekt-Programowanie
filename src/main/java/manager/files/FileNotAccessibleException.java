package manager.files;

class FileNotAccessibleException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public FileNotAccessibleException() {
		super();
	}

	public FileNotAccessibleException(String msg) {
		super(msg);
	}

	public FileNotAccessibleException(Throwable t) {
		super(t);
	}

	public FileNotAccessibleException(String msg, Throwable t) {
		super(msg, t);
	}

}
