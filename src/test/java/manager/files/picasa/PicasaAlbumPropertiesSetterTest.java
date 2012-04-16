package manager.files.picasa;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;

import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.util.ServiceException;

import manager.files.picasa.PicasaAlbum.AlbumPropertiesSetter;

public class PicasaAlbumPropertiesSetterTest extends
		AbstractAlbumPropertiesSetterTestFrame {

	AlbumEntry albumEntry;

	@Override
	AbstractAlbumPropertiesSetter initialize() {
		PicasaAlbum album = mock(PicasaAlbum.class);
		albumEntry = mock(AlbumEntry.class);
		when(album.getAlbumEntry()).thenReturn(albumEntry);

		return new AlbumPropertiesSetter(album);
	}

	@Test
	public void executeUpdateTest() throws IOException, ServiceException,
			PicasaDataModificationException {
		ps.execute();

		verify(albumEntry).update();
	}

	@Test(expected = PicasaDataModificationException.class)
	public void executeUpdateWithIOException() throws IOException,
			ServiceException, PicasaDataModificationException {

		when(albumEntry.update()).thenThrow(
				new IOException());
		ps.execute();

	}

}
