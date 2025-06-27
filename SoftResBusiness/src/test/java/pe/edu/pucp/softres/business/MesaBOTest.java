
package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
/**
 *
 * @author frank
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MesaBOTest {

    private MesaBO mesaBO;
    private static Integer mesaidGenerado;

    @BeforeEach
    public void setUp() {
        this.mesaBO = new MesaBO();
    }
    
    @Test
    @Order(1)
    public void testInsertar() {
        LocalDTO local = new LocalDTO();
        local.setIdLocal(2);
        TipoMesaDTO tipo = new TipoMesaDTO();
        tipo.setIdTipoMesa(2);
        MesaDTO mesa = new MesaDTO();
        mesa.setLocal(local);
        mesa.setTipoMesa(tipo);
        mesa.setNumeroMesa("m-564");
        mesa.setCapacidad(4);
        mesa.setEstado(EstadoMesa.DISPONIBLE);
        mesa.setX(23);
        mesa.setY(12);
        mesa.setFechaCreacion(new Date());
        mesa.setUsuarioCreacion("test_user");
        MesaDTO insertado = this.mesaBO.insertar(mesa);
        assertNotNull(insertado);
        mesaidGenerado = insertado.getIdMesa();
    }
    
    @Test
    @Order(2)
    public void testObtenerPorId() {
        MesaDTO mesa = this.mesaBO.obtenerPorId(mesaidGenerado);
        assertNotNull(mesa);
        assertEquals("m-564", mesa.getNumeroMesa());
    }
    
    @Test
    @Order(3)
    public void testListar() {
        MesaParametros parametros = new MesaParametros();
        List<MesaDTO> lista = this.mesaBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(t -> t.getIdMesa().equals(mesaidGenerado)));
    }
    
    @Test
    @Order(4)
    public void testModificar() {
        MesaDTO Mesa = this.mesaBO.obtenerPorId(mesaidGenerado);
        Mesa.setNumeroMesa("mm-567");
        Mesa.setUsuarioModificacion("test_modificar");
        Mesa.setFechaModificacion(new Date());
        int updated = this.mesaBO.modificar(Mesa);
        assertTrue(updated > 0);

        MesaDTO modificado = this.mesaBO.obtenerPorId(mesaidGenerado);
        assertEquals("mm-567", modificado.getNumeroMesa());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        MesaDTO Mesa = this.mesaBO.obtenerPorId(mesaidGenerado);
        Mesa.setEstado(EstadoMesa.DESECHADA);
        Mesa.setUsuarioModificacion("test_eliminado");
        Mesa.setFechaModificacion(new Date());
        int eliminado = this.mesaBO.eliminar(Mesa);
        assertTrue(eliminado > 0);
        MesaDTO deleted = this.mesaBO.obtenerPorId(mesaidGenerado);
        assertEquals(EstadoMesa.DESECHADA, deleted.getEstado());
    }

}

