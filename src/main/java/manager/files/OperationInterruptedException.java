package manager.files;

public class OperationInterruptedException extends Exception {

	private static final long serialVersionUID = -3724332839293997992L;

	public OperationInterruptedException() {
		super();
	}

	public OperationInterruptedException(String msg) {
		super(msg);
	}

	public OperationInterruptedException(Throwable t) {
		super(t);
	}

	public OperationInterruptedException(String msg, Throwable t) {
		super(msg, t);
	}

}
