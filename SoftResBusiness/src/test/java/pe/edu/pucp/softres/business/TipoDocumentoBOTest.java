
package pe.edu.pucp.softres.business;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipoDocumentoBOTest {

    private  TipoDocumentoBO tipoDocumentoBO;
    private static Integer idInsertado;

    @BeforeEach
    public void setup() {
        tipoDocumentoBO = new TipoDocumentoBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        TipoDocumentoDTO tipodoc = new TipoDocumentoDTO();
        tipodoc.setNombre("test_insertar");
        TipoDocumentoDTO insertado = this.tipoDocumentoBO.insertar(tipodoc);
        idInsertado = insertado.getIdTipoDocumento();
        assertNotNull(idInsertado);
        assertTrue(idInsertado > 0);
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        TipoDocumentoDTO tipoDoc = tipoDocumentoBO.obtenerPorId(idInsertado);
        assertNotNull(tipoDoc);
        assertEquals("test_insertar", tipoDoc.getNombre());
    }

    @Test
    @Order(3)
    public void testListar() {
        List<TipoDocumentoDTO> lista = tipoDocumentoBO.listar();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }

    @Test
    @Order(4)
    public void testModificar() {
        TipoDocumentoDTO tipoDoc = tipoDocumentoBO.obtenerPorId(idInsertado);
        tipoDoc.setNombre("test_modificado");
        Integer resultado = this.tipoDocumentoBO.modificar(tipoDoc);
        assertEquals(1, resultado);

        TipoDocumentoDTO actualizado = tipoDocumentoBO.obtenerPorId(idInsertado);
        assertEquals("test_modificado", actualizado.getNombre());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        TipoDocumentoDTO tipoDoc = tipoDocumentoBO.obtenerPorId(idInsertado);
        Integer resultado = tipoDocumentoBO.eliminar(tipoDoc);
        assertEquals(1, resultado);

        TipoDocumentoDTO eliminado = tipoDocumentoBO.obtenerPorId(idInsertado);
        assertNull(eliminado);
    }
}
