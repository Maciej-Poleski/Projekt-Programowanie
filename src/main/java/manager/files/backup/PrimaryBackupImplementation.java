package manager.files.backup;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.files.backup.PrimaryBackup;
import manager.tags.MasterTag;
import manager.tags.Tags;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

class Info implements Serializable {

	private static final long serialVersionUID = 1L;

	Date created;
	HashMap<FileID, File> infos = new HashMap<FileID, File>();

	Info() {
		created = new Date();
	}
}

class History implements Serializable {

	private static final long serialVersionUID = 1L;

	Date created;

	HashMap<FileID, File> history = new HashMap<FileID, File>();

	History() {
		created = new Date();
	}
}

/**
 * @author Jakub Cieśla, Marcin Ziemiński
 * 
 */
public final class PrimaryBackupImplementation implements PrimaryBackup {

	private static final long serialVersionUID = 1L;

	private Info data;
	private History guard;
	private File input, hist;
	private ObjectOutputStream wHistory;

	private final Tags tempTags;
	private final String backupPath;

	public PrimaryBackupImplementation(String path, Tags tags, File info,
			File history) throws IOException {
		backupPath = path;
		tempTags = tags;
		readInput(info, history);
	}

	/**
	 * Funkcja wczytuje plik z informacjami na temat calej struktury oraz plik z
	 * historia zmian podczas dzialania ostatniej instancji programu. W
	 * przypadku brutalnego zamkniecia programu dokonuje synchronizacji
	 * struktury z historia.
	 * 
	 * @param sciezki
	 *            do pliku z danymi i historia
	 * @throws java.io.IOException
	 */
	public void readInput(File input, File history) throws IOException {
		boolean sucD = false, sucH = false;
		this.input = input;
		this.hist = history;

		ObjectInputStream inData, inHistory;
		try {
			inData = new ObjectInputStream(new FileInputStream(input));
			try {
				data = (Info) inData.readObject();
				sucD = true;
			} catch (ClassNotFoundException | InvalidClassException e) {
				data = new Info();
			}
			inData.close();

		} catch (FileNotFoundException e) {
			data = new Info();
		}

		try {
			inHistory = new ObjectInputStream(new FileInputStream(hist));
			try {
				guard = (History) inHistory.readObject();
				sucH = true;
			} catch (ClassNotFoundException e) {
				guard = new History();
			} catch (InvalidClassException e) {
				guard = new History();
			}
			inHistory.close();
		} catch (FileNotFoundException e) {
			guard = new History();
		}

		// Synchronizacja struktury danych z historia operacji
		if ((!sucD && sucH)
				|| (sucH && sucD && data.created.before(guard.created))) {

			File master;
			for (FileID file : guard.history.keySet()) {
				if (guard.history.containsKey(file)) {
					master = guard.history.get(file);
					if (master != null) {
						data.infos.put(file, master);
					} else {
						data.infos.remove(file);
					}
				}
			}
			saveChanges();
		}
		guard = new History();
		saveHistory();
	}

	/**
	 * Funkcja uaktualnia plik z informacjami na temat calej struktury
	 * 
	 * @throws java.io.IOException
	 */
	private void saveChanges() throws IOException {
		ObjectOutputStream wData = new ObjectOutputStream(new FileOutputStream(
				input));
		data.created = new Date();
		wData.writeObject(data);
		wData.close();
	}

	/**
	 * Funkcja zapisuje biezaca historie zmian
	 * 
	 * @throws java.io.IOException
	 */
	private void saveHistory() throws IOException {
		wHistory = new ObjectOutputStream(new FileOutputStream(hist));
		guard.created = new Date();
		wHistory.writeObject(guard);
		wHistory.close();
	}

	/**
	 * Dodanie oryginalnej sciezki do informacji na temat pliku
	 * 
	 * @param ID
	 *            danego pliku i sciezka do niego
	 * @throws java.io.IOException
	 */
	private void addPath(FileID file, File path) throws IOException {
		data.infos.put(file, path);
		guard.history.put(file, path);
		saveHistory();
	}

	/**
	 * Usuniecie oryginalnej z informacji na temat pliku
	 * 
	 * @param ID
	 *            danego pliku
	 * @throws java.io.IOException
	 */
	private void removePath(FileID file) throws IOException {
		guard.history.put(file, null);
		data.infos.remove(file);
		saveHistory();
	}

	@Override
	public File getFile(FileID file) throws FileNotAvailableException {
		File result = data.infos.get(file);

		if (result == null) {
			throw new FileNotAvailableException(file.toString());
		}

		return result;
	}

	@Override
	public Set<FileID> getListOfAvailableFiles() {
		return data.infos.keySet();
	}

	/**
	 * Funkcja służąca do dodania nowego pliku do danego PrimaryBackup.
	 * 
	 * @throws IOException
	 *             Nieudane skopiowanie pliku.
	 * @throws FileNotFoundException
	 *             Plik do skopiowania nie odnaleziony.
	 * @param tag
	 *            MasterTag utożsamiany z katalogiem, do którego ma zostać
	 *            dodany nowy plik.
	 * @param file
	 *            Plik to dodania.
	 */
	@Override
	public void addFile(MasterTag tag, File file)
			throws OperationInterruptedException, FileNotFoundException {

		if (!file.exists()) {
			throw new FileNotFoundException(file.getPath());
		}

		try {
			Path end = tempTags.getPathFromMasterTag(tag);
			File real = new File(backupPath + File.separator + end.toString());
			real.mkdirs();
			real = new File(real.toString() + File.separator + file.getName());
			addPath(new FileID(), real);

			// Otwiera kanał na pliku, który ma być kopiowany
			FileChannel srcChannel = new FileInputStream(file).getChannel();

			// Otwiera kanał dla pliku docelowego
			FileChannel dstChannel = new FileOutputStream(real).getChannel();

			// Kopiuje zawartość z jednego do drugiego
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			// Zamknięcie kanałów.
			srcChannel.close();
			dstChannel.close();

		} catch (IOException e) {
			throw new OperationInterruptedException(e);
		}
	}

	/**
	 * Funkcja służąca do usunięcia pliku z konkretnym ID z danego
	 * PrimaryBackup. Jeśli, dzięku działaniu tej fukcji, któryś katalog stał
	 * się pusty to ona automatycznie go usuwa.
	 * 
	 * @throws FileNotAvailableException
	 *             Jeśli wystąpił błąd związany z dostępem do pliku.
	 * @param fileID
	 *            FileID, które reprezentuje plik do usunięcia.
	 * @throws IOException
	 *             Przerzuca wyjątek z removePath.
	 */
	@Override
	public void removeFile(FileID fileId) throws FileNotAvailableException,
			OperationInterruptedException {

		try {
			File file = getFile(fileId);
			removePath(fileId);
			File temp = file;
			temp.delete();
			file = file.getParentFile();

			while (file != null) {
				temp = file;
				file = file.getParentFile();
				if (temp.listFiles().length == 0)
					break;
				temp.delete();
			}

		} catch (IOException e) {
			throw new OperationInterruptedException(e);
		}
	}

}
