
package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.RolDTO;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RolBOTest {

    private RolBO rolBO;
    private static Integer rolIdCreado;

    @BeforeEach
    public void setup() {
        rolBO = new RolBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        RolDTO rol = new RolDTO();
        rol.setNombre("test");
        rol.setEsCliente(true);
        RolDTO insertado = this.rolBO.insertar(rol);
        rolIdCreado = insertado.getIdRol();
        assertNotNull(rolIdCreado);
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        RolDTO rol = rolBO.obtenerPorId(rolIdCreado);
        assertNotNull(rol);
        assertEquals("test", rol.getNombre());
        assertTrue(rol.getEsCliente());
    }

    @Test
    @Order(3)
    public void testModificar() {
        RolDTO rol = rolBO.obtenerPorId(rolIdCreado);
        rol.setNombre("test_modificado");
        int rows = this.rolBO.modificar(rol);
        assertNotEquals(0, rows);
        RolDTO modificado = rolBO.obtenerPorId(rolIdCreado);
        assertEquals("test_modificado", modificado.getNombre());
    }

    @Test
    @Order(4)
    public void testListar() {
        List<RolDTO> roles = rolBO.listar();
        assertNotNull(roles);
        assertTrue(roles.stream().anyMatch(r -> r.getIdRol().equals(rolIdCreado)));
    }

    @Test
    @Order(5)
    public void testEliminar() {
        RolDTO rol = rolBO.obtenerPorId(rolIdCreado);
        Integer deleted = rolBO.eliminar(rol);
        assertNotEquals(0, deleted);

        RolDTO eliminado = rolBO.obtenerPorId(rolIdCreado);
        assertNull(eliminado, "El rol eliminado ya no debería existir");
    }
}