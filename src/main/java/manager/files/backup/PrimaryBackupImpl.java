package manager.files.backup;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.files.backup.PrimaryBackup;
import manager.tags.MasterTag;
import manager.tags.Tags;
import manager.tags.TagFilesStore;
import manager.tags.UserTag;
import manager.tags.UserTagAutoProvider;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.*;

import javax.imageio.ImageIO;

/**
 * @author Jakub Cieśla
 * @author Marcin Ziemiński
 * @author Piotr Kolacz
 * 
 */
public final class PrimaryBackupImpl implements PrimaryBackup {

	private static final long serialVersionUID = 1L;

	private HashMap<FileID, File> infos = new HashMap<FileID, File>();

	private final Tags tempTags;
	private final TagFilesStore store;
	private final String backupPath;
	private Date created = new Date();

	/**
	 * @param path
	 * @param tags
	 * @throws IOException
	 */
	public PrimaryBackupImpl(String path, Tags tags) throws IOException {
		backupPath = path;
		tempTags = tags;
		store = tempTags.getStore();
	}

	String getBackupLocation() {
		return backupPath;
	}

	/**
	 * Funkcja zwraca datę utworzenia danego PrimaryBackup
	 */
	public Date getBackupDate() {
		return created;
	}

	/**
	 * Dodanie oryginalnej sciezki do informacji na temat pliku
	 * 
	 * @param fileid
	 *            ID danego pliku i scieżka do niego.
	 * @param path
	 *            Ścieżka do pliku
	 */
	private void addPath(FileID fileid, File path) {
		infos.put(fileid, path);
	}

	/**
	 * Usuniecie oryginalnej z informacji na temat pliku
	 * 
	 * @param fileid
	 *            ID danego pliku
	 */
	private void removePath(FileID fileid) throws IOException {
		infos.remove(fileid);
	}

	/**
	 * Funkcja zwraca ścieżkę do pliku z danego Primary Backup
	 * 
	 * @param fileid
	 *            ID danego pliku
	 * @throws FileNotAvailableException
	 *             Plik o danym ID nie znajduje się w bazie
	 */
	@Override
	public File getFile(FileID fileid) throws FileNotAvailableException {
		File result = infos.get(fileid);

		if (result == null) {
			throw new FileNotAvailableException(fileid.toString());
		}

		return result;
	}

	/**
	 * Funkcja zwraca zbiór wszystkich plików znajdujących się w danym
	 * PrimaryBackp
	 */
	@Override
	public Set<FileID> getListOfAvailableFiles() {
		return infos.keySet();
	}

	/**
	 * Funkcja służąca do dodania nowego pliku lub całego katalogu do danego
	 * PrimaryBackup.
	 * 
	 * @throws IOException
	 *             Nieudane skopiowanie pliku.
	 * @throws FileNotFoundException
	 *             Plik do skopiowania nie odnaleziony.
	 * @param tag
	 *            MasterTag utożsamiany z katalogiem, do którego ma zostać
	 *            dodany nowy plik.
	 * @param file
	 *            Plik lub katalog to dodania.
	 * @param fresh
	 *            Zmienna oznacza, że ma zostać dodany folder do MasterTaga
	 *            korzenia.
	 */
	@Override
	public void addFile(MasterTag tag, File file, boolean fresh)
			throws OperationInterruptedException, FileNotFoundException {

		if (!file.exists()) {
			throw new FileNotFoundException(file.getPath());
		}

		Path path = FileSystems.getDefault().getPath(file.getPath());
		if (Files.isSymbolicLink(path)){
			return;
		}
		
		if (file.isFile()) { // Kopiuje zwykły plik
			try {
				Path end = tempTags.getPathFromMasterTag(tag);
				File realParent = new File(backupPath + File.separator
						+ end.toString());
				if (!realParent.exists() && !realParent.mkdirs()) {
					throw new OperationInterruptedException(
							"Nie można utworzyć katalogu");
				}

				String newName = file.getName();
				File real = new File(realParent.toString() + File.separator
						+ newName);
				while (real.exists()) {
					newName = getGoodName(newName);
					real = new File(realParent.toString() + File.separator
							+ newName);
				}

				// Otwiera kanał na pliku, który ma być kopiowany
				FileChannel srcChannel = new FileInputStream(file).getChannel();

				// Otwiera kanał dla pliku docelowego
				FileChannel dstChannel = new FileOutputStream(real)
						.getChannel();

				// Kopiuje zawartość z jednego do drugiego
				dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

				FileID id = new FileID();
				addPath(id, real);

				UserTagAutoProvider userTagsProvider = tempTags
						.getUserTagAutoProvider();
				Set<UserTag> userTags = userTagsProvider
						.getUserTagsForFileName(file.getName());

				store.addFile(id, tag, userTags);

				// Zamknięcie kanałów.
				srcChannel.close();
				dstChannel.close();

			} catch (IOException e) {
				throw new OperationInterruptedException(e);
			}
		} else { // Kopiuje całą hierarchię folderów
			try {
				MasterTag root = null;
				File realParent = null;
				if (fresh) {
					root = tag;
					realParent = new File(backupPath + File.separator
							+ file.getName());

				} else {
					root = tempTags.newMasterTag(tag, file.getName());
					Path end = tempTags.getPathFromMasterTag(root);
					realParent = new File(backupPath + File.separator
							+ end.toString());
				}
				if (!realParent.exists() && !realParent.mkdirs()) {
					throw new OperationInterruptedException(
							"Nie można utworzyć katalogu");
				}

				if (!realParent.equals(file)) {
					copyFiles(file, realParent, root, false);
				} else {
					// only tag files
					copyFiles(file, realParent, root, true);
				}

			} catch (IOException e) {
				throw new OperationInterruptedException(e);
			}
		}
	}

