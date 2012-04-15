package manager.files.old;

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
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;
import manager.tags.Tags;

/**
 * @author Karol Banys
 */
public class BackupOfFiles implements Backup_old {

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
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	static private void copyFile(String from, String to) throws FileNotFoundException, IOException {
		FileInputStream inFrom;
		FileOutputStream outTo;
		inFrom = new FileInputStream(from);
		outTo = new FileOutputStream(to);
		FileChannel fromFC = inFrom.getChannel(), toFC = outTo.getChannel();
		ByteBuffer b = ByteBuffer.allocateDirect(1024);
		while (fromFC.read(b) != -1) {
			b.flip();
			toFC.write(b);
			b.clear();
		}
		inFrom.close();
		outTo.close();
	}

	/**
	 * Funkcja kopiująca katalog razem z jego zawartością.
	 * 
	 * @param File
	 *            folder z którego będziemy chcieli kopiować.
	 * @param File
	 *            folder do którego będziemy chcieli kopiować.
	 * @throws IOException 
	 */
	static private void copyDirectory(File from, File to) throws IOException {
		if (from.isDirectory()) {
			if (!to.exists())
				to.mkdir();
			String filesOrDirectories[] = from.list();
			for (int i = 0; i < filesOrDirectories.length; i++)
				copyDirectory(new File(from, filesOrDirectories[i]), new File(
						to, filesOrDirectories[i]));
		} else {
			if (!to.exists()) {
				to.createNewFile();
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
	 * @throws OperationInterruptedException 
	 */
	@Override
	public void createNewBackup(MasterTag masterTag, File backupLocation) throws OperationInterruptedException {
		Path pathFromMasterTag = tags.getPathFromMasterTag(masterTag);
		try {
			copyDirectory(pathFromMasterTag.toFile(), backupLocation);
		} catch (IOException e1) {
			e1.printStackTrace();
			throw new OperationInterruptedException();
		}
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
	public void synchronizeBackup(MasterTag masterTag, File backupLocation) throws OperationInterruptedException {
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
	public Date getLastBackupDate(File backupLocation) {
		return new Date(backupLocation.lastModified());
	}

}
