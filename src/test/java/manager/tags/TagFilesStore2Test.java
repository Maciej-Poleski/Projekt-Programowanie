package manager.tags;

import manager.files.FileID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.*;

/**
 * User: Maciej Poleski
 * Date: 30.04.12
 * Time: 17:23
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(FileID.class)
public class TagFilesStore2Test {
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
    FileID file1;
    FileID file2;
    FileID file3;

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
        file1 = createMock(FileID.class);
        file2 = createMock(FileID.class);
        file3 = createMock(FileID.class);
        replay(file1, file2, file3);
    }

    @After
    public void tearDown() throws Exception {
        verify(masterTag1, masterTag2, masterTag3, masterTag4, masterTag5);
        verify(userTag1, userTag2, userTag3, userTag4, userTag5, userTag6);
        verify(file1, file2, file3);
    }

    private MasterTag setUpMasterTag() {
        MasterTag result = PowerMock.createMock(MasterTag.class);
        expect(result.getParent()).andReturn(null).anyTimes();
        expect(result.getParents()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        expect(result.getPredecessors()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        expect(result.getChildren()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        expect(result.getDescendants()).andReturn(new ArrayList<MasterTag>()).anyTimes();
        replay(result);
        return result;
    }

    private UserTag setUpUserTag() {
        UserTag result = PowerMock.createMock(UserTag.class);
        expect(result.getParents()).andReturn(new ArrayList<UserTag>()).anyTimes();
        expect(result.getPredecessors()).andReturn(new ArrayList<UserTag>()).anyTimes();
        expect(result.getChildren()).andReturn(new ArrayList<UserTag>()).anyTimes();
        expect(result.getDescendants()).andReturn(new ArrayList<UserTag>()).anyTimes();
        replay(result);
        return result;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFileErrorNoFileId() throws Exception {
        store.addFile(null, masterTag1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFileErrorNoMasterTag() throws Exception {
        store.addFile(file1, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFileErrorNoFileIdNoMasterTag() throws Exception {
        store.addFile(null, null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddFileErrorDoubleAddFile() throws Exception {
        store.addFile(file1, masterTag1, null);
        store.addFile(file1, masterTag2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFileErrorNoFileId() throws Exception {
        store.removeFile(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFilesWithOneOfErrorNoTag() throws Exception {
        store.getFilesWithOneOf(new HashSet<>(Collections.<Tag<?>>singleton(null)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFilesWithAllOfErrorNoTag() throws Exception {
        store.getFilesWithAllOf(new HashSet<>(Collections.<Tag<?>>singleton(null)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFilesWithAllOfErrorNoTagSecond() throws Exception {
        store.getFilesWithAllOf(new HashSet<Tag<?>>(Arrays.asList(masterTag1, masterTag2, masterTag3, masterTag4, null)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFilesFromErrorNoMasterTag() throws Exception {
        store.getFilesFrom(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFilesErrorNoMasterTag() throws Exception {
        store.addFiles(new HashSet<>(Arrays.asList(file1, file2)), null);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddFilesErrorFileAlreadyInStore() throws Exception {
        store.addFiles(new HashSet<>(Collections.singleton(file1)), masterTag1);
        store.addFiles(new HashSet<>(Collections.singleton(file1)), masterTag2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFilesErrorNoFileId() throws Exception {
        store.addFiles(new HashSet<>(Collections.<FileID>singleton(null)), masterTag1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFilesErrorNoMasterTagSecond() throws Exception {
        store.addFiles(new HashSet<>(Arrays.asList(file1, file2)), null, new HashSet<>(Arrays.asList(userTag1, userTag2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFilesErrorNoUserTag() throws Exception {
        store.addFiles(new HashSet<>(Arrays.asList(file1, file2)), masterTag1, new HashSet<>(Arrays.asList(userTag1, null)));
    }

    @Test(expected = IllegalStateException.class)
    public void testAddFilesErrorFileAlreadyInStoreSecond() throws Exception {
        store.addFiles(new HashSet<>(Collections.singleton(file1)), masterTag1, new HashSet<>(Arrays.asList(userTag1, userTag2)));
        store.addFiles(new HashSet<>(Collections.singleton(file1)), masterTag2, new HashSet<>(Arrays.asList(userTag4)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserTagToFileErrorNoUserTag() throws Exception {
        store.addFile(file1, masterTag1);
        store.addUserTagToFile(null, file1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserTagToFileErrorNoFileId() throws Exception {
        store.addUserTagToFile(userTag1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddUserTagToFileErrorNoUserTagNoFileId() throws Exception {
        store.addUserTagToFile(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTagsFromErrorNoUserTag() throws Exception {
        store.getTagsFrom(new HashSet<>(Arrays.asList(file1, null, file2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetMasterTagFromErrorNoFileId() throws Exception {
        store.getMasterTagFrom(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testGetMasterTagFromErrorFileHasNoMasterTag() throws Exception {
        store.getMasterTagFrom(file1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFileTagErrorNoFileId() throws Exception {
        store.removeFileTag(null, userTag1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFileTagErrorNoUserTag() throws Exception {
        store.addFile(file1, masterTag1);
        store.removeFileTag(file1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFileTagErrorNoFileIdNoUserTag() throws Exception {
        store.removeFileTag(null, null);
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFileTagErrorFileHasNotUserTag() throws Exception {
        store.addFile(file1, masterTag1);
        store.removeFileTag(file1, userTag1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFileTagsErrorNoFileId() throws Exception {
        store.removeFileTags(null, new HashSet<>(Arrays.asList(userTag1, userTag2)));
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFileTagsErrorFileHasNotUserTag() throws Exception {
        store.addFile(file1, masterTag1);
        store.removeFileTags(file1, new HashSet<>(Collections.singleton(userTag1)));
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFileTagsErrorFileIsNotInStore() throws Exception {
        store.removeFileTags(file1, new HashSet<>(Collections.singleton(userTag1)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFamilyErrorNoMasterTag() throws Exception {
        store.removeFamily(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFilesWithRealTagErrorNoTag() throws Exception {
        store.getFilesWithRealTag(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveFileErrorFileIsNotInStore() throws Exception {
        store.removeFile(file1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFileTagsErrorNoUserTag() throws Exception {
        store.addFile(file1, masterTag1);
        store.removeFileTags(file1, new HashSet<>(Collections.<UserTag>singleton(null)));
    }
}
