package manager.files.backup;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class FileSystemBackupImplementationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
    @Ignore
	public void test() throws OperationInterruptedException,
			FileNotAvailableException {

		PrimaryBackup original = mock(PrimaryBackup.class);

		Set<FileID> ids = new HashSet<>();
		FileID id = new FileID();
		ids.add(id);
		when(original.getListOfAvailableFiles()).thenReturn(ids);
		when(original.getFile(id)).thenReturn(
				new File("src/test/java/manager/files/picasa/dj2.jpg"));

		File backupLoc = new File(
				"src/test/java/manager/files/picasa/downloaded");

		FileSystemBackupImplementation fsb = new FileSystemBackupImplementation(
				original, backupLoc);

		fsb.updateBackup();

		System.out.println(fsb.getFile(id));
		System.out.println(fsb.getLastBackupUpdateDate());
	}

}
