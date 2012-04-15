package manager.files.backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Date;
import java.util.Set;

import manager.files.FileID;
import manager.files.FileInfo;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;
import manager.tags.Tags;

/**
 * @author Karol Banys
 */
public class BackupOfFiles implements Backup {

	private final Tags tags;

	public BackupOfFiles(Tags tags) {
		this.tags = tags;
	}

	/**
	 * Funkcja kopiująca plik.
	 * 
	 * @param String
	 *            obrazujacy ścieżkę do pliku z którego chcemy skopiować.
	 * @param String
	 *            obrazujący scieżkę do pliku do którego chcemy skopiować.
	 */
	static private void copyFile(String from, String to) {
		FileInputStream inFrom;
		FileOutputStream outTo;
		try {
			inFrom = new FileInputStream(from);
		} catch (FileNotFoundException e) {
			System.out.println("Plik IN nie został znaleziony");
			return;
		}
		try {
			outTo = new FileOutputStream(to);
		} catch (FileNotFoundException e) {
			System.out.println("Plik OUT nie został znaleziony");
			return;
		}
		FileChannel fromFC = inFrom.getChannel(), toFC = outTo.getChannel();
		ByteBuffer b = ByteBuffer.allocateDirect(1024);
		try {
			while (fromFC.read(b) != -1) {
				b.flip();
				toFC.write(b);
				b.clear();
			}
		} catch (IOException e) {
			System.out.println("I/O problems with read / write");
		}
		try {
			inFrom.close();
			outTo.close();
		} catch (IOException e) {
			System.out.println("I/O problems with clossing");
		}
	}

	/**
	 * Funkcja kopiująca katalog razem z jego zawartością.
	 * 
	 * @param File
	 *            folder z którego będziemy chcieli kopiować.
	 * @param File
	 *            folder do którego będziemy chcieli kopiować.
	 */
	static private void copyDirectory(File from, File to) {
		if (from.isDirectory()) {
			if (!to.exists())
				to.mkdir();
			String filesOrDirectories[] = from.list();
			for (int i = 0; i < filesOrDirectories.length; i++)
				copyDirectory(new File(from, filesOrDirectories[i]), new File(
						to, filesOrDirectories[i]));
		} else {
			if (!to.exists()) {
				try {
					to.createNewFile();
				} catch (IOException e) {
					System.out.println("Nie udało się utowrzyć pliku");
				}
			}
			copyFile(from.getAbsolutePath(), to.getAbsolutePath());
		}
	}

	/**
	 * Tworzy nowy Backup.
	 * 
	 * @param MasterTag
	 *            master Tag którego backup chcemy zrobić.
	 * @param File
	 *            ścieżka gdzie chcemy go zrobić.
	 */
	@Override
	public void createNewBackup(MasterTag masterTag, File backupLocation) {
		Path pathFromMasterTag = tags.getPathFromMasterTag(masterTag);
		copyDirectory(pathFromMasterTag.toFile(), backupLocation);
		Set<FileID> setOfFileID = tags.getStore().getFilesFrom(masterTag);
		for (FileID f : setOfFileID) {
			try {
				FileInfo.addPath(f, backupLocation);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Synchronizuje backup.
	 * 
	 * @param MasterTag
	 *            master Tag którego backup chcemy zrobić.
	 * @param File
	 *            ścieżka gdzie chcemy go zrobić.
	 */
	@Override
	public void synchronizeBackup(MasterTag masterTag, File backupLocation)
			throws OperationInterruptedException {
		// Ostrzeżenie, że jeżeli zostanie przerwany to utracisz go.
		if (backupLocation.exists())
			backupLocation.delete();
		createNewBackup(masterTag, backupLocation);
		// TODO Auto-generated method stub
	}

	/**
	 * Zwraca odzyskany plik.
	 * 
	 * @param FileID
	 *            id pliku.
	 * @return Pilk.
	 */
	@Override
	public File retrieveFile(FileID file) throws OperationInterruptedException {
		Set<File> fileOfFileID = FileInfo.getPaths(file);
		for (File f : fileOfFileID) {
			if (f.exists())
				return f;
		}
		return null;
	}

	/**
	 * Zwraca date dla danego backup'u.
	 * 
	 * @param File
	 *            lokacja pliku.
	 * @return Date data.
	 */
	@Override
	public Date getLastBackupDate(File backupLocation)
			throws OperationInterruptedException {
		return new Date(backupLocation.lastModified());
	}

}
