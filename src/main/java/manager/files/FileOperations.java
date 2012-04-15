package manager.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import manager.tags.MasterTag;
import manager.tags.Tags;

/**
 * Klasa udostepnia podstawowe operacje na plikach i katalogach, takie jak
 * dodanie nowego pliku, stworzenie nowego katalogu itp.
 * 
 * @author Jakub Cieśla
 */
public class FileOperations {

	private final Tags tempTags;

	public FileOperations(Tags tags) {
		this.tempTags = tags;
	}

	/**
	 * Funkcja slużąca do dodania nowego pliku do zarządzania przez aplikację.
	 * 
	 * @throws IllegalArgumentException
	 *             Jeżeli masterTag == null.
	 * @throws FileSaveException
	 *             Błąd kopiowania pliku.
	 * @param file
	 *            Plik do dodania.
	 * @param masterTag
	 *            Miejsce, do którego należy skopiować plik.
	 */
	public void addFile(File file, MasterTag masterTag)
			throws IllegalArgumentException, FileSaveException {
		MasterTag tempSuperMasterTag = tempTags.getOldestAncestor(masterTag);

		 Path first; //= ... // tutaj zostanie zwrócona ścieżka do
		// tempSuperMasterTag z pliku, kto się zajmuję tym plikiem?
		 //FIXME o co w ogóle chodzi?
		 
		 
		Path second = tempTags.getPathFromMasterTag(masterTag);
		File real = new File(first.toString() + File.separator
				+ second.toString() + File.separator + file.getName());

		real.createNewFile(); // tutaj trzeba skomunikować się z GUI co jeśli
								// taki plik już istnieje

		try {
			// Otwiera kanał na pliku, który ma być kopiowany
			FileChannel srcChannel = new FileInputStream(file).getChannel();

			// Otwiera kanał dla pliku docelowego
			FileChannel dstChannel = new FileOutputStream(real).getChannel();

			// Kopiuje zawartość z jednego do drugiego
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			// Zamknięcie kanałów.
			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			throw new FileSaveException();
		}

		// dodać plik do bazy ID (pytanie jak utworzyc nowe id?)
	}

	/**
	 * Funkcja tworzy nowy katalog (odpowiadający nowemu MasterTagowi).
	 * 
	 * @throws IllegalArgumentException
	 *             Jeżeli masterTag == null.
	 * @param masterTag
	 *            MasterTag (odpowiadający katalogowi), w którym ma zostać
	 *            utworzony nowy katalog.
	 * @param name
	 *            Nazwa nowego katalogu.
	 */
	public void addDirectory(MasterTag masterTag, String name)
			throws IllegalArgumentException {
		MasterTag tempSuperMasterTag = tempTags.getOldestAncestor(masterTag);
		Path first; //FIXME= ... // tutaj zostanie zwrócona ścieżka do
		// tempSuperMasterTag z pliku, na którym będzie operował prawdopodobnie
		// Marcin
		Path second = tempTags.getPathFromMasterTag(masterTag);
		File real = new File(first.toString() + File.separator
				+ second.toString() + File.separator + name);
		real.mkdir(); // tutaj trzeba skomunikowac się z GUI co jeśli taki
						// folder już istnieje
	}

	/**
	 * Funkcja tworzy nowy katalog będący nowym MasterTagiem, ale najbardziej
	 * zewnętrznym, można utworzyć go na dowolnym dysku w dowolnym miejscu.
	 * 
	 * @throws IllegalArgumentException
	 *             Jeżeli file == null lub name == null.
	 * @param file
	 *            Katalog, w którym ma zostać utworzony nowy katalog(MasterTag).
	 * @param name
	 *            Nazwa katalogu(MasterTagu).
	 */
	public void addMasterDirectory(File file, String name)
			throws IllegalArgumentException {
		if (file == null || name == null)
			throw new IllegalArgumentException();
		File real;
		try {
			real = new File(file.getCanonicalPath() + File.separator + name);
			real.mkdir(); // tutaj trzeba skomunikowac się z GUI co jeśli taki
			// folder już istnieje
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// dodać superMasterTag do pliku ze ścieżkami do nich (czekam na ten
		// plik...)
	}

	/**
	 * Funkcja usuwa podany plik.
	 * 
	 * @throws IllegalArgumentException
	 *             Jeżeli fileID == null.
	 * @throws FileNotAccessibleException
	 *             Brak dostępu do pliku.
	 * @param file
	 *            Plik do usunięcia.
	 */
	public void deleteFile(FileID fileID) throws IllegalArgumentException,
			FileNotAccessibleException {
		if (fileID == null)
			throw new IllegalArgumentException();
		try {
			File file = FileInfo.getFile(fileID); // oczekiwanie na orginalną
													// ścieżkę
			FileInfo.removePath(fileID, file.getCanonicalFile());
			file.delete();
		} catch (Exception e) {
			throw new FileNotAccessibleException();
		}
	}

	/**
	 * Funkcja usuwa podany MasterTag(katalog wrac z zawartością).
	 * 
	 * @throws IllegalArgumentException
	 *             Jeżeli masterTag == null.
	 * @throws FileArgumentException
	 *             Brak dostępu do pliku.
	 * @param masterTag
	 *            MasterTag do usunięcia.
	 */
	public void deleteDirectory(MasterTag masterTag)
			throws IllegalArgumentException, FileNotAccessibleException {
		if (masterTag == null)
			throw new IllegalArgumentException();
		try {
			MasterTag tempSuperMasterTag = tempTags
					.getOldestAncestor(masterTag);
			 Path first; //FIXME= ... // tutaj zostanie zwrócona ścieżka do
			// tempSuperMasterTag z pliku, kto się zajmuje tym plikiem?
			Path second = tempTags.getPathFromMasterTag(masterTag);
			File real = new File(first.toString() + File.separator
					+ second.toString());
			if (masterTag.toString().compareTo(
					tempTags.getOldestAncestor(masterTag).toString())) {
				// usuniecie z pliku zewnętrznego MastetTagu, kto się zajmuje
				// tym plikiem???
			}
			// należy usunąć także fileID, ale do tego jest potrzebna fukcja
			// Maćka getFilesFrom z klasy TagFilesStore
			deleteAll(real);
		} catch (FileNotAccessibleException e) {
			throw new FileNotAccessibleException();
		}
	}

	/**
	 * Rekurencyjna fukcja do usuwania plików.
	 * 
	 * @throws IllegalArgumentException
	 *             Jeżeli file == null.
	 * @throws FileNotAccessibleException
	 *             Brak dostępu do pliku.
	 * @param file
	 *            Plik (katalog) do usunięcia.
	 */
	private void deleteAll(File file) throws IllegalArgumentException,
			FileNotAccessibleException {
		if (file == null)
			throw new IllegalArgumentException();
		try {
			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					deleteAll(f);
				}
				file.delete();
				// jak usuwać te FileID ????
			}
		} catch (Exception e) {
			throw new FileNotAccessibleException();
		}
	}
}
