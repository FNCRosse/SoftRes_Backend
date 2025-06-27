
package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.parametros.TipoMesaParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoMesaBOTest {

    private static TipoMesaBO tipoMesaBO;
    private static Integer idGenerado;

    @BeforeAll
    public static void setup() {
        tipoMesaBO = new TipoMesaBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        TipoMesaDTO tipo = new TipoMesaDTO();
        tipo.setNombre("Ventana");
        TipoMesaDTO insertado = tipoMesaBO.insertar(tipo);
        assertNotNull(insertado);
        idGenerado = insertado.getIdTipoMesa();
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        TipoMesaDTO tipoMesa = tipoMesaBO.obtenerPorId(idGenerado);
        assertNotNull(tipoMesa);
        assertEquals("Ventana", tipoMesa.getNombre());
    }

    @Test
    @Order(3)
    public void testListar() {
        List<TipoMesaDTO> lista = tipoMesaBO.listar();
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(t -> t.getIdTipoMesa().equals(idGenerado)));
    }

    @Test
    @Order(4)
    public void testModificar() {
        TipoMesaDTO tipoMesa = tipoMesaBO.obtenerPorId(idGenerado);
        tipoMesa.setNombre("2do Piso");
        int updated = tipoMesaBO.modificar(tipoMesa);
        assertTrue(updated > 0);

        TipoMesaDTO modificado = tipoMesaBO.obtenerPorId(idGenerado);
        assertEquals("2do Piso", modificado.getNombre());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        TipoMesaDTO tipoMesa = tipoMesaBO.obtenerPorId(idGenerado);
        int eliminado = tipoMesaBO.eliminar(tipoMesa);
        assertTrue(eliminado > 0);
    }
}
