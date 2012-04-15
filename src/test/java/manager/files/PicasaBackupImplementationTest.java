package manager.files;


import java.io.File;
import java.util.HashSet;
import java.util.Set;

import manager.files.backup.PicasaBackupImplementation;
import manager.files.backup.PrimaryBackup;

import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class PicasaBackupImplementationTest {

	@Test
	@Ignore
	public void testBackup() throws OperationInterruptedException, FileNotAvailableException {

		PrimaryBackup original = mock(PrimaryBackup.class);
		
		Set<FileID> ids = new HashSet<>();
		FileID id = new FileID();
		ids.add(id);
		when(original.getListOfAvailableFiles()).thenReturn(ids);
		when(original.getFile(id)).thenReturn(new File("src/test/java/manager/files/picasa/dj2.jpg"));

		PicasaBackupImplementation bi = new PicasaBackupImplementation(
				original, "projekt.programowanie", "projekt1234", "src/test/java/manager/files/picasa/downloaded");
		
		bi.updateBackup();
		
		System.out.println(bi.getLastBackupUpdateDate());
	}
	
	
	@Test
	public void testRetrieve() throws OperationInterruptedException, FileNotAvailableException {

		PrimaryBackup original = mock(PrimaryBackup.class);
		
		Set<FileID> ids = new HashSet<>();
		FileID id = new FileID();
		ids.add(id);
		when(original.getListOfAvailableFiles()).thenReturn(ids);
		when(original.getFile(id)).thenReturn(new File("src/test/java/manager/files/picasa/dj2.jpg"));

		PicasaBackupImplementation bi = new PicasaBackupImplementation(
				original, "projekt.programowanie", "projekt1234", "src/test/java/manager/files/picasa/downloaded");
		
		bi.updateBackup();

		System.out.println(bi.getLastBackupUpdateDate());
		System.out.println(bi.getFile(id));		
		
	}

	
}
