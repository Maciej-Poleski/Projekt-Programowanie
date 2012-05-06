package manager.files.backup;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;
import manager.tags.Tags;

import org.junit.Test;
import static org.mockito.Mockito.*;

public class BackupsManagerTest {

	@Test
	public void getBackupManager() throws OperationInterruptedException {

		MasterTag tag1 = mock(MasterTag.class);
		MasterTag tag2 = mock(MasterTag.class);

		Tags tags = mock(Tags.class);
		when(tags.getOldestAncestor(tag2)).thenReturn(tag1);

		BackupsManager manager = new BackupsManager(tags);

		BackupManager backupManager = mock(BackupManager.class);

		manager.registerBackupManager(tag1, backupManager);

		assertEquals(backupManager,
				(manager.getBackupManagerAssociatedWithMasterTag(tag2)));

	}

	@Test
	public void getFile() throws OperationInterruptedException,
			FileNotAvailableException {

		MasterTag tag1 = mock(MasterTag.class);
		MasterTag tag2 = mock(MasterTag.class);

		Tags tags = mock(Tags.class);
		when(tags.getOldestAncestor(tag2)).thenReturn(tag1);

		BackupsManager manager = new BackupsManager(tags);

		FileID fileId = new FileID();
		File file = mock(File.class);

		PrimaryBackup primaryBackup = mock(PrimaryBackup.class);
		when(primaryBackup.getListOfAvailableFiles()).thenReturn(
				new HashSet<>(Arrays.asList(fileId)));
		when(primaryBackup.getFile(fileId)).thenReturn(file);

		BackupManager backupManager = mock(BackupManager.class);
		when(backupManager.getPrimaryBackup()).thenReturn(primaryBackup);

		manager.registerBackupManager(tag1, backupManager);

		assertEquals(file, manager.getFile(fileId));

	}
	
	@Test
	public void getAllBackupManagers() throws OperationInterruptedException{

		MasterTag tag1 = mock(MasterTag.class);
		MasterTag tag2 = mock(MasterTag.class);

		Tags tags = mock(Tags.class);

		BackupsManager manager = new BackupsManager(tags);

		BackupManager backupManager = mock(BackupManager.class);
		BackupManager backupManager2 = mock(BackupManager.class);
		
		manager.registerBackupManager(tag2, backupManager2);
		manager.registerBackupManager(tag1, backupManager);

		Map<MasterTag, BackupManager> result = manager.getAllBackupManagers();
		assertEquals(2, result.size());
		assertEquals(result.get(tag2), backupManager2);
		assertEquals(result.get(tag1), backupManager);
		
	}

}
