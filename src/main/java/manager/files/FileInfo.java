package manager.files; /**
 * Klasa odpowiedzialna za przechowywanie struktury informacji na temat sciezek do plikow oraz 
 * za udostepnianie metod do modyfikacji tej struktury. 
 *
 * @author Marcin Zieminski
 */

import java.io.*;
import java.util.*;

class Info implements Serializable {
	Date created;
    HashMap<FileID, File> masters = new HashMap<FileID, File>();
	HashMap<FileID, Set<File>> infos = new HashMap<FileID, Set<File>>();
	Info() { created = new Date(); }
}

class History implements Serializable {
	Date created;
    HashMap<FileID, File> masters = new HashMap<FileID, File>();
    HashMap<FileID, Set<File>> history = new HashMap<FileID, Set<File>>();
	History() { created = new Date(); }
}

public class FileInfo {

	private static Info data;
	private static History guard;
	private static File input, hist;
	private static ObjectOutputStream wHistory;

	/**
	 * Funkcja wczytuje plik z informacjami na temat calej struktury 
	 * oraz plik z historia zmian podczas dzialania ostatniej instancji programu.
	 * W przypadku brutalnego zamkniecia programu dokonuje synchronizacji struktury z historia.
     *
     * @param sciezki do pliku z danymi i historia
     * @throws IOException
	 */
	public static void readInput(File input, File history) throws IOException {
		boolean sucD = false, sucH = false;
        FileInfo.input = input;
        FileInfo.hist = history;
        ObjectInputStream inData, inHistory;
		try{
			inData = new ObjectInputStream(new FileInputStream(input));
			try {
				data = (Info)inData.readObject();
				sucD = true;
			} catch(ClassNotFoundException e) {
				data = new Info();
			} catch(InvalidClassException e) {
                data = new Info();
            }
			inData.close();
        } catch (FileNotFoundException e) {
            data = new Info();
		}

		try{
            inHistory = new ObjectInputStream(new FileInputStream(hist));
			try {
				guard = (History)inHistory.readObject();
				sucH = true;
			} catch(ClassNotFoundException e) {
                guard = new History();
            } catch(InvalidClassException e) {
                guard = new History();
            }
			inHistory.close();
        } catch(FileNotFoundException e) {
            guard = new History();
		}

        if((!sucD && sucH) || (sucH && sucD && data.created.before(guard.created))) { // Synchronizacja struktury danych z historia operacji
            Set<File> set;
            File master;
			for(FileID file : guard.history.keySet()) {
                set = guard.history.get(file);
				if(set!=null) {
                    data.infos.put(file, set);
                }
                else {
                    data.infos.remove(file);
                }
                if(guard.masters.containsKey(file)) {
                    master = guard.masters.get(file);
                    if(master!=null) {
                        data.masters.put(file,master);
                    }
                    else {
                        data.masters.remove(file);
                    }
                }
			}
			saveChanges();
		}
		guard = new History();
		saveHistory();
	}

	/**
	 * Funkcja uaktualnia plik z informacjami na temat calej struktury
     *
     * @throws IOException
	 */
	public static void saveChanges() throws IOException {
		ObjectOutputStream wData = new ObjectOutputStream(new FileOutputStream(input));
        data.created = new Date();
		wData.writeObject(data);
		wData.close();
	}

	/**
	 * Funkcja zapisuje biezaca historie zmian
     *
     * @throws IOException
     */
	public static void saveHistory() throws IOException {
		wHistory = new ObjectOutputStream(new FileOutputStream(hist));
		guard.created = new Date();
        wHistory.writeObject(guard);
		wHistory.close();
	}

    /**
     * Sprawdzenie czy podany plik znajduje w oryginalnych katalogach
     *
     * @param ID danego pliku
     * @return boolean
     */
    public static boolean containsMaster(FileID file) {
        return data.masters.containsKey(file);
    }

    /**
     * Sprawdzenie czy podany plik znajduje sie w backupach
     *
     * @param ID danego pliku
     * @return boolean
     */
    public static boolean contains(FileID file) {
        return data.infos.containsKey(file);
    }

