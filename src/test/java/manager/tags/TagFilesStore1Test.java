package manager.tags;

import manager.files.FileID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.*;
import static org.powermock.api.easymock.PowerMock.verify;

/**
 * User: Maciej Poleski
 * Date: 08.04.12
 * Time: 20:15
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FileID.class)
public class TagFilesStore1Test {
    private TagFilesStore store;
    private MasterTag masterTag1;
    private MasterTag masterTag2;
    private MasterTag masterTag3;
    private MasterTag masterTag4;
    private MasterTag masterTag5;
    private UserTag userTag1;
    private UserTag userTag2;
    private UserTag userTag3;
    private UserTag userTag4;
    private UserTag userTag5;
    private UserTag userTag6;

    @Before
    public void setUp() throws Exception {
        store = new TagFilesStore();
        masterTag1 = setUpMasterTag();
        masterTag2 = setUpMasterTag();
        masterTag3 = setUpMasterTag();
        masterTag4 = setUpMasterTag();
        masterTag5 = setUpMasterTag();
        userTag1 = setUpUserTag();
        userTag2 = setUpUserTag();
        userTag3 = setUpUserTag();
        userTag4 = setUpUserTag();
        userTag5 = setUpUserTag();
        userTag6 = setUpUserTag();
    }

    private MasterTag setUpMasterTag() {
        MasterTag result = createMock(MasterTag.class);
        expect(result.getParent()).andReturn(null).anyTimes();
        expect(result.getParents()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        expect(result.getPredecessors()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        expect(result.getChildren()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        expect(result.getDescendants()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        replay(result);
        return result;
    }

    private UserTag setUpUserTag() {
        UserTag result = createMock(UserTag.class);
        expect(result.getParents()).andReturn(new ArrayList<UserTag>()).anyTimes();
        expect(result.getPredecessors()).andReturn(new ArrayList<UserTag>()).anyTimes();
        expect(result.getChildren()).andReturn(new ArrayList<UserTag>()).anyTimes();
        expect(result.getDescendants()).andReturn(new ArrayList<UserTag>()).anyTimes();
        replay(result);
        return result;
    }

    @Test
    public void testAddFile() throws Exception {
        FileID file1 = createMock(FileID.class);
        FileID file2 = createMock(FileID.class);
        FileID file3 = createMock(FileID.class);
        replay(file1, file2, file3);
        store.addFile(file1, masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag2, userTag1)));
        store.addFile(file2, masterTag2, new HashSet<>(Arrays.asList(userTag2, userTag4, userTag4)));
        store.addFile(file3, masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag4, userTag5)));
        assertEquals(store.getFilesWithRealTag(masterTag1), new HashSet<>(Arrays.asList(file1, file3)));
        assertEquals(store.getFilesWithRealTag(masterTag2), new HashSet<>(Arrays.asList(file2)));
        assertEquals(store.getFilesWith(masterTag1), new HashSet<>(Arrays.asList(file1, file3)));
        assertEquals(store.getFilesFrom(masterTag1), new HashSet<>(Arrays.asList(file1, file3)));
        assertEquals(store.getTagsFrom(file1), new HashSet<>(Arrays.asList(userTag1, userTag2, masterTag1)));
        assertEquals(store.getTagsFrom(file2), new HashSet<>(Arrays.asList(userTag2, userTag4, masterTag2)));
        assertEquals(store.getTagsFrom(file3), new HashSet<>(Arrays.asList(masterTag1, userTag1, userTag4, userTag5)));
        verify(file1, file2, file3);
    }

    @Test
    public void testRemoveFile() throws Exception {
        FileID file1 = createMock(FileID.class);
        FileID file2 = createMock(FileID.class);
        FileID file3 = createMock(FileID.class);
        replay(file1, file2, file3);
        store.addFile(file1, masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag2, userTag1)));
        store.addFile(file2, masterTag2, new HashSet<>(Arrays.asList(userTag2, userTag4, userTag4)));
        store.addFile(file3, masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag4, userTag5)));
        store.removeFile(file2);
        assertEquals(store.getFilesWithRealTag(masterTag1), new HashSet<>(Arrays.asList(file1, file3)));
        assertEquals(store.getFilesWithRealTag(masterTag2), new HashSet<>());
        assertEquals(store.getFilesWith(masterTag1), new HashSet<>(Arrays.asList(file1, file3)));
        assertEquals(store.getFilesFrom(masterTag1), new HashSet<>(Arrays.asList(file1, file3)));
        assertEquals(store.getTagsFrom(file1), new HashSet<>(Arrays.asList(userTag1, userTag2, masterTag1)));
        assertEquals(store.getTagsFrom(file2), new HashSet<>());
        assertEquals(store.getTagsFrom(file3), new HashSet<>(Arrays.asList(masterTag1, userTag1, userTag4, userTag5)));
        verify(file1, file2, file3);
    }

    @Test
    public void testGetFilesWithOneOf() throws Exception {
        FileID file1 = createMock(FileID.class);
        FileID file2 = createMock(FileID.class);
        FileID file3 = createMock(FileID.class);
        replay(file1, file2, file3);
        store.addFile(file1, masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag2)));
        store.addFile(file2, masterTag2, new HashSet<>(Arrays.asList(userTag2, userTag3)));
        store.addFile(file3, masterTag3, new HashSet<>(Arrays.asList(userTag3, userTag4)));
        assertEquals(store.getFilesWithOneOf(new HashSet<Tag<?>>(Arrays.asList(userTag1, userTag2))),
                new HashSet<>(Arrays.asList(file1, file2)));
        assertEquals(store.getFilesWithOneOf(new HashSet<Tag<?>>(Arrays.asList(userTag3, userTag2))),
                new HashSet<>(Arrays.asList(file1, file2, file3)));
        assertEquals(store.getFilesWithOneOf(new HashSet<Tag<?>>(Arrays.asList(userTag3, userTag4))),
                new HashSet<>(Arrays.asList(file3, file2)));
        assertEquals(store.getFilesWithOneOf(new HashSet<Tag<?>>(Arrays.asList(userTag1, userTag3))),
                new HashSet<>(Arrays.asList(file1, file2, file3)));
        assertEquals(store.getFilesWithOneOf(new HashSet<Tag<?>>(Arrays.asList(userTag1, userTag4))),
                new HashSet<>(Arrays.asList(file1, file3)));
        verify(file1, file2, file3);
    }

    @Test
    public void testGetFilesWithAllOf() throws Exception {
        FileID file1 = createMock(FileID.class);
        FileID file2 = createMock(FileID.class);
        FileID file3 = createMock(FileID.class);
        replay(file1, file2, file3);
        store.addFile(file1, masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag2)));
        store.addFile(file2, masterTag2, new HashSet<>(Arrays.asList(userTag2, userTag3)));
        store.addFile(file3, masterTag3, new HashSet<>(Arrays.asList(userTag3, userTag4)));
        assertEquals(store.getFilesWithAllOf(new HashSet<Tag<?>>(Arrays.asList(userTag1, userTag2))),
                new HashSet<>(Arrays.asList(file1)));
        assertEquals(store.getFilesWithAllOf(new HashSet<Tag<?>>(Arrays.asList(userTag3, userTag2))),
                new HashSet<>(Arrays.asList(file2)));
        assertEquals(store.getFilesWithAllOf(new HashSet<Tag<?>>(Arrays.asList(userTag3, userTag4))),
                new HashSet<>(Arrays.asList(file3)));
        assertEquals(store.getFilesWithAllOf(new HashSet<Tag<?>>(Arrays.asList(userTag1, userTag3))),
                new HashSet<>());
        assertEquals(store.getFilesWithAllOf(new HashSet<Tag<?>>(Arrays.asList(userTag2))),
                new HashSet<>(Arrays.asList(file1, file2)));
        verify(file1, file2, file3);
    }

    @Test
    public void testGetFilesWith() throws Exception {

    }

    @Test
    public void testGetFilesFrom() throws Exception {

    }

    @Test
    public void testAddFilesTo() throws Exception {

    }

    @Test
    public void testAddFiles() throws Exception {

    }

    @Test
    public void testGetTagsFrom() throws Exception {

    }

    @Test
    public void testRemoveFileTag() throws Exception {

    }

    @Test
    public void testRemoveFileTags() throws Exception {

    }

    @Test
    public void testRemoveFamily() throws Exception {

    }

    @Test
    public void testPretendRemoveFamily() throws Exception {

    }

    @Test
    public void testGetFilesWithRealTag() throws Exception {

    }
}
