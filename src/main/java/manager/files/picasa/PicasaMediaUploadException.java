package manager.files.picasa;

public class PicasaMediaUploadException extends Exception {

	private static final long serialVersionUID = -3408348332599032149L;

	public PicasaMediaUploadException() {
		super();
	}

	public PicasaMediaUploadException(String msg) {
		super(msg);
	}

	public PicasaMediaUploadException(Throwable t) {
		super(t);
	}

	public PicasaMediaUploadException(String msg, Throwable t) {
		super(msg, t);
	}

}
