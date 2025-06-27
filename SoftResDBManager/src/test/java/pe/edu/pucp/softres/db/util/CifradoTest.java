
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
        String texto = "bdfranky77";
        String resultado = Cifrado.cifrarMD5(texto);
        String resultado_esperado = "3J+Mv8WgCCNHU2xfbZGGog==";
        System.out.println(resultado);
        assertEquals(resultado, resultado_esperado);
    }

    @Test
    public void testDescifrarMD5() {
        System.out.println("descifrarMD5");
        String texto = "V+bsfFzALCHIJz4nAw/+Ww==";
        String resultado = Cifrado.descifrarMD5(texto);
        String resultado_esperado = "bryan123";
        assertEquals(resultado, resultado_esperado);
    }
}
