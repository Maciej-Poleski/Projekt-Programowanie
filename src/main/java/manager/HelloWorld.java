package manager;

/**
 * User: Maciej Poleski
 * Date: 03.04.12
 * Time: 21:58
 */

/**
 * Główna (i jedyna) klasa przykładowej aplikacji.
 * Możliwe jest jej wypisanie.
 */
public class HelloWorld {
    /**
     * @return "Hello World!"
     */
    @Override
    public String toString() {
        return "Witaj świecie!";
    }

    public static void main(String[] args) {
        System.out.println(new HelloWorld());
    }
}
