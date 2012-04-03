package manager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Maciej Poleski
 * Date: 03.04.12
 * Time: 22:00
 */

public class HelloWorldTest {
    /**
     * Ta funkcja sprawdza, czy metoda toString() działa poprawnie.
     *
     * @throws Exception Jeżeli wykryto błąd
     */
    @Test
    public void testToString() throws Exception {
        assertEquals(new HelloWorld().toString(), "Hello World!");
    }
}
