package manager.files.picasa;

/**
 * @author Piotr Kolacz
 *
 */
public class PicasaAuthenticationException extends Exception {

	private static final long serialVersionUID = -3408348332599032149L;

	public PicasaAuthenticationException() {
		super();
	}

	public PicasaAuthenticationException(String msg) {
		super(msg);
	}

	public PicasaAuthenticationException(Throwable t) {
		super(t);
	}

	public PicasaAuthenticationException(String msg, Throwable t) {
		super(msg, t);
	}

}
