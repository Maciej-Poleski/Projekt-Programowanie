package manager.tags;

import manager.files.FileID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;
import static org.powermock.api.easymock.PowerMock.*;

/**
 * User: Maciej Poleski
 * Date: 10.04.12
 * Time: 15:39
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Tags.class)
public class Tags1Test {
    private Tags tags;
    private static Tags firstInstance;
    private static boolean instantiated = false;

    @Before
    public void setUp() throws Exception {
        if (!instantiated) {
            tags = firstInstance = new Tags();
            instantiated = true;
        } else {
            tags = new Tags();
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testNewMasterTag1() throws Exception {
        tags.newMasterTag();
        verifyAll();
    }

    @Test
    public void testNewUserTag1() throws Exception {
        tags.newUserTag();
        verifyAll();
    }

    @Test
    public void testGetHeads() throws Exception {
        MasterTag masterReal = tags.newMasterTag();
        UserTag userReal = tags.newUserTag();
        assertEquals(tags.getHeads(), new HashSet<>(Arrays.asList(userReal, masterReal)));
        verifyAll();
    }

    @Test
    public void testGetMasterTagHeads() throws Exception {
        MasterTag masterReal = tags.newMasterTag();
        tags.newUserTag();
        assertEquals(tags.getMasterTagHeads(), new HashSet<>(Arrays.asList(masterReal)));
        verifyAll();
    }

    @Test
    public void testGetUserTagHeads() throws Exception {
        tags.newMasterTag();
        UserTag userReal = tags.newUserTag();
        assertEquals(tags.getUserTagHeads(), new HashSet<>(Arrays.asList(userReal)));
        verifyAll();
    }

    @Test
    public void testRemoveTag1() throws Exception {
        MasterTag masterReal = tags.newMasterTag();
        TagFilesStore store = createMock(TagFilesStore.class);
        expect(store.pretendRemoveFamily(masterReal)).andReturn(new HashSet<FileID>());
        replay(store);
        tags.setStore(store);
        tags.removeTag(masterReal);
        assertEquals(tags.getHeads(), new HashSet<>());
        verifyAll();
    }

    @Test
    public void testRemoveTag2() throws Exception {
        UserTag tag = tags.newUserTag();
        TagFilesStore store = createMock(TagFilesStore.class);
        expect(store.getFilesWithRealTag(tag)).andReturn(new HashSet<FileID>());
        replay(store);
        tags.setStore(store);
        tags.removeTag(tag);
        assertEquals(tags.getHeads(), new HashSet<>());
        verifyAll();
    }

    @Test
    public void testSetStore() throws Exception {
        TagFilesStore store = createMock(TagFilesStore.class);
        replay(store);
        tags.setStore(store);
        assertEquals(tags.getStore(), store);
    }

    @Test
    public void testGetStore() throws Exception {
        TagFilesStore store = createMock(TagFilesStore.class);
        replay(store);
        tags.setStore(store);
        assertEquals(tags.getStore(), store);
    }

    @Test
    public void testNewMasterTag2() throws Exception {
        MasterTag parent = tags.newMasterTag();
        MasterTag childA = tags.newMasterTag(parent);
        MasterTag childB = tags.newMasterTag(parent);
        assertEquals(parent.getParent(), null);
        assertEquals(parent.getChildren().size(), 2);
        assertTrue(parent.getChildren().contains(childA));
        assertTrue(parent.getChildren().contains(childB));
        assertEquals(childA.getParent(), parent);
        assertEquals(childB.getParent(), parent);
        assertEquals(parent.getDescendants().size(), 2);
        assertTrue(parent.getDescendants().contains(childA));
        assertTrue(parent.getDescendants().contains(childB));
        assertEquals(tags.getHeads(), new HashSet<>(Arrays.asList(parent)));
        assertEquals(tags.getMasterTagHeads(), new HashSet<>(Arrays.asList(parent)));
    }

    @Test
    public void testNewMasterTag3() throws Exception {
        MasterTag parent = tags.newMasterTag(null, "parent");
        MasterTag childA = tags.newMasterTag(parent, "childA");
        MasterTag childB = tags.newMasterTag(parent, "childB");
        assertEquals(parent.getParent(), null);
        assertEquals(parent.getChildren().size(), 2);
        assertTrue(parent.getChildren().contains(childA));
        assertTrue(parent.getChildren().contains(childB));
        assertEquals(childA.getParent(), parent);
        assertEquals(childB.getParent(), parent);
        assertEquals(parent.getDescendants().size(), 2);
        assertTrue(parent.getDescendants().contains(childA));
        assertTrue(parent.getDescendants().contains(childB));
        assertEquals(tags.getHeads(), new HashSet<>(Arrays.asList(parent)));
        assertEquals(tags.getMasterTagHeads(), new HashSet<>(Arrays.asList(parent)));
        assertEquals(parent.toString(), "parent");
        assertEquals(childA.toString(), "childA");
        assertEquals(childB.toString(), "childB");
    }

    @Test
    public void testNewUserTag2() throws Exception {
        UserTag parent1 = tags.newUserTag();
        UserTag parent2 = tags.newUserTag();
        UserTag child1 = tags.newUserTag();
        UserTag child2 = tags.newUserTag();
        UserTag tag = tags.newUserTag(new HashSet<>(Arrays.asList(parent1, parent2)),
                new HashSet<>(Arrays.asList(child1, child2)));
        assertEquals(tag.getParents().size(), 2);
        assertEquals(tag.getChildren().size(), 2);
        assertEquals(new HashSet<>(tag.getParents()), new HashSet<>(Arrays.asList(parent1, parent2)));
        assertEquals(new HashSet<>(tag.getChildren()), new HashSet<>(Arrays.asList(child1, child2)));
    }

    @Test
    public void testNewUserTag3() throws Exception {
        UserTag parent1 = tags.newUserTag("parent1");
        UserTag parent2 = tags.newUserTag(null, null, "parent2");
        UserTag child1 = tags.newUserTag("child1");
        UserTag child2 = tags.newUserTag(null, null, "child2");
        UserTag tag = tags.newUserTag(new HashSet<>(Arrays.asList(parent1, parent2)),
                new HashSet<>(Arrays.asList(child1, child2)), "tag");
        assertEquals(tag.getParents().size(), 2);
        assertEquals(tag.getChildren().size(), 2);
        assertEquals(new HashSet<>(tag.getParents()), new HashSet<>(Arrays.asList(parent1, parent2)));
        assertEquals(new HashSet<>(tag.getChildren()), new HashSet<>(Arrays.asList(child1, child2)));
        assertEquals(parent1.toString(), "parent1");
        assertEquals(parent2.toString(), "parent2");
        assertEquals(child1.toString(), "child1");
        assertEquals(child2.toString(), "child2");
        assertEquals(tag.toString(), "tag");
    }

    @Test
    public void testGetNameOfTag() throws Exception {
        MasterTag tag = tags.newMasterTag("tag");
        assertEquals(tags.getNameOfTag(tag), "tag");
    }

    @Test
    public void testSetNameOfTag() throws Exception {
        UserTag tag = tags.newUserTag();
        tags.setNameOfTag(tag, "tag");
        assertEquals(tag.toString(), "tag");
    }

    @Test
    public void testGetAllMetadataOfTag() throws Exception {
        UserTag tag = tags.newUserTag();
        tags.addTagMetadata(tag, "tag");
        tags.addTagMetadata(tag, new HashSet<>(Arrays.asList("a", "b")));
        assertEquals(tags.getAllMetadataOfTag(tag),
                new HashSet<>(Arrays.asList("tag", new HashSet<>(Arrays.asList("a", "b")))));
    }

    @Test
    public void testAddTagMetadata() throws Exception {
        UserTag tag = tags.newUserTag();
        tags.addTagMetadata(tag, "tag");
        tags.addTagMetadata(tag, new HashSet<>(Arrays.asList("a", "b")));
        assertEquals(tags.getAllMetadataOfTag(tag),
                new HashSet<>(Arrays.asList("tag", new HashSet<>(Arrays.asList("a", "b")))));
    }

    @Test
    public void testRemoveTagMetadata() throws Exception {
        UserTag tag = tags.newUserTag();
        tags.addTagMetadata(tag, "tag");
        tags.addTagMetadata(tag, new HashSet<>(Arrays.asList("a", "b")));
        assertEquals(tags.getAllMetadataOfTag(tag),
                new HashSet<>(Arrays.asList("tag", new HashSet<>(Arrays.asList("a", "b")))));
        tags.removeTagMetadata(tag, "tag");
        assertEquals(tags.getAllMetadataOfTag(tag),
                new HashSet<>(Arrays.asList(new HashSet<>(Arrays.asList("a", "b")))));
        tags.removeTagMetadata(tag, new HashSet<>(Arrays.asList("a", "b")));
        assertEquals(tags.getAllMetadataOfTag(tag), new HashSet<>());
    }

    @Test
    public void testAddChildToTag() throws Exception {
        MasterTag tag = tags.newMasterTag();
        MasterTag child1 = tags.newMasterTag();
        MasterTag child2 = tags.newMasterTag();
        tags.addChildToTag(child1, tag);
        tags.addChildToTag(child2, tag);
        assertEquals(new HashSet<>(tag.getChildren()), new HashSet<>(Arrays.asList(child1, child2)));
    }

    @Test
    public void testRemoveChildFromTag() throws Exception {
        MasterTag tag = tags.newMasterTag();
        MasterTag child1 = tags.newMasterTag();
        MasterTag child2 = tags.newMasterTag();
        tags.addChildToTag(child1, tag);
        tags.addChildToTag(child2, tag);
        assertEquals(new HashSet<>(tag.getChildren()), new HashSet<>(Arrays.asList(child1, child2)));
        tags.removeChildFromTag(child1, tag);
        assertEquals(new HashSet<>(tag.getChildren()), new HashSet<>(Arrays.asList(child2)));
        tags.removeChildFromTag(child2, tag);
        assertTrue(tag.getChildren().isEmpty());
    }

    @Test
    public void testSetParentOfTag() throws Exception {
        MasterTag parent = tags.newMasterTag();
        MasterTag child = tags.newMasterTag();
        tags.setParentOfTag(parent, child);
        assertEquals(child.getParent(), parent);
        MasterTag newParent = tags.newMasterTag();
        tags.setParentOfTag(newParent, child);
        assertEquals(child.getParent(), newParent);
        assertTrue(newParent.getChildren().contains(child));
        assertFalse(parent.getChildren().contains(child));
    }

    @Test
    public void testRemoveParentOfTag() throws Exception {
        UserTag child = tags.newUserTag();
        UserTag parent = tags.newUserTag(null, new HashSet<>(Arrays.asList(child)));
        assertTrue(child.getParents().contains(parent));
        assertTrue(parent.getChildren().contains(child));
        tags.removeParentOfTag(parent, child);
        assertFalse(child.getParents().contains(parent));
        assertFalse(parent.getChildren().contains(child));
    }

    @Test
    public void testAddParentOfTag() throws Exception {
        UserTag child = tags.newUserTag();
        UserTag parent = tags.newUserTag();
        tags.addParentOfTag(parent, child);
        assertTrue(child.getParents().contains(parent));
        assertTrue(parent.getChildren().contains(child));
    }

    @Test
    public void testGetPathFromMasterTag() throws Exception {
        MasterTag a = tags.newMasterTag("a");
        MasterTag b = tags.newMasterTag(a, "b");
        MasterTag c = tags.newMasterTag(b, "c");
        Path pathA = tags.getPathFromMasterTag(a);
        Path pathB = tags.getPathFromMasterTag(b);
        Path pathC = tags.getPathFromMasterTag(c);
        assertEquals(pathA.toString(), "a");
        assertEquals(pathB.toString(), "a" + File.separator + "b");
        assertEquals(pathC.toString(), "a" + File.separator + "b" + File.separator + "c");
    }

    @Test
    public void testGetOldestAncestor() throws Exception {
        MasterTag a = tags.newMasterTag("a");
        MasterTag b = tags.newMasterTag(a, "b");
        MasterTag c = tags.newMasterTag(b, "c");
        assertEquals(tags.getOldestAncestor(c), a);
        assertEquals(tags.getOldestAncestor(b), a);
        assertEquals(tags.getOldestAncestor(a), a);
    }

    @Test
    public void testGetDefaultInstance() throws Exception {
        assertEquals(Tags.getDefaultInstance(), firstInstance);
    }

    @Test
    public void testSetDefaultInstance() throws Exception {
        Tags myTags = new Tags();
        Tags.setDefaultInstance(myTags);
        assertEquals(Tags.getDefaultInstance(), myTags);
    }

    @Test
    public void testGetUserTagAutoProvider() throws Exception {
        assertNotNull(tags.getUserTagAutoProvider());
    }

    @Test
    public void testGetUserTagAutoExtensionManager() throws Exception {
        assertNotNull(tags.getUserTagAutoExtensionManager());
    }
}
