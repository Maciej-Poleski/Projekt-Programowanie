package manager.tags;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User: Maciej Poleski
 * Date: 30.04.12
 * Time: 15:09
 */
class UserTagAutoExtensionImplementation implements UserTagAutoProvider, UserTagAutoExtensionsManager, Serializable {
    private final Map<UserTag, Set<String>> extensionsByTags = new HashMap<>();
    private final Map<String, Set<UserTag>> userTagsByExtensions = new HashMap<>();
    private static final long serialVersionUID = 1;

    @Override
    public Set<UserTag> getUserTagsForFileName(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException("Musisz podać nazwę pliku");
        }
        Set<UserTag> result = new HashSet<>();
        for (String extension : userTagsByExtensions.keySet()) {
            if (fileName.endsWith("." + extension)) {
                result.addAll(userTagsByExtensions.get(extension));
            }
        }
        return result;
    }

    @Override
    public void registerUserTagAutoExtension(UserTag tag, String extension) {
        if (tag == null || extension == null) {
            throw new IllegalArgumentException("Rejestrowanie null-i jest zabronione");
        }
        if (!extensionsByTags.containsKey(tag)) {
            extensionsByTags.put(tag, new HashSet<String>());
        }
        if (!userTagsByExtensions.containsKey(extension)) {
            userTagsByExtensions.put(extension, new HashSet<UserTag>());
        }
        extensionsByTags.get(tag).add(extension);
        userTagsByExtensions.get(extension).add(tag);
    }

    @Override
    public void unregisterUserTagAutoExtension(UserTag tag, String extension) {
        if (tag == null || extension == null) {
            throw new IllegalArgumentException("Wyrejestrowywanie null-i jest zabronione");
        }
        if (extensionsByTags.containsKey(tag) && extensionsByTags.get(tag).contains(extension)) {
            extensionsByTags.get(tag).remove(extension);
            userTagsByExtensions.get(extension).remove(tag);
            if (extensionsByTags.get(tag).isEmpty()) {
                extensionsByTags.remove(tag);
            }
            if (userTagsByExtensions.get(extension).isEmpty()) {
                userTagsByExtensions.remove(extension);
            }
        }
    }

    @Override
    public Map<UserTag, Set<String>> getUserTagToAutoExtensionsMapping() {
        return new HashMap<>(extensionsByTags);
    }
}
