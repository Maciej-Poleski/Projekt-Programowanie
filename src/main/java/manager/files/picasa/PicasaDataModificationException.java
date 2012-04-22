package manager.files.picasa;

/**
 * @author Piotr Kolacz
 *
 */
public class PicasaDataModificationException extends Exception {

	private static final long serialVersionUID = -3408988332599032149L;

	public PicasaDataModificationException() {
		super();
	}

	public PicasaDataModificationException(String msg) {
		super(msg);
	}

	public PicasaDataModificationException(Throwable t) {
		super(t);
	}

	public PicasaDataModificationException(String msg, Throwable t) {
		super(msg, t);
	}

}
