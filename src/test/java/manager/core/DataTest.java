package manager.core;

import manager.tags.MasterTag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static junit.framework.Assert.*;

/**
 * User: Maciej Poleski
 * Date: 21.04.12
 * Time: 13:50
 */
public class DataTest {
    @Test
    public void testSaveAndLoad() throws Exception {
        Data data;
        File database = new File("database");
        database.delete();
        Data.breakLock();
        data = Data.lockAndLoad();
        assertTrue(Data.isLocked());
        MasterTag masterTag = data.getTags().newMasterTag("masterTag");
        MasterTag child = data.getTags().newMasterTag(masterTag, "child");
        data.getTags().newUserTag("userTag");
        data.saveAndRelease();
        data = Data.lockAndLoad();
        assertEquals(data.getTags().getMasterTagHeads().size(), 1);
        assertEquals(data.getTags().getMasterTagHeads().toArray()[0].toString(), "masterTag");
        assertEquals(data.getTags().getUserTagHeads().size(), 1);
        assertEquals(data.getTags().getUserTagHeads().toArray()[0].toString(), "userTag");
        assertEquals(((MasterTag) data.getTags().getMasterTagHeads().toArray()[0]).getChildren().get(0).toString(), "child");
        data.saveAndRelease();
        database.delete();
    }
}
