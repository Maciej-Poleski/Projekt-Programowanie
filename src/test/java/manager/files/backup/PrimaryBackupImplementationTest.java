package manager.files.backup;

import java.io.File;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Set;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;
import manager.tags.Tags;

import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class PrimaryBackupImplementationTest {

	@Test
	@Ignore
	public void test() throws OperationInterruptedException,
			FileNotAvailableException, IOException {

		MasterTag mt = mock(MasterTag.class);
		Tags tags = mock(Tags.class);
		when(tags.getPathFromMasterTag(mt)).thenReturn(
				Paths.get("rodzina/wakacje"));

		String backupLoc = "src/test/java/manager/files/picasa/downloaded";

		PrimaryBackupImpl pbi = new PrimaryBackupImpl(
				backupLoc, tags);

		File file = new File("src/test/java/manager/files/picasa/dj2.jpg");
		pbi.addFile(mt, file, false);

		Set<FileID> files = pbi.getListOfAvailableFiles();

		for (FileID f : files) {
			System.out.println(pbi.getFile(f));
		}

	}

}
