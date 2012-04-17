package manager.files.picasa;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.util.ServiceException;

import manager.files.picasa.PicasaService.AlbumCreator;
import static org.mockito.Mockito.*;

public class PicasaServiceAlbumCreatorTest extends
		AbstractAlbumPropertiesSetterTestFrame {

	private String title = "TestTitle";
	private PicasawebService picasawebService = mock(PicasawebService.class);
	private static URL feedUrl;
	private AlbumEntry entry = mock(AlbumEntry.class);
	
	@BeforeClass
	public static void beforeClassInitialization() throws MalformedURLException {
		feedUrl = new URL("http://google.com");
	}

	@Override
	AbstractAlbumPropertiesSetter initialize() {

		return new AlbumCreator(title, picasawebService, feedUrl, entry);
	}
	
	@Override
	public void settingAlbumTitle() {

		ArgumentCaptor<TextConstruct> argument = ArgumentCaptor
				.forClass(TextConstruct.class);

		verify(ps.albumEntry).setTitle(argument.capture());
		assertEquals(title, argument.getValue().getPlainText());
	}

	@Test
	public void executeMethodTest() throws IOException, ServiceException, PicasaDataModificationException{
		AlbumEntry returnEntry = mock(AlbumEntry.class);
		
		Link link = mock(Link.class);
		when(link.getHref()).thenReturn("http://google.com");
		when(returnEntry.getFeedLink()).thenReturn(link);
		
		TextConstruct tc = mock(TextConstruct.class);
		when(returnEntry.getTitle()).thenReturn(tc);
		
		when(picasawebService.insert(feedUrl, entry)).thenReturn(returnEntry);
		
		PicasaAlbum pa = ps.execute();
		
		assertEquals(returnEntry, pa.getAlbumEntry());
	}
	
}
