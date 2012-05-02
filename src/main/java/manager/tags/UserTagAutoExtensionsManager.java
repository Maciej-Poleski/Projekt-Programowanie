package manager.tags;

import java.util.Map;
import java.util.Set;

/**
 * Ten interfejs umożliwia konfigurację wskazówek dotyczących automatycznego tagowania plików podczas importu.
 * Użytkownik ma możliwość konfigurowania preferencji tak, aby uzyskać efekt polegający np. na automatycznym otagowaniu
 * plików z rozszerzeniem ".jpg" i ".jpeg" tagiem użytkownika "jpeg".
 * Ta funkcjonalność może okazać się bardzo poręczna dla użytkownika, który importując pliki od razu dostaje
 * kolekcję plików otagowanych zgodnie z jego preferencjami.
 *
 * @author Maciej Poleski
 */
public interface UserTagAutoExtensionsManager {
    /**
     * Rejestruje podane rozszerzenie jako wskazówkę do tagowania wskazanym tagiem użytkownika.
     *
     * @param tag       Tag którym pliki będą tagowane po napotkaniu danego rozszerzenia.
     * @param extension Rozszerzenie pliku
     * @throws IllegalArgumentException Jeżeli tag==null lub extension==null
     */
    void registerUserTagAutoExtension(UserTag tag, String extension);

    /**
     * Wyrejestrowuje wskazane rozszerzenie jako wskazówkę do tagowania wskazanym tagiem użytkownika.
     *
     * @param tag       Tag którym automatycznie jest tagowany plik o podanym rozszerzeniu.
     * @param extension Rozszerzenie pliku
     * @throws IllegalArgumentException Jeżeli tag==null lub extension==null
     */
    void unregisterUserTagAutoExtension(UserTag tag, String extension);

    /**
     * Zwraca mapowanie które każdemu tagowi użytkownika przypisuje zbiór rozszerzeń plików automatycznie nim
     * tagowanych. Jeżeli dany tag użytkownika nie ma przypisanego żadnego rozszerzenia, to nie będzie go w tym
     * mapowaniu.
     *
     * @return Mapowanie z tagu użytkownika w zbiór rozszerzeń.
     */
    Map<UserTag, Set<String>> getUserTagToAutoExtensionsMapping();
}
