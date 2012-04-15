package manager.tags;

import manager.files.FileID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.easymock.PowerMock.*;

/**
 * User: Maciej Poleski Date: 10.04.12 Time: 15:39
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Tags.class)
public class Tags1Test {
	Tags tags;

	@Before
	public void setUp() throws Exception {
		tags = new Tags();
	}

	@After
	public void tearDown() throws Exception {

	}

	private MasterTag newMasterTagInTags() throws Exception {
		MasterTag tagMock = createMockAndExpectNew(MasterTag.class);
		expect(tagMock.getParent()).andReturn(null).anyTimes();
		expect(tagMock.getParents()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		expect(tagMock.getChildren()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		expect(tagMock.getPredecessors()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		expect(tagMock.getDescendants()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		replay(tagMock, MasterTag.class);
		MasterTag tagReal = tags.newMasterTag();
		assertEquals(tagMock, tagReal);
		return tagReal;
	}

	private UserTag newUserTagInTags() throws Exception {
		UserTag tagMock = createMockAndExpectNew(UserTag.class);
		expect(tagMock.getParents()).andReturn(new ArrayList<UserTag>())
				.anyTimes();
		expect(tagMock.getChildren()).andReturn(new ArrayList<UserTag>())
				.anyTimes();
		expect(tagMock.getPredecessors()).andReturn(new ArrayList<UserTag>())
				.anyTimes();
		expect(tagMock.getDescendants()).andReturn(new ArrayList<UserTag>())
				.anyTimes();
		replay(tagMock, UserTag.class);
		UserTag tagReal = tags.newUserTag();
		assertEquals(tagMock, tagReal);
		return tagReal;
	}

	@Test
	public void testNewMasterTag1() throws Exception {
		MasterTag tagMock = createMockAndExpectNew(MasterTag.class);
		replay(tagMock, MasterTag.class);
		MasterTag tagReal = tags.newMasterTag();
		assertEquals(tagMock, tagReal);
		verifyAll();
	}

	@Test
	public void testNewUserTag1() throws Exception {
		UserTag tagMock = createMockAndExpectNew(UserTag.class);
		replay(tagMock, UserTag.class);
		UserTag tagReal = tags.newUserTag();
		assertEquals(tagMock, tagReal);
		verifyAll();
	}

	@Test
	public void testGetHeads() throws Exception {
		MasterTag masterMock = createMockAndExpectNew(MasterTag.class);
		expect(masterMock.getParents()).andReturn(new ArrayList<MasterTag>())
				.atLeastOnce();
		replay(masterMock, MasterTag.class);
		MasterTag masterReal = tags.newMasterTag();
		assertEquals(masterMock, masterReal);
		UserTag userMock = createMockAndExpectNew(UserTag.class);
		expect(userMock.getParents()).andReturn(new ArrayList<UserTag>())
				.atLeastOnce();
		replay(userMock, UserTag.class);
		UserTag userReal = tags.newUserTag();
		assertEquals(userMock, userReal);
		assertEquals(tags.getHeads(),
				new HashSet<>(Arrays.asList(userReal, masterReal)));
		verifyAll();
	}

	@Test
	public void testGetMasterTagHeads() throws Exception {
		MasterTag masterMock = createMockAndExpectNew(MasterTag.class);
		expect(masterMock.getParents()).andReturn(new ArrayList<MasterTag>())
				.atLeastOnce();
		replay(masterMock, MasterTag.class);
		MasterTag masterReal = tags.newMasterTag();
		assertEquals(masterMock, masterReal);
		UserTag userMock = createMockAndExpectNew(UserTag.class);
		expect(userMock.getParents()).andReturn(new ArrayList<UserTag>())
				.anyTimes();
		replay(userMock, UserTag.class);
		UserTag userReal = tags.newUserTag();
		assertEquals(userMock, userReal);
		assertEquals(tags.getMasterTagHeads(),
				new HashSet<>(Arrays.asList(masterReal)));
		verifyAll();
	}

	@Test
	public void testGetUserTagHeads() throws Exception {
		MasterTag masterMock = createMockAndExpectNew(MasterTag.class);
		expect(masterMock.getParents()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		replay(masterMock, MasterTag.class);
		MasterTag masterReal = tags.newMasterTag();
		assertEquals(masterMock, masterReal);
		UserTag userMock = createMockAndExpectNew(UserTag.class);
		expect(userMock.getParents()).andReturn(new ArrayList<UserTag>())
				.atLeastOnce();
		replay(userMock, UserTag.class);
		UserTag userReal = tags.newUserTag();
		assertEquals(userMock, userReal);
		assertEquals(tags.getUserTagHeads(),
				new HashSet<>(Arrays.asList(userReal)));
		verifyAll();
	}

	@Test
	public void testRemoveTag1() throws Exception {
		MasterTag masterMock = createMockAndExpectNew(MasterTag.class);
		expect(masterMock.getParent()).andReturn(null).anyTimes();
		expect(masterMock.getParents()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		expect(masterMock.getChildren()).andReturn(new ArrayList<MasterTag>())
				.anyTimes();
		replay(masterMock, MasterTag.class);
		MasterTag masterReal = tags.newMasterTag();
		TagFilesStore store = createMock(TagFilesStore.class);
		expect(store.removeFamily(masterReal)).andReturn(new HashSet<FileID>());
		replay(store);
		tags.setStore(store);
		tags.removeTag(masterReal);
		assertEquals(tags.getHeads(), new HashSet<>());
		verifyAll();
	}

	@Test
	public void testRemoveTag2() throws Exception {
		UserTag tag = newUserTagInTags();
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

	}

	@Test
	public void testNewMasterTag3() throws Exception {

	}

	@Test
	public void testNewUserTag2() throws Exception {

	}

	@Test
	public void testNewUserTag3() throws Exception {

	}

	@Test
	public void testGetNameOfTag() throws Exception {

	}

	@Test
	public void testSetNameOfTag() throws Exception {

	}

	@Test
	public void testGetAllMetadataOfTag() throws Exception {

	}

	@Test
	public void testAddTagMetadata() throws Exception {

	}

	@Test
	public void testRemoveTagMetadata() throws Exception {

	}

	@Test
	public void testAddChildToTag() throws Exception {

	}

	@Test
	public void testRemoveChildFromTag() throws Exception {

	}

	@Test
	public void testSetParentOfTag() throws Exception {

	}

	@Test
	public void testRemoveParentOfTag() throws Exception {

	}

	@Test
	public void testAddParentOfTag() throws Exception {

	}
}