    /**
     * Dodanie oryginalnej sciezki do informacji na temat pliku
     *
     * @param ID danego pliku i sciezka do niego
     * @throws IOException
     */
    public static void addMasterPath(FileID file, File path) throws IOException {
        data.masters.put(file, path);
        guard.masters.put(file, path);
        saveHistory();
    }

    /**
     * Usuniecie oryginalnej z informacji na temat pliku
     *
     * @param ID danego pliku
     * @throws IOException
     */
    public static void removeMasterPath(FileID file) throws IOException {
        guard.masters.put(file, null);
        data.masters.remove(file);
        saveHistory();
    }

    /**
     * Pozyskanie sciezki do pliku w oryginalnym katalogu
     * Jesli podany plik nie istnieje funkcja zwraca null.
     *
     * @param ID pliku
     * @return sciezka do wskazanego pliku
     */
    public static File getMasterPath(FileID file) {
        return data.masters.get(file);
    }

	/**
	 * Dodanie sciezki do pliku w backupie
     *
     * @param ID danego pliku i sciezka do niego w backupie
     * @throws IOException
	 */
	public static void addPath(FileID file, File path) throws IOException {
		Set<File> set;
        if(data.infos.containsKey(file)) {
            set = data.infos.get(file);
		} else {
            set = new HashSet<File>();
        }
        set.add(path);
        data.infos.put(file, set);
        guard.history.put(file, set);
        saveHistory();
	}

	/**
	 * Usuniecie okreslonej sciezki ze zbioru informacji na temat pliku zwiazanej z backupem
     *
     * @param ID danego pliku i sciezka z backupu przeznaczona do usuniecia
     * @throws IOException
	 */
	public static void removePath(FileID file, File path) throws IOException {
		Set<File> set = data.infos.get(file);
		if(set != null) {
			set.remove(path);
            if(data.infos.get(file).size() == 0) {
                guard.history.put(file, null);
                data.infos.remove(file);
            }
            else {
                guard.history.put(file, set);
            }
			saveHistory();
		}
	}

	/**
	 * Pozyskanie wszystkich posiadanych sciezek do pliku w backupach,
	 * zwraca null, gdy zadna nie istnieje
     *
     * @param ID danego pliku
     * @return Zbior wszystkich sciezke do pliku w backupach
	 */
	public static Set<File> getPaths(FileID file) {
		return data.infos.get(file);
	}

	/**
	 * Pozyskanie wszystkich posiadanych sciezek do plikow w backupach okreslonych przez kolekcje
     *
     * @param Kolekcja ID plikow
     * @return Mapa w ktorej kluczami sa ID plikow a wartosciami zbioru sciezek z nimi zwiazanymi
     */
	public static HashMap<FileID,Set<File>> getAllPaths(Collection<FileID> files) {
		HashMap<FileID,Set<File>> map = new HashMap<FileID,Set<File>>();
		Iterator<FileID> iterator = files.iterator();
		FileID file;
		while(iterator.hasNext()) {
			file = iterator.next();
			if(data.infos.containsKey(file)) {
				map.put(file, data.infos.get(file));
			} 
		}
		return map;
	}

	/**
	 * Usuniecie wszelkich informacji na temat pewnego pliku
     *
     * @param ID pliku
     * @throws IOException
	 */
	public static void removeFileInfo(FileID file) throws IOException {
		data.infos.remove(file);
        data.masters.remove(file);
        guard.masters.put(file,null);
		guard.history.put(file,null);
		saveHistory();
	}

	/**
	 * Usuniecie wszelkich informacji na temat kolekcji plikow
     *
     * @param Kolekcja ID plikow
     * @throws IOException
	 */
	public static void removeFileInfo(Collection<FileID> files) throws IOException {
		Iterator<FileID> iterator = files.iterator();
		FileID file;
		while(iterator.hasNext()) {
			file = iterator.next();
            data.masters.remove(file);
            guard.masters.put(file, null);
            data.infos.remove(file);
			guard.history.put(file,null);
		}
		saveHistory();
	}

	public static File getFile(FileID fileID) {
		// FIXME (Marcin) potrzebna  metoda zwracająca ORGINALNA ścieżkę
		return null;
	}
}
