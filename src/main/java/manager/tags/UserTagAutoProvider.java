package manager.tags;

import java.util.Set;

/**
 * Ten interfejs umożliwia uzyskanie wskazówki jak powinien być otagowany plik o danej nazwie tagami użytkownika
 * podczas importowania. Użytkownik będzie miał możliwość konfigurowania preferencji tak, aby możliwe było uzyskanie
 * efektu polegającego np. na automatycznym otagowaniu plików z rozszerzeniem ".jpg" i ".jpeg" tagiem użytkownika
 * "jpeg". Ta funkcjonalność może okazać się bardzo poręczna dla użytkownika, który importując pliki od razu dostaje
 * kolekcję plików otagowanych zgodnie z jego preferencjami.
 *
 * @author Maciej Poleski
 */
public interface UserTagAutoProvider {
    /**
     * Zwraca zbiór tagów którymi powinien zostać otagowany plik o podanej nazwie.
     *
     * @param fileName Nazwa pliku
     * @return Zbiór tagów którymi powinien automatycznie zostać otagowany plik o wskazanej nazwie
     * @throws IllegalArgumentException Jeżeli fileName==null
     */
    Set<UserTag> getUserTagsForFileName(String fileName);
}
