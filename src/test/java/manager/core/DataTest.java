package manager.core;

import manager.tags.MasterTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

import static junit.framework.Assert.*;

/**
 * User: Maciej Poleski
 * Date: 21.04.12
 * Time: 13:50
 */
public class DataTest {
    private Data data;

    @After
    public void tearDown() throws Exception {
        File database = new File("database");
        database.deleteOnExit();
    }

    @Before
    public void setUp() throws Exception {
        File database = new File("database");
        database.delete();
        data = Data.load();
        MasterTag masterTag = data.getTags().newMasterTag("masterTag");
        MasterTag child = data.getTags().newMasterTag(masterTag, "child");
        data.getTags().newUserTag("userTag");
    }

    @Test
    public void testSaveAndLoad() throws Exception {
        data.save();
        assertTrue(Data.isLoaded());
        Data.setLoaded(false);
        assertFalse(Data.isLoaded());
        Data data = Data.load();
        assertEquals(data.getTags().getMasterTagHeads().size(), 1);
        assertEquals(data.getTags().getMasterTagHeads().toArray()[0].toString(), "masterTag");
        assertEquals(data.getTags().getUserTagHeads().size(), 1);
        assertEquals(data.getTags().getUserTagHeads().toArray()[0].toString(), "userTag");
        assertEquals(((MasterTag) data.getTags().getMasterTagHeads().toArray()[0]).getChildren().get(0).toString(), "child");
    }
}
