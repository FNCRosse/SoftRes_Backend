
package pe.edu.pucp.softres.business;

/**
 *
 * @author frank
 */
import java.time.LocalDateTime;
import java.util.Date;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.SedeDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocalBOTest {

    private LocalBO localBO;
    private static Integer localIdCreado;

    @BeforeEach
    public void setup() {
        localBO = new LocalBO();
    }
    
    @Test
    @Order(1)
    public void testInsertar() {
        SedeDTO sede = new SedeDTO();
        sede.setIdSede(2);
        LocalDTO local = new LocalDTO();
        local.setSede(sede);
        local.setNombre("Local prueba");
        local.setDireccion("Av.Prueba 123");
        local.setTelefono("99999999");
        local.setCantidadMesas(0);
        local.setEstado(true);
        local.setUsuarioCreacion("User_test");     
        local.setFechaCreacion(new Date());    
        LocalDTO insertado = this.localBO.insertar(local);
        assertNotNull(insertado.getIdLocal());
        assertTrue(insertado.getIdLocal() > 0);
        localIdCreado = insertado.getIdLocal();
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        LocalDTO localObtenido = localBO.obtenerPorId(localIdCreado);
        assertNotNull(localObtenido);
        assertEquals(localIdCreado, localObtenido.getIdLocal());
    }

    @Test
    @Order(4)
    public void testListar() {
        LocalParametros parametros = new LocalParametros();
        parametros.setEstado(true);
        List<LocalDTO> lista = localBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }

    @Test
    @Order(3)
    public void testModificar() {
        LocalDTO local = localBO.obtenerPorId(localIdCreado);
        local.setNombre("Local Modificado");
        local.setDireccion("Av. Modificada 101");
        local.setTelefono("7777777");
        local.setFechaModificacion(new Date());
        local.setUsuarioModificacion("Test_Eliminar");
        int resultado = localBO.modificar(local);
        assertNotEquals(0, resultado);

        LocalDTO modificado = localBO.obtenerPorId(localIdCreado);
        assertEquals("Local Modificado", modificado.getNombre());
        assertEquals("Av. Modificada 101", modificado.getDireccion());
        assertEquals("7777777", modificado.getTelefono());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        LocalDTO local = localBO.obtenerPorId(localIdCreado);
        local.setEstado(false);
        local.setUsuarioModificacion("Test_Eliminar");
        local.setFechaModificacion(new Date());
        Integer res = localBO.eliminar(local);
        assertNotEquals(0, res);

        LocalDTO eliminado = localBO.obtenerPorId(localIdCreado);
        assertFalse(eliminado.getEstado()); // Verificamos que fue eliminado lógicamente
    }
}

