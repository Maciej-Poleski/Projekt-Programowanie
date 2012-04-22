package manager.files.backup;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;
import manager.tags.Tags;

import org.junit.Test;

public class IntegrationTest {

	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns
	// false.
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return dir.delete();
	}

	@Test
	public void integrationExample() throws IOException,
			OperationInterruptedException, FileNotAvailableException {

		deleteDir(new File("src/test/resources/primaryBackup/"));
		deleteDir(new File("src/test/resources/secondaryBackup/"));
		new File("src/test/resources/primaryBackup/").mkdir();

		MasterTag mTag = mock(MasterTag.class);
		MasterTag mTagChild = mock(MasterTag.class);

		Tags tags = mock(Tags.class);
		when(tags.getOldestAncestor(mTagChild)).thenReturn(mTag);
		when(tags.getPathFromMasterTag(mTagChild)).thenReturn(
				Paths.get("rodzina/wakacje"));

		BackupsManager bManager = new BackupsManager(tags);

		PrimaryBackup primaryBackup = new PrimaryBackupImpl(
				"src/test/resources/primaryBackup/", tags);

		BackupManager firstBackupManager = new BackupManager(primaryBackup);
		bManager.registerBackupManager(mTag, firstBackupManager);

		// //////////////////////////// mocking ////////////////////////////

		// BackupsManager backupsManager = data.getBackupsManager()
		BackupsManager backupsManager = bManager;

		BackupManager manager = backupsManager
				.getBackupManagerAssociatedWithMasterTag(mTagChild);

		manager.getPrimaryBackup().addFile(mTagChild,
				new File("src/test/resources/GoogleMusic.jpg"));

		FileID fileId = manager.getPrimaryBackup().getListOfAvailableFiles()
				.iterator().next();

		assertEquals(
				new File(
						"src/test/resources/primaryBackup/rodzina/wakacje/GoogleMusic.jpg"),
				primaryBackup.getFile(fileId));

		manager.registerFileSystemBackup(new File(
				"src/test/resources/secondaryBackup/"));

		Set<SecondaryBackup> backups = manager.getSecondaryBackups();
		SecondaryBackup sb = backups.iterator().next();

		sb.updateBackup();

		assertEquals(new File(
				"src/test/resources/secondaryBackup/GoogleMusic.jpg").getAbsolutePath(),
				sb.getFile(fileId).getAbsolutePath());

	}
}
