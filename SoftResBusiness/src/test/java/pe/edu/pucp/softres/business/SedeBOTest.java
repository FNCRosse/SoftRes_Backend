
package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.SedeDTO;
import pe.edu.pucp.softres.parametros.SedeParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SedeBOTest {

    private SedeBO sedeBO;
    private static Integer sedeIdCreado;

    @BeforeEach
    public void setup() {
        sedeBO = new SedeBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        SedeDTO sede = new SedeDTO();
        sede.setNombre("Sede Test 2");
        sede.setDistrito("Distrito Test");
        sede.setFechaCreacion(new Date());
        sede.setUsuarioCreacion("test_insertar");
        sede.setEstado(true);
        SedeDTO insertada = sedeBO.insertar(sede);
        assertNotNull(insertada.getIdSede());
        sedeIdCreado = insertada.getIdSede();
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        SedeDTO recuperada = sedeBO.obtenerPorId(sedeIdCreado);
        assertNotNull(recuperada);
        assertEquals(sedeIdCreado, recuperada.getIdSede());
    }

    @Test
    @Order(4)
    public void testListar() {
        SedeParametros parametros = new SedeParametros();
        List<SedeDTO> lista = this.sedeBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(r -> r.getIdSede().equals(sedeIdCreado)));
    }

    @Test
    @Order(3)
    public void testModificar() {
        SedeDTO sede = sedeBO.obtenerPorId(sedeIdCreado);
        sede.setNombre("Sede Modificada");
        sede.setDistrito("Distrito Modificado");
        sede.setFechaModificacion(new Date());
        sede.setUsuarioModificacion("test_modificar");
        int res = sedeBO.modificar(sede);
        assertNotEquals(0, res);

        SedeDTO modificado = sedeBO.obtenerPorId(sedeIdCreado);
        assertEquals("Sede Modificada", modificado.getNombre());
        assertEquals("Distrito Modificado", modificado.getDistrito());
        assertEquals("test_modificar", modificado.getUsuarioModificacion());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        SedeDTO sede = sedeBO.obtenerPorId(sedeIdCreado);
        sede.setFechaModificacion(new Date());
        sede.setUsuarioModificacion("test_eliminar");
        sede.setEstado(false);
        Integer res = sedeBO.eliminar(sede);
        assertNotEquals(0, res);
        SedeDTO eliminado = sedeBO.obtenerPorId(sedeIdCreado);
        assertFalse(eliminado.getEstado()); // Verificamos que fue eliminado lógicamente
    }
}
