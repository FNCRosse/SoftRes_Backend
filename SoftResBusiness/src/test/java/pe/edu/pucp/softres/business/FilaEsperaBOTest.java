
package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilaEsperaBOTest {

    private FilaEsperaBO filaEsperaBO;
    private static Integer filaIdCreado;
    private final Integer userIdCreado = 3;
    
    @BeforeEach
    public void setup() {
        filaEsperaBO = new FilaEsperaBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        System.out.println("Insertar fila de espera");

        FilaEsperaDTO filaespera = new FilaEsperaDTO();
        UsuariosDTO user = new UsuariosDTO();
        user.setIdUsuario(userIdCreado);
        filaespera.setUsuario(user);
        filaespera.setEstado(EstadoFilaEspera.PENDIENTE);
        FilaEsperaDTO fila = filaEsperaBO.insertar(filaespera);
        filaIdCreado = fila.getIdFila();
        assertNotNull(fila);
        assertNotNull(fila.getIdFila());
        assertEquals(user.getIdUsuario(), fila.getUsuario().getIdUsuario());
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        System.out.println("Obtener fila de espera por ID");
        FilaEsperaDTO obtenida = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        assertNotNull(obtenida);
        assertEquals(filaIdCreado, obtenida.getIdFila());
        assertEquals(userIdCreado, obtenida.getUsuario().getIdUsuario());
    }

    @Test
    @Order(4)
    public void testListar() {
        System.out.println("Listar fila de espera");
        FilaEsperaParametros parametros = new FilaEsperaParametros();
        List<FilaEsperaDTO> filas = filaEsperaBO.listar(parametros);
        assertNotNull(filas);
        assertTrue(filas.size() >= 1);
    }

    @Test
    @Order(3)
    public void testModificar() {
        System.out.println("Modificar fila de espera");
        FilaEsperaDTO fila = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        fila.setEstado(EstadoFilaEspera.CONFIRMADA);
        int modificado = filaEsperaBO.modificar(fila);
        assertNotEquals(0, modificado);

        FilaEsperaDTO filaMod = filaEsperaBO.obtenerPorId(fila.getIdFila(), userIdCreado);
        assertEquals(EstadoFilaEspera.CONFIRMADA, filaMod.getEstado());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        System.out.println("Eliminar fila de espera");
        FilaEsperaDTO fila = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        //Se realiza Eliminar logico, entonces el estado de la fila se pasa a cancelado
        fila.setEstado(EstadoFilaEspera.CANCELADO);
        Integer eliminado = filaEsperaBO.eliminar(fila);
        assertNotEquals(0, eliminado);
        
        FilaEsperaDTO filaMod = filaEsperaBO.obtenerPorId(fila.getIdFila(), userIdCreado);
        assertEquals(EstadoFilaEspera.CANCELADO, filaMod.getEstado());
    }
}

