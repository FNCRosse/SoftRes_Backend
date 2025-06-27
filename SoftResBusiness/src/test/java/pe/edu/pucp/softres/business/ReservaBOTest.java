
package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservaBOTest {

    private ReservaBO reservaBO;
    private MotivoCancelacionBO motivoCancelacionBO;
    private static Integer reservaComunIdCreado;
    private static Integer reservaEventoIdCreado;
    
    @BeforeEach
    public void setup() {
        this.reservaBO = new ReservaBO();
        this.motivoCancelacionBO = new MotivoCancelacionBO();
    }
    
    @Test
    @Order(1)
    public void testInsertar() {
        ReservaDTO reserva = new ReservaDTO();
        UsuariosDTO user = new UsuariosDTO();
        user.setIdUsuario(2);
        LocalDTO local = new LocalDTO();
        local.setIdLocal(1);
        FilaEsperaDTO fila = new FilaEsperaDTO();
        fila.setIdFila(2);
        TipoMesaDTO tmesa = new TipoMesaDTO();
        //Insertar tipo de reserva comun
        tmesa.setIdTipoMesa(2);
        reserva.setUsuario(user);
        reserva.setLocal(local);
        reserva.setFilaEspera(fila);
        reserva.setTipoReserva(TipoReserva.COMUN);
        reserva.setFecha_Hora(new Date());
        reserva.setCantidad_personas(3);
        reserva.setTipoMesa(tmesa);
        reserva.setNumeroMesas(1);
        reserva.setObservaciones("reserva comun test");
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setFechaCreacion(new Date());
        reserva.setUsuarioCreacion("test_insercion");
        ReservaDTO insertado = this.reservaBO.insertar(reserva);
        assertNotNull(insertado);
        reservaComunIdCreado = insertado.getIdReserva();
        
        //Insertar Tipo de reserva evento
        reserva.setTipoReserva(TipoReserva.EVENTO);
        reserva.setObservaciones("reserva evento test");
        reserva.setNombreEvento("Evento test");
        reserva.setDescripcionEvento("descripcion evento test");
        ReservaDTO insertadoevento = this.reservaBO.insertar(reserva);
        assertNotNull(insertadoevento);
        reservaEventoIdCreado = insertadoevento.getIdReserva();
    }
    
    @Test
    @Order(2)
    public void testObtenerPorId() {
        ReservaDTO reservaComun = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertNotNull(reservaComun);
        assertEquals(reservaComunIdCreado, reservaComun.getIdReserva());
        assertEquals(TipoReserva.COMUN, reservaComun.getTipoReserva());
        
        ReservaDTO reservaEvento = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertNotNull(reservaEvento);
        assertEquals(reservaEventoIdCreado, reservaEvento.getIdReserva());
        assertEquals(TipoReserva.EVENTO, reservaEvento.getTipoReserva());
    }
    
    @Test
    @Order(3)
    public void testModificar() {
        ReservaDTO reservaComun = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertNotNull(reservaComun);
        reservaComun.setObservaciones("reserva comun test modificado");
        reservaComun.setFechaModificacion(new Date());
        reservaComun.setUsuarioModificacion("test_modificacion");
        int result = this.reservaBO.modificar(reservaComun);
        assertNotEquals(0, result);
        ReservaDTO reservaComunMod = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertEquals("reserva comun test modificado", reservaComunMod.getObservaciones());
        
        //Reserva Evento 
        ReservaDTO reservaEvento = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertNotNull(reservaEvento);
        reservaEvento.setObservaciones("reserva evento test modificado");
        reservaEvento.setFechaModificacion(new Date());
        reservaEvento.setUsuarioModificacion("test_modificacion");
        int resultEvento = this.reservaBO.modificar(reservaEvento);
        assertNotEquals(0, resultEvento);
        ReservaDTO reservaEventoMod = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertEquals("reserva evento test modificado", reservaEventoMod.getObservaciones());
    }
    
    @Test
    @Order(4)
    public void testListar() {
        ReservaParametros parametros = new ReservaParametros();
        List<ReservaDTO> lista = this.reservaBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }
    
    @Test
    @Order(5)
    public void testEliminar() {
        ReservaDTO reservaComun = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        // Aqui se insertaria el motivo Cancelacion escrito
        MotivosCancelacionDTO mCancelacionComun = this.motivoCancelacionBO.obtenerPorId(2);
        reservaComun.setFechaModificacion(new Date());
        reservaComun.setUsuarioModificacion("test_eliminar");
        reservaComun.setEstado(EstadoReserva.CANCELADA);
        reservaComun.setMotivoCancelacion(mCancelacionComun);
        Integer deletedComun = this.reservaBO.eliminar(reservaComun);
        assertNotEquals(0, deletedComun);
        ReservaDTO reservaComunElim = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertEquals(EstadoReserva.CANCELADA, reservaComunElim.getEstado());// Verificamos que fue eliminado lógicamente
        
        ReservaDTO reservaEvento = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        MotivosCancelacionDTO mCancelacionEvento = this.motivoCancelacionBO.obtenerPorId(1);
        reservaEvento.setFechaModificacion(new Date());
        reservaEvento.setUsuarioModificacion("test_eliminar");
        reservaEvento.setEstado(EstadoReserva.CANCELADA);
        reservaEvento.setMotivoCancelacion(mCancelacionEvento);
        Integer deletedEvento = this.reservaBO.eliminar(reservaEvento);
        assertNotEquals(0, deletedEvento);
        ReservaDTO reservaEventoElim = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertEquals(EstadoReserva.CANCELADA, reservaEventoElim.getEstado());// Verificamos que fue eliminado lógicamente
    }
}
