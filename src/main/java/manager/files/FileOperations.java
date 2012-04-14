package manager.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import manager.tags.MasterTag;
import manager.tags.Tags;

/**
 * Klasa udostepnia podstawowe operacje na plikach i katalogach, takie jak dodanie nowego pliku, 
 * stworzenie nowego katalogu itp.
 *
 * @author Jakub Cieśla
 */

public class FileOperations {
	
	private Tags tempTags = Tags.getDefaultInstance(); //na razie zdecydowaliśmy się z Maćkiem na takie rozwiązanie
	// być może z czasem będzie ono zmienione
	
	/**
	 * Funkcja slużąca do dodania nowego pliku do zarządzania przez aplikację.
	 * 
	 * @throws IllegalArgumentException Jeżeli masterTag == null.
	 * @param file Plik do dodania.
	 * @param masterTag Miejsce, do którego należy skopiować plik.
	 */
	public void addFile(File file, MasterTag masterTag) throws IllegalArgumentException{
		MasterTag tempSuperMasterTag = tempTags.getOldestAncestor(masterTag);
		//Path first = ...  // tutaj zostanie zwrócona ścieżka do tempSuperMasterTag z pliku, na którym będzie operował prawdopodobnie Marcin
		Path second = tempTags.getPathFromMasterTag(masterTag);
		File real = new File(first.toString()+File.separator+second.toString()+File.separator+file.getName());
		real.createNewFile(); //tutaj trzeba skomunikować się z GUI co jeśli taki plik już istnieje
		//skopiowac plik fukcją Karola
		//dodać plik do bazy ID (Marcin, dlaczego nie ma takiej fukcji?)
	}
	
	/**
	 * Funkcja tworzy nowy katalog (odpowiadający nowemu MasterTagowi).
	 * 
	 * @throws IllegalArgumentException Jeżeli masterTag == null.
	 * @param masterTag MasterTag (odpowiadający katalogowi), w którym ma zostać utworzony nowy katalog.
	 * @param name Nazwa nowego katalogu.
	 */
	public void addDirectory(MasterTag masterTag, String name) throws IllegalArgumentException{
		MasterTag tempSuperMasterTag = tempTags.getOldestAncestor(masterTag);
		//Path first = ...  // tutaj zostanie zwrócona ścieżka do tempSuperMasterTag z pliku, na którym będzie operował prawdopodobnie Marcin
		Path second = tempTags.getPathFromMasterTag(masterTag);
		File real = new File(first.toString()+File.separator+second.toString()+File.separator+name);
		real.mkdir(); //tutaj trzeba skomunikowac się z GUI co jeśli taki folder już istnieje
	}
	
	/**
	 * Funkcja tworzy nowy katalog będący nowym MasterTagiem, ale najbardziej zewnętrznym,
	 * można uworzyć go na dowolnym dysku w dowolnym miejscu.
	 * 
	 * @throws IllegalArgumentException Jeżeli file == null lub name == null.
	 * @param file Katalog, w którym ma zostać utworzony nowy katalog(MasterTag).
	 * @param name Nazwa katalogu(MasterTagu).
	 */
	public void addMasterDirectory(File file, String name) throws IllegalArgumentException{
		if(file == null || name == null) throw new IllegalArgumentException();
		File real = new File(file.getCanonicalPath()+File.separator+name);
		real.mkdir(); //tutaj trzeba skomunikowac się z GUI co jeśli taki folder już istnieje
		//dodać superMasterTag do pliku ze ścieżkami do nich (czekam na ten plik...)
	}
}