	private void copyFiles(File source, File destination, MasterTag parent,
			boolean onlyTag) throws IOException, OperationInterruptedException {
		File[] filesAndDirs = source.listFiles();
		if (filesAndDirs == null) {
			return;
		}

		for (File file : filesAndDirs) {
			
			Path path = FileSystems.getDefault().getPath(file.getPath());
			if (Files.isSymbolicLink(path)){
				continue;
			}
			
			if (file.isDirectory()) {

				File dest = new File(destination.toString() + File.separator
						+ file.getName());
				MasterTag mt = tempTags.newMasterTag(parent, file.getName());
				if (!dest.exists() && !dest.mkdirs()) {
					throw new OperationInterruptedException(
							"Nie można utworzyć katalogu");
				}
				copyFiles(file, dest, mt, onlyTag);

			} else {

				String newName = file.getName();
				File real = new File(destination.toString() + File.separator
						+ newName);

				if (!onlyTag) {
					FileChannel srcChannel = new FileInputStream(file)
							.getChannel();

					while (real.exists()) {
						newName = getGoodName(newName);
						real = new File(destination.toString() + File.separator
								+ newName);
					}

					FileChannel dstChannel = new FileOutputStream(real)
							.getChannel();
					dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

					srcChannel.close();
					dstChannel.close();
				}

				FileID id = new FileID();
				addPath(id, real);

				UserTagAutoProvider userTagsProvider = tempTags
						.getUserTagAutoProvider();
				Set<UserTag> userTags = userTagsProvider
						.getUserTagsForFileName(file.getName());

				store.addFile(id, parent, userTags);

			}
		}
	}

	private String getGoodName(String name) {
		Matcher tmp;
		if (name.matches("^\\.?[^\\.]*\\([0-9]+\\)(\\..*||$)")) {
			String s;
			int num;

			if (name.matches("^\\.?[^\\.]*\\([0-9]+\\)\\..*")) {
				tmp = Pattern.compile("\\([0-9]+\\)\\.").matcher(name);
				tmp.find();
				s = tmp.group();
				tmp = Pattern.compile("[0-9]+").matcher(s);
				tmp.find();
				s = tmp.group();
				num = Integer.parseInt(s);
				num++;
				tmp = Pattern.compile("\\([0-9]+\\)\\.").matcher(name);
				name = tmp.replaceFirst("(" + num + ").");

			} else {
				tmp = Pattern.compile("\\([0-9]+\\)$").matcher(name);
				tmp.find();
				s = tmp.group();
				tmp = Pattern.compile("[0-9]+").matcher(s);
				tmp.find();
				s = tmp.group();
				num = Integer.parseInt(s);
				num++;
				tmp = Pattern.compile("\\([0-9]+\\)$").matcher(name);
				name = tmp.replaceFirst("(" + num + ")");
			}

		} else {

			if (name.matches("\\.?[^\\.]+\\..*")) {
				tmp = Pattern.compile("\\.?[^\\.]*").matcher(name);
				tmp.find();
				String s = tmp.group();
				name = name.replaceFirst("\\.?[^\\.]*\\.", s + "(1).");

			} else {
				name = name + "(1)";
			}
		}
		return name;
	}

