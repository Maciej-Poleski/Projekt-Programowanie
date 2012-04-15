package manager.files.picasa;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.google.gdata.data.TextConstruct;

public abstract class AbstractAlbumPropertiesSetterTestFrame {

	AbstractAlbumPropertiesSetter ps;

	abstract AbstractAlbumPropertiesSetter initialize();

	@Before
	public void setUp() throws Exception {
		ps = initialize();
	}
 
	@Test
	public void settingAlbumDescription() {

		String description = "AbC";

		ps.setDescription(description);

		ArgumentCaptor<TextConstruct> argument = ArgumentCaptor
				.forClass(TextConstruct.class);

		verify(ps.albumEntry).setDescription(argument.capture());
		assertEquals(description, argument.getValue().getPlainText());
	}

	@Test
	public void settingAlbumTitle() {

		String title = "AbCdE";

		ps.setTitle(title);

		ArgumentCaptor<TextConstruct> argument = ArgumentCaptor
				.forClass(TextConstruct.class);

		verify(ps.albumEntry).setTitle(argument.capture());
		assertEquals(title, argument.getValue().getPlainText());
	}
 
	@Test
	public void settingAlbumDate() {

		Date date = new Date();

		ps.setDate(date);

		verify(ps.albumEntry).setDate(date);
	}
	
	@Test
	public void changingAlbumVisibilityToPublic(){
		
		ps.setPublic(true);
		verify(ps.albumEntry).setAccess("public");
	}
	
	@Test
	public void changingAlbumVisibilityToPrivate(){
		
		ps.setPublic(false);
		verify(ps.albumEntry).setAccess("private");
	}
	
	@Test
	public void settingAllPropertiesTogether(){
		// doesn't use title because of conflict with AlbumCreation usecase
		
		Date date = new Date();
		String description = "AbC";
		String title = "AbCdE";
		
		ps.setPublic(false).setDate(date).setDescription(description).setTitle(title);
		
		verify(ps.albumEntry).setAccess("private");
		verify(ps.albumEntry).setDate(date);
		
		ArgumentCaptor<TextConstruct> argument = ArgumentCaptor
				.forClass(TextConstruct.class);
		
		verify(ps.albumEntry).setDescription(argument.capture());
		assertEquals(description, argument.getValue().getPlainText());
	}
}
