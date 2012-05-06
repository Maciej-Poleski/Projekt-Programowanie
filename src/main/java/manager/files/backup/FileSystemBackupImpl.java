package manager.files.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.util.Set;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;

/**
 * @author Karol Banys
 * @author Piotr Kolacz
 * 
 */
final class FileSystemBackupImpl extends SecondaryBackup {

	private static final long serialVersionUID = 1L;

	private Map<FileID, File> filesInBackup = new HashMap<FileID, File>();
	private final File location;

	private Date dateOfLastModification = new Date();

	/**
	 * Konsruktor dla backupu.
	 * 
	 * @param Primary
	 *            Backup Oryginalny backup.
	 * @param File
	 *            Katalog gdzie mamy skopiować.
	 * @throws OperationInterruptedException
	 */
	FileSystemBackupImpl(PrimaryBackup originalBackup, File to)
			throws OperationInterruptedException {

		super(originalBackup);
		location = new File(to.getAbsolutePath());
		if (!location.exists()) {
			if (!location.mkdir()) {
				throw new OperationInterruptedException(
						"Nie można utworzyć katalogu");
			}
		} else if (!location.isDirectory()) {
			throw new OperationInterruptedException(
					"Specified location should be a directory");
		}
	}

	/**
	 * @return location of that backup
	 */
	public File getLocation() {
		return location;
	}

	/**
	 * Zwraca dla danego ID plik który go zawiera.
	 * 
	 * @param FileID
	 *            Id pliku
	 * @return File Plik o danym ID.
	 * @throws FileNotAvailableException
	 *             Zostaje zwracany jeżeli plik nie istnieje.
	 */
	@Override
	public File getFile(FileID fileId) throws FileNotAvailableException {
		if (fileId == null)
			throw new FileNotAvailableException();

		File toReturn = filesInBackup.get(fileId);
		if (toReturn != null && toReturn.exists()) {
			return toReturn;
		}

		throw new FileNotAvailableException();

	}

	/**
	 * Zwraca liste ID wszystkich plików znajdujących się w backupie.
	 * 
	 * @return List<FileID>
	 */
	@Override
	public Set<FileID> getListOfAvailableFiles() {
		Set<FileID> setOfFileID = filesInBackup.keySet();
		return new HashSet<FileID>(setOfFileID);

	}

	/**
	 * Zwraca date ostaniej modyfikacji Backupu.
	 * 
	 * @return Date
	 */
	@Override
	public Date getLastBackupUpdateDate() {
		return dateOfLastModification;
	}

	/**
	 * Usuwa zawartość katalogu.
	 * 
	 * @param File
	 *            Katalog którego zawartość chcemy usunąć.
	 */
	private void deleteInner(File toDelete) {
		String[] files = toDelete.list();
		for (int i = 0; i < files.length; i++) {
			File f = new File(toDelete, files[i]);
			f.delete();
		}
	}

	/**
	 * Funkcja kopiująca plik.
	 * 
	 * @param File
	 *            obrazujacy ścieżkę do pliku z którego chcemy skopiować.
	 * @param File
	 *            obrazujący scieżkę do pliku do którego chcemy skopiować.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void copyFile(File from, File to) throws IOException {

		FileInputStream inFrom = new FileInputStream(from);
		FileOutputStream outTo = new FileOutputStream(to);
		FileChannel fromFC = inFrom.getChannel(), toFC = outTo.getChannel();
		ByteBuffer b = ByteBuffer.allocateDirect(2 << 10);
		while (fromFC.read(b) != -1) {
			b.flip();
			toFC.write(b);
			b.clear();
		}
		inFrom.close();
		outTo.close();
	}

	/**
	 * Updatuje backup do obecnej wersji.
	 * 
	 * @throws OperationInterruptedException
	 * @throws FileNotAvailableException
	 */
	@Override
	public void updateBackup() throws OperationInterruptedException {

		filesInBackup = new HashMap<FileID, File>();
		if (location.exists()) {
			deleteInner(location);
		} else {
			location.mkdir();
		}

		Set<FileID> listOfFileID = super.originalBackup
				.getListOfAvailableFiles();
		for (FileID fileId : listOfFileID) {
			try {
				File fromCopy = super.originalBackup.getFile(fileId);
				if (fromCopy.exists()) {
					String name = fromCopy.getName();
					File toCopy = new File(location, name);
					toCopy.createNewFile();
					copyFile(fromCopy, toCopy);
					filesInBackup.put(fileId, toCopy);
				}
			} catch (IOException | FileNotAvailableException e) {
				throw new OperationInterruptedException(
						"Nie wszystkie pliki zostały skopiowane");
			}
		}

	}

	@Override
	public Map<String, FileID> getListOfAdditionalFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Backup [location=");
		builder.append(location);
		builder.append("]");
		return builder.toString();
	}

	@Override
	protected void delete() throws OperationInterruptedException {
		if (location.exists()) {
			deleteInner(location);
		}
	}

}