	/**
	 * Funkcja służąca do usunięcia pliku z konkretnym ID z danego
	 * PrimaryBackup. Jeśli, dzięku działaniu tej fukcji, któryś katalog stał
	 * się pusty to ona automatycznie go usuwa.
	 * 
	 * @throws FileNotAvailableException
	 *             Błąd związany z dostępem do pliku.
	 * @param fileID
	 *            FileID, które reprezentuje plik do usunięcia.
	 * @throws OperationInterruptedException
	 *             Błąd wykonania operacji.
	 */
	@Override
	public void removeFile(FileID fileId) throws FileNotAvailableException,
			OperationInterruptedException {

		try {
			File file = getFile(fileId);
			removePath(fileId);

			File temp = file;

			if (!temp.delete()) {
				throw new OperationInterruptedException(
						"Nie można usunąć pliku");
			}

			file = file.getParentFile();

			while (file != null) {
				temp = file;
				file = file.getParentFile();
				if (temp.listFiles().length == 0) {
					break;
				}

				if (!temp.delete()) {
					throw new OperationInterruptedException(
							"Nie można usunąć pliku");
				}
			}
			removePath(fileId);
			store.removeFile(fileId);

		} catch (IOException e) {
			throw new OperationInterruptedException(e);
		}
	}

	/**
	 * Funkcja niesłychanie przydatna do pracy na plikach w edytorze. Pobiera z
	 * FileId uchyw do pliku (ImageHolder), z wczytanym już obrazem jako
	 * BufferedImage.
	 * 
	 * @param fileId
	 *            ID pliku, na którym chce się pracować w edytorze.
	 * @return ImageHolder, na którym można pracować w edytorze.
	 * 
	 * @throws FileNotAvailableException
	 *             Błąd związany z dostępem do pliku.
	 * @throws OperationInterruptedException
	 *             Błąd wykonania operacji.
	 */
	@Override
	public ImageHolder getImageToEdition(FileID fileId)
			throws FileNotAvailableException, OperationInterruptedException {
		try {
			File file = getFile(fileId);
			String name = file.getName();

			StringBuilder temp = new StringBuilder();

			// pobranie typu pliku
			for (int i = name.length() - 1; name.charAt(i) != '.'; i--) {
				temp.append(name.charAt(i));
			}
			temp.reverse();
			String type = temp.toString();

			// sprawdzenie czy plik nadaje sie do edycji

			BufferedImage im = ImageIO.read(file);

			return new ImageHolder(im, fileId, type);
		} catch (IOException e) {

			throw new OperationInterruptedException(e);
		}
	}

	/**
	 * Druga z fukcji konieczna do używania edytora. Zapisuje ona (w zasadzie
	 * usuwa stary i tworzy nowy) plik po edycji w edytorze.
	 * 
	 * @param image
	 *            Uchwyt (ImageHolder), na którym pracował edytor.
	 * @throws FileNotAvailableException
	 *             Błąd związany z dostępem do pliku.
	 * @throws OperationInterruptedException
	 *             Błąd wykonania operacji.
	 */
	@Override
	public void saveEditedImage(ImageHolder image)
			throws FileNotAvailableException, OperationInterruptedException {
		try {
			File file = getFile(image.getFileId());
			BufferedImage im = image.getBufferedImage();
			String type = image.getType();

			// usuwanie pliku z przed edycji
			if (!file.delete()) {
				throw new OperationInterruptedException(
						"Nie można usunać starej wersji pliku");
			}

			// dodanie zedytowanego pliku
			ImageIO.write(im, type, new File(file.getCanonicalPath()));

		} catch (IOException e) {
			throw new OperationInterruptedException(e);
		}
	}

	@Override
	public void addDeletedFile(FileID fileID, File file)
			throws OperationInterruptedException {

		try {
			File real = getFile(fileID);
			System.out.println(real.getPath());
			System.out.println(file.getPath());
			FileChannel srcChannel = new FileInputStream(file).getChannel();

			FileChannel dstChannel = new FileOutputStream(real).getChannel();

			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			srcChannel.close();
			dstChannel.close();

		} catch (FileNotAvailableException | IOException e) {
			throw new OperationInterruptedException(e);
		}

	}

}
