
package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.EstadoNotificacion;
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.model.TipoNotificacion;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.NotificacionParametros;

/**
 *
 * @author frank
 */


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotificacionBOTest {

    private  NotificacionBO notificacionBO;
    private static Integer insertedId;
    private static Integer idUsuario = 3;

    @BeforeEach
    public void setUp() {
        this.notificacionBO = new NotificacionBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        NotificacionDTO notificacion = new NotificacionDTO();
        UsuariosDTO user = new UsuariosDTO();
        user.setIdUsuario(idUsuario);
        notificacion.setUsuario(user);
        notificacion.setMensaje("Notificacion de Insercion");
        notificacion.setTipoNotificacion(TipoNotificacion.RECORDATORIO);
        notificacion.setLeida(false);
        notificacion.setEstado(EstadoNotificacion.PENDIENTE);
        NotificacionDTO insertada = this.notificacionBO.insertar(notificacion);
        insertedId = insertada.getIdNotificacion();
        assertNotNull(insertedId);
    }

    @Test
    @Order(2)
    public void testModificar() {
        NotificacionDTO notificacion = this.notificacionBO.obtenerPorId(insertedId,idUsuario);
        notificacion.setMensaje("Notificacion modificada");
        int result = this.notificacionBO.modificar(notificacion);
        assertTrue(result >0);
        NotificacionDTO modificada = this.notificacionBO.obtenerPorId(insertedId,idUsuario);
        assertEquals("Notificacion modificada", modificada.getMensaje());
    }

    @Test
    @Order(3)
    public void testListar() {
        NotificacionParametros parametros = new NotificacionParametros();
        List<NotificacionDTO> lista = this.notificacionBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(t -> t.getIdNotificacion().equals(insertedId)));
    }

    @Test
    @Order(4)
    void testObtenerPorId() {
        NotificacionDTO notificacion = this.notificacionBO.obtenerPorId(insertedId,idUsuario);
        assertNotNull(notificacion);
        assertEquals(insertedId, notificacion.getIdNotificacion());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        NotificacionDTO notificacion = this.notificacionBO.obtenerPorId(insertedId,idUsuario);
        notificacion.setEstado(EstadoNotificacion.FALLIDO);
        Integer result = notificacionBO.eliminar(notificacion);
        assertNotNull(result);
        assertTrue(result > 0);
    }
}  
