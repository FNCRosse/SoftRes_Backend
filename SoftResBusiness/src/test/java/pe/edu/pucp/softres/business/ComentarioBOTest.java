
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
import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComentarioBOTest {

    private ComentarioBO comentarioBO;
    private static Integer comentarioIdCreado;
    
    @BeforeEach
    public void setUp() {
        this.comentarioBO = new ComentarioBO();
    }

    @Test
    @Order(1)
     public void testInsertar() {
        System.out.println("Insertar comentarios");

        ComentariosDTO comentario = new ComentariosDTO();
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(5);
        ReservaDTO reserva = new ReservaDTO();
        reserva.setIdReserva(3);
        comentario.setUsuario(usuario); // ID del usuario
        comentario.setReserva(reserva);  // ID de la reserva
        comentario.setMensaje("Elegante servicio");
        comentario.setPuntuacion(5);
        comentario.setEstado(true);
        comentario.setUsuarioCreacion("test_insertar");
        comentario.setFechaCreacion(new Date());
        ComentariosDTO insertado = comentarioBO.insertar(comentario);
        comentarioIdCreado = insertado.getIdComentario();
        assertNotNull(insertado.getIdComentario());
        assertTrue(insertado.getIdComentario() > 0);
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        System.out.println("Obtener comentario por ID");
        ComentariosDTO obtenido = comentarioBO.obtenerPorId(comentarioIdCreado);
        assertNotNull(obtenido);
        assertEquals(comentarioIdCreado, obtenido.getIdComentario());
        assertEquals("Elegante servicio", obtenido.getMensaje());
    }

    @Test
    @Order(4)
    public void testListar() {
        System.out.println("Listar comentarios");
        ComentarioParametros parametros = new ComentarioParametros();
        parametros.setEstado(true); // ejemplo, lista solo activos

        List<ComentariosDTO> lista = comentarioBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }

    @Test
    @Order(3)
    public void testModificar() {
        System.out.println("Modificar comentario");
        ComentariosDTO comentario = comentarioBO.obtenerPorId(comentarioIdCreado);
        // Modificar campos
        comentario.setMensaje("Modificado");
        comentario.setPuntuacion(4);
        comentario.setUsuarioModificacion("mod_test");
        comentario.setFechaModificacion(new Date());
        int resultado = comentarioBO.modificar(comentario);
        assertNotEquals(0, resultado);

        ComentariosDTO actualizado = comentarioBO.obtenerPorId(comentarioIdCreado);
        assertEquals("Modificado", actualizado.getMensaje());
        assertEquals(4, actualizado.getPuntuacion().intValue());
        assertEquals("mod_test", actualizado.getUsuarioModificacion());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        System.out.println("Eliminar comentario");
        ComentariosDTO comentario = comentarioBO.obtenerPorId(comentarioIdCreado);
        comentario.setEstado(false);
        comentario.setUsuarioModificacion("test_eliminar");
        comentario.setFechaModificacion(new Date());
        Integer resultado = comentarioBO.eliminar(comentario);
        assertNotEquals(0, resultado);

        ComentariosDTO eliminado = comentarioBO.obtenerPorId(comentarioIdCreado);
        assertFalse(eliminado.getEstado()); // Verificamos que fue eliminado lógicamente
    }
}
