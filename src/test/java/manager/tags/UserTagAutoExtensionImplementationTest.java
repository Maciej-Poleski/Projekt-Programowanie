package manager.tags;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * User: Maciej Poleski
 * Date: 30.04.12
 * Time: 16:14
 */
public class UserTagAutoExtensionImplementationTest {
    UserTagAutoExtensionImplementation implementation;
    Tags tags;

    @Before
    public void setUp() throws Exception {
        implementation = new UserTagAutoExtensionImplementation();
        tags = new Tags();
    }

    @Test
    public void testGetUserTagsForFileName() throws Exception {
        UserTag tag1 = tags.newUserTag("tag1");
        UserTag tag2 = tags.newUserTag("tag2");
        UserTag tag3 = tags.newUserTag("tag3");
        implementation.registerUserTagAutoExtension(tag1, "png");
        implementation.registerUserTagAutoExtension(tag1, "jpg");
        implementation.registerUserTagAutoExtension(tag2, "png");
        implementation.registerUserTagAutoExtension(tag3, "pdf");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>(Arrays.asList(tag3)));
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>(Arrays.asList(tag3)));
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
    }

    @Test
    public void testRegisterUserTagAutoExtension() throws Exception {
        UserTag tag1 = tags.newUserTag("tag1");
        UserTag tag2 = tags.newUserTag("tag2");
        UserTag tag3 = tags.newUserTag("tag3");
        implementation.registerUserTagAutoExtension(tag1, "png");
        implementation.registerUserTagAutoExtension(tag1, "jpg");
        implementation.registerUserTagAutoExtension(tag2, "png");
        implementation.registerUserTagAutoExtension(tag3, "pdf");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>(Arrays.asList(tag1, tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>(Arrays.asList(tag3)));
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>(Arrays.asList(tag3)));
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
    }

    @Test
    public void testUnregisterUserTagAutoExtension() throws Exception {
        UserTag tag1 = tags.newUserTag("tag1");
        UserTag tag2 = tags.newUserTag("tag2");
        UserTag tag3 = tags.newUserTag("tag3");
        implementation.registerUserTagAutoExtension(tag1, "png");
        implementation.registerUserTagAutoExtension(tag1, "jpg");
        implementation.registerUserTagAutoExtension(tag2, "png");
        implementation.registerUserTagAutoExtension(tag3, "pdf");
        implementation.unregisterUserTagAutoExtension(tag1, "png");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>(Arrays.asList(tag3)));
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>(Arrays.asList(tag3)));
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
        implementation.unregisterUserTagAutoExtension(tag3, "pdf");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
        implementation.unregisterUserTagAutoExtension(tag1, "png");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>(Arrays.asList(tag2)));
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
        implementation.unregisterUserTagAutoExtension(tag2, "png");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>(Arrays.asList(tag1)));
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
        implementation.unregisterUserTagAutoExtension(tag1, "jpg");
        assertEquals(implementation.getUserTagsForFileName("a.png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName(".png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("aasdf.png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.sd.png"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.jpg"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("sdfa.jpg"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.sdf.jpg"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.png.jpg"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("adfd.pdf"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("da.gif"), new HashSet<>());
        assertEquals(implementation.getUserTagsForFileName("a.txt"), new HashSet<>());
    }

    @Test
    public void testGetUserTagToAutoExtensionsMapping() throws Exception {
        UserTag tag1 = tags.newUserTag("tag1");
        UserTag tag2 = tags.newUserTag("tag2");
        UserTag tag3 = tags.newUserTag("tag3");
        UserTag tag4 = tags.newUserTag("tag4");
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag2));
        implementation.registerUserTagAutoExtension(tag1, "png");
        implementation.registerUserTagAutoExtension(tag1, "jpg");
        implementation.registerUserTagAutoExtension(tag2, "png");
        implementation.registerUserTagAutoExtension(tag3, "pdf");
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag1).size(), 2);
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag2).size(), 1);
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag3).size(), 1);
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag4));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag1), new HashSet<>(Arrays.asList("png", "jpg")));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag2), new HashSet<>(Arrays.asList("png")));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag3), new HashSet<>(Arrays.asList("pdf")));
        implementation.unregisterUserTagAutoExtension(tag1, "png");
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag1), new HashSet<>(Arrays.asList("jpg")));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag2), new HashSet<>(Arrays.asList("png")));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag3), new HashSet<>(Arrays.asList("pdf")));
        implementation.unregisterUserTagAutoExtension(tag3, "pdf");
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag1), new HashSet<>(Arrays.asList("jpg")));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag2), new HashSet<>(Arrays.asList("png")));
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag3));
        implementation.unregisterUserTagAutoExtension(tag1, "png");
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag1), new HashSet<>(Arrays.asList("jpg")));
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag2), new HashSet<>(Arrays.asList("png")));
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag3));
        implementation.unregisterUserTagAutoExtension(tag2, "png");
        assertEquals(implementation.getUserTagToAutoExtensionsMapping().get(tag1), new HashSet<>(Arrays.asList("jpg")));
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag2));
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag3));
        implementation.unregisterUserTagAutoExtension(tag1, "jpg");
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag1));
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag2));
        assertFalse(implementation.getUserTagToAutoExtensionsMapping().containsKey(tag3));

    }
}
