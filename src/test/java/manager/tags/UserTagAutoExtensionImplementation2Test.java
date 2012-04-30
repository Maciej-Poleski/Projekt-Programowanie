package manager.tags;

import org.junit.Before;
import org.junit.Test;

/**
 * User: Maciej Poleski
 * Date: 30.04.12
 * Time: 16:45
 */
public class UserTagAutoExtensionImplementation2Test {
    UserTagAutoExtensionImplementation implementation;
    Tags tags;
    UserTag tag1;
    UserTag tag2;
    UserTag tag3;

    @Before
    public void setUp() throws Exception {
        implementation = new UserTagAutoExtensionImplementation();
        tags = new Tags();
        tag1 = tags.newUserTag("tag1");
        tag2 = tags.newUserTag("tag2");
        tag3 = tags.newUserTag("tag3");
        implementation.registerUserTagAutoExtension(tag1, "png");
        implementation.registerUserTagAutoExtension(tag1, "jpg");
        implementation.registerUserTagAutoExtension(tag2, "png");
        implementation.registerUserTagAutoExtension(tag3, "pdf");
        implementation.unregisterUserTagAutoExtension(tag1, "png");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetUserTagsForFileNameErrorNoName() throws Exception {
        implementation.getUserTagsForFileName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserTagAutoExtensionErrorNoTag() throws Exception {
        implementation.registerUserTagAutoExtension(null, "bmp");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserTagAutoExtensionErrorNoExtension() throws Exception {
        implementation.registerUserTagAutoExtension(tag2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegisterUserTagAutoExtensionErrorNoNameNoExtension() throws Exception {
        implementation.registerUserTagAutoExtension(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnregisterUserTagAutoExtensionErrorNoTag() throws Exception {
        implementation.unregisterUserTagAutoExtension(null, "bmp");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnregisterUserTagAutoExtensionErrorNoExtension() throws Exception {
        implementation.unregisterUserTagAutoExtension(tag2, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnregisterUserTagAutoExtensionErrorNoNameNoExtension() throws Exception {
        implementation.unregisterUserTagAutoExtension(null, null);
    }
}
