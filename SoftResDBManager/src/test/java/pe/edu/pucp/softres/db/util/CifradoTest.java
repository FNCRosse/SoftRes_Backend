
package pe.edu.pucp.softres.db.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author frank
 */
public class CifradoTest {
    
    public CifradoTest() {
    }

    /**
     * Test of descifrarMD5 method, of class Cifrado.
     */
    
     @Test
    public void testCifrarMD5() {
        System.out.println("cifrarMD5");
        String texto = "Chifa1212";
        String resultado = Cifrado.cifrarMD5(texto);
        String resultado_esperado = "IY+ZCZ81sdI5rjz/OrNSMg==";
        System.out.println(resultado);
        assertEquals(resultado, resultado_esperado);
    }

    @Test
    public void testDescifrarMD5() {
        System.out.println("descifrarMD5");
        String texto = "IY+ZCZ81sdI5rjz/OrNSMg==";
        String resultado = Cifrado.descifrarMD5(texto);
        String resultado_esperado = "Chifa1212";
        assertEquals(resultado, resultado_esperado);
    }
    @Test
    public void testDescifrar() {
        System.out.println("descifrarMD5");
        String texto = "IY+ZCZ81sdI5rjz/OrNSMg==";
        String resultado = Cifrado.descifrarMD5(texto);
        System.out.println(resultado);
    }
}
