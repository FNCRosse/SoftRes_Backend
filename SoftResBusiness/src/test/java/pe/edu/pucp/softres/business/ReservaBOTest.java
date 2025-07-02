package pe.edu.pucp.softres.business;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.RolDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 * Test unitarios mejorados para ReservaBO
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
    public void testInsertarReservaComun_Success() {
        System.out.println("=== Test: Insertar Reserva Común ===");
        
        ReservaDTO reserva = crearReservaValida(TipoReserva.COMUN);
        reserva.setObservaciones("reserva comun test");
        
        Integer reservaId = this.reservaBO.insertar(reserva);
        
        assertNotNull(reservaId, "El ID de la reserva no debe ser null");
        assertTrue(reservaId > 0, "El ID de la reserva debe ser positivo");
        
        ReservaDTO reservaInsertada = this.reservaBO.obtenerPorId(reservaId);
        assertNotNull(reservaInsertada, "La reserva insertada debe existir");
        assertEquals(EstadoReserva.PENDIENTE, reservaInsertada.getEstado(), "El estado inicial debe ser PENDIENTE");
        assertEquals(TipoReserva.COMUN, reservaInsertada.getTipoReserva(), "El tipo debe ser COMUN");
        assertNotNull(reservaInsertada.getFechaCreacion(), "La fecha de creación debe establecerse");
        
        reservaComunIdCreado = reservaId;
        System.out.println("Reserva común creada con ID: " + reservaComunIdCreado);
    }
    
    @Test
    @Order(2)
    public void testInsertarReservaEvento_Success() {
        System.out.println("=== Test: Insertar Reserva Evento ===");
        
        ReservaDTO reserva = crearReservaValida(TipoReserva.EVENTO);
        reserva.setObservaciones("reserva evento test");
        reserva.setNombreEvento("Evento test");
        reserva.setDescripcionEvento("descripcion evento test");
        
        Integer reservaId = this.reservaBO.insertar(reserva);
        
        assertNotNull(reservaId, "El ID de la reserva no debe ser null");
        assertTrue(reservaId > 0, "El ID de la reserva debe ser positivo");
        
        ReservaDTO reservaInsertada = this.reservaBO.obtenerPorId(reservaId);
        assertNotNull(reservaInsertada, "La reserva insertada debe existir");
        assertEquals(TipoReserva.EVENTO, reservaInsertada.getTipoReserva(), "El tipo debe ser EVENTO");
        assertEquals("Evento test", reservaInsertada.getNombreEvento(), "El nombre del evento debe persistirse");
        
        reservaEventoIdCreado = reservaId;
        System.out.println("Reserva evento creada con ID: " + reservaEventoIdCreado);
    }
    
    @Test
    @Order(3)
    public void testInsertarReserva_Validaciones() {
        System.out.println("=== Test: Validaciones de Inserción ===");
        
        // Test null
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(null);
        }, "Debe lanzar excepción con reserva null");
        
        // Test sin fecha
        ReservaDTO reservaSinFecha = crearReservaValida(TipoReserva.COMUN);
        reservaSinFecha.setFechaHoraRegistro(null);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaSinFecha);
        }, "Debe lanzar excepción sin fecha");
        
        // Test fecha en el pasado
        ReservaDTO reservaPasado = crearReservaValida(TipoReserva.COMUN);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, -2); // Claramente en el pasado
        reservaPasado.setFechaHoraRegistro(cal.getTime());
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaPasado);
        }, "Debe lanzar excepción con fecha en el pasado");
        
        // Test fecha con menos de 30 minutos de anticipación
        ReservaDTO reservaPocoTiempo = crearReservaValida(TipoReserva.COMUN);
        Calendar calPocoTiempo = Calendar.getInstance();
        calPocoTiempo.add(Calendar.MINUTE, 15); // Solo 15 minutos de anticipación
        reservaPocoTiempo.setFechaHoraRegistro(calPocoTiempo.getTime());
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaPocoTiempo);
        }, "Debe lanzar excepción con menos de 30 minutos de anticipación");
        
        // Test sin usuario
        ReservaDTO reservaSinUsuario = crearReservaValida(TipoReserva.COMUN);
        reservaSinUsuario.setUsuario(null);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaSinUsuario);
        }, "Debe lanzar excepción sin usuario");
        
        // Test sin local
        ReservaDTO reservaSinLocal = crearReservaValida(TipoReserva.COMUN);
        reservaSinLocal.setLocal(null);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaSinLocal);
        }, "Debe lanzar excepción sin local");
        
        // Test cantidad personas inválida
        ReservaDTO reservaPersonasInvalida = crearReservaValida(TipoReserva.COMUN);
        reservaPersonasInvalida.setCantidadPersonas(0);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaPersonasInvalida);
        }, "Debe lanzar excepción con 0 personas");
        
        // Test demasiadas personas
        ReservaDTO reservaMuchasPersonas = crearReservaValida(TipoReserva.COMUN);
        reservaMuchasPersonas.setCantidadPersonas(125);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.insertar(reservaMuchasPersonas);
        }, "Debe lanzar excepción con más de 100 personas");
        
        System.out.println("Todas las validaciones pasaron correctamente");
    }
    
    @Test
    @Order(4)
    public void testObtenerPorId_Success() {
        System.out.println("=== Test: Obtener por ID ===");
        
        ReservaDTO reservaComun = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertNotNull(reservaComun, "La reserva común debe existir");
        assertEquals(reservaComunIdCreado, reservaComun.getIdReserva(), "Los IDs deben coincidir");
        assertEquals(TipoReserva.COMUN, reservaComun.getTipoReserva(), "El tipo debe ser COMUN");
        
        ReservaDTO reservaEvento = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertNotNull(reservaEvento, "La reserva evento debe existir");
        assertEquals(reservaEventoIdCreado, reservaEvento.getIdReserva(), "Los IDs deben coincidir");
        assertEquals(TipoReserva.EVENTO, reservaEvento.getTipoReserva(), "El tipo debe ser EVENTO");
        
        System.out.println("Reservas obtenidas correctamente");
    }
    
    @Test
    @Order(5)
    public void testObtenerPorId_Validaciones() {
        System.out.println("=== Test: Validaciones Obtener por ID ===");
        
        // Test ID null
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.obtenerPorId(null);
        }, "Debe lanzar excepción con ID null");
        
        // Test ID inválido
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.obtenerPorId(-1);
        }, "Debe lanzar excepción con ID negativo");
        
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.obtenerPorId(0);
        }, "Debe lanzar excepción con ID cero");
        
        System.out.println("Validaciones de ID pasaron correctamente");
    }
    
    @Test
    @Order(6)
    public void testListar_Success() {
        System.out.println("=== Test: Listar Reservas ===");
        
        // Test listar todas
        ReservaParametros parametrosVacios = new ReservaParametros();
        List<ReservaDTO> todasLasReservas = this.reservaBO.listar(parametrosVacios);
        assertNotNull(todasLasReservas, "La lista no debe ser null");
        assertTrue(todasLasReservas.size() >= 2, "Debe haber al menos 2 reservas (las que creamos)");
        
        // Test listar con parámetros null
        List<ReservaDTO> reservasConNull = this.reservaBO.listar(null);
        assertNotNull(reservasConNull, "La lista no debe ser null aunque los parámetros sean null");
        
        // Test filtrar por tipo
        ReservaParametros parametrosTipo = new ReservaParametros();
        parametrosTipo.setTipoReserva(TipoReserva.COMUN);
        List<ReservaDTO> reservasComunes = this.reservaBO.listar(parametrosTipo);
        assertNotNull(reservasComunes, "La lista de reservas comunes no debe ser null");
        
        // Test filtrar por estado
        ReservaParametros parametrosEstado = new ReservaParametros();
        parametrosEstado.setEstado(EstadoReserva.PENDIENTE);
        List<ReservaDTO> reservasPendientes = this.reservaBO.listar(parametrosEstado);
        assertNotNull(reservasPendientes, "La lista de reservas pendientes no debe ser null");
        
        System.out.println("Total de reservas encontradas: " + todasLasReservas.size());
    }
    
    @Test
    @Order(7)
    public void testModificar_Success() {
        System.out.println("=== Test: Modificar Reserva ===");
        
        ReservaDTO reservaComun = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertNotNull(reservaComun, "La reserva debe existir antes de modificar");
        
        String observacionesOriginal = reservaComun.getObservaciones();
        reservaComun.setObservaciones("reserva comun test modificado");
        reservaComun.setUsuarioModificacion("test_modificacion");
        
        int resultado = this.reservaBO.modificar(reservaComun);
        assertTrue(resultado > 0, "La modificación debe ser exitosa");
        
        // Verificar que se guardó el cambio
        ReservaDTO reservaModificada = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertEquals("reserva comun test modificado", reservaModificada.getObservaciones(), 
                    "Las observaciones deben haberse actualizado");
        assertNotNull(reservaModificada.getFechaModificacion(), 
                     "La fecha de modificación debe establecerse");
        
        // Test modificar reserva evento
        ReservaDTO reservaEvento = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        reservaEvento.setObservaciones("reserva evento test modificado");
        reservaEvento.setUsuarioModificacion("test_modificacion");
        
        int resultadoEvento = this.reservaBO.modificar(reservaEvento);
        assertTrue(resultadoEvento > 0, "La modificación del evento debe ser exitosa");
        
        System.out.println("Modificaciones realizadas correctamente");
    }
    
    @Test
    @Order(8)
    public void testModificar_Validaciones() {
        System.out.println("=== Test: Validaciones de Modificación ===");
        
        // Test modificar null
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.modificar(null);
        }, "Debe lanzar excepción al modificar null");
        
        // Test modificar sin ID
        ReservaDTO reservaSinId = crearReservaValida(TipoReserva.COMUN);
        reservaSinId.setIdReserva(null);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.modificar(reservaSinId);
        }, "Debe lanzar excepción sin ID");
        
        System.out.println("Validaciones de modificación pasaron correctamente");
    }
    
    @Test
    @Order(9)
    public void testConfirmarReserva_Success() {
        System.out.println("=== Test: Confirmar Reserva ===");
        
        // Confirmar la reserva común (asumiendo que el usuario 1 es admin/gerente/mozo)
        Integer usuarioConfirmador = 1;
        Integer resultado = this.reservaBO.confirmarReserva(reservaComunIdCreado, usuarioConfirmador);
        assertTrue(resultado > 0, "La confirmación debe ser exitosa");
        
        // Verificar que el estado cambió
        ReservaDTO reservaConfirmada = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertEquals(EstadoReserva.CONFIRMADA, reservaConfirmada.getEstado(), 
                    "El estado debe ser CONFIRMADA");
        assertNotNull(reservaConfirmada.getFechaModificacion(), 
                     "La fecha de modificación debe establecerse");
        
        System.out.println("Reserva confirmada exitosamente");
    }
    
    @Test
    @Order(10)
    public void testConfirmarReserva_Validaciones() {
        System.out.println("=== Test: Validaciones de Confirmación ===");
        
        // Test IDs inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.confirmarReserva(null, 1);
        }, "Debe lanzar excepción con reservaId null");
        
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.confirmarReserva(1, null);
        }, "Debe lanzar excepción con usuarioId null");
        
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.confirmarReserva(-1, 1);
        }, "Debe lanzar excepción con reservaId negativo");
        
        // Test confirmar reserva ya confirmada
        assertThrows(RuntimeException.class, () -> {
            this.reservaBO.confirmarReserva(reservaComunIdCreado, 1);
        }, "Debe lanzar excepción al confirmar reserva ya confirmada");
        
        System.out.println("Validaciones de confirmación pasaron correctamente");
    }
    
    @Test
    @Order(11)
    public void testCancelar_Success() {
        System.out.println("=== Test: Cancelar Reserva ===");
        
        // Cancelar la reserva evento
        ReservaDTO reservaEvento = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        
        // Crear un motivo de cancelación simple para el test
        MotivosCancelacionDTO motivo = new MotivosCancelacionDTO();
        motivo.setIdMotivo(1);
        motivo.setDescripcion("Cancelación por test");
        
        reservaEvento.setEstado(EstadoReserva.CANCELADA);
        reservaEvento.setMotivoCancelacion(motivo);
        reservaEvento.setUsuarioModificacion("test_cancelar");
        
        Integer resultado = this.reservaBO.cancelar(reservaEvento);
        assertTrue(resultado > 0, "La cancelación debe ser exitosa");
        
        // Verificar el estado
        ReservaDTO reservaCancelada = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertEquals(EstadoReserva.CANCELADA, reservaCancelada.getEstado(), 
                    "El estado debe ser CANCELADA");
        
        System.out.println("Reserva cancelada exitosamente");
    }
    
    @Test
    @Order(12)
    public void testCancelar_Validaciones() {
        System.out.println("=== Test: Validaciones de Cancelación ===");
        
        // Test cancelar null
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.cancelar(null);
        }, "Debe lanzar excepción al cancelar null");
        
        // Test cancelar sin ID
        ReservaDTO reservaSinId = crearReservaValida(TipoReserva.COMUN);
        assertThrows(IllegalArgumentException.class, () -> {
            this.reservaBO.cancelar(reservaSinId);
        }, "Debe lanzar excepción sin ID");
        
        // Test cancelar reserva ya cancelada
        ReservaDTO reservaYaCancelada = this.reservaBO.obtenerPorId(reservaEventoIdCreado);
        assertThrows(RuntimeException.class, () -> {
            this.reservaBO.cancelar(reservaYaCancelada);
        }, "Debe lanzar excepción al cancelar reserva ya cancelada");
        
        System.out.println("Validaciones de cancelación pasaron correctamente");
    }
    
    @Test
    @Order(13)
    public void testEliminar_Success() {
        System.out.println("=== Test: Eliminar Reserva (Eliminación Lógica) ===");
        
        // La eliminación debe ser igual a la cancelación
        ReservaDTO reservaComun = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        
        // Crear un motivo de cancelación simple para el test
        MotivosCancelacionDTO motivo = new MotivosCancelacionDTO();
        motivo.setIdMotivo(2);
        motivo.setDescripcion("Eliminación por test");
        
        reservaComun.setEstado(EstadoReserva.CANCELADA);
        reservaComun.setMotivoCancelacion(motivo);
        reservaComun.setUsuarioModificacion("test_eliminar");
        
        Integer resultado = this.reservaBO.eliminar(reservaComun);
        assertTrue(resultado > 0, "La eliminación debe ser exitosa");
        
        // Verificar eliminación lógica
        ReservaDTO reservaEliminada = this.reservaBO.obtenerPorId(reservaComunIdCreado);
        assertEquals(EstadoReserva.CANCELADA, reservaEliminada.getEstado(), 
                    "Debe estar marcada como CANCELADA (eliminación lógica)");
        
        System.out.println("Eliminación lógica exitosa");
    }
    
    // Método helper para crear reservas válidas
    private ReservaDTO crearReservaValida(TipoReserva tipo) {
        ReservaDTO reserva = new ReservaDTO();
        
        // Usuario válido
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(2);
        RolDTO rol = new RolDTO();
        rol.setIdRol(3); // Cliente normal
        usuario.setRol(rol);
        usuario.setEstado(true);
        reserva.setUsuario(usuario);
        
        // Local válido
        LocalDTO local = new LocalDTO();
        local.setIdLocal(1);
        reserva.setLocal(local);
        
        // Fila de espera (opcional)
        FilaEsperaDTO fila = new FilaEsperaDTO();
        fila.setIdFila(2);
        reserva.setFilaEspera(fila);
        
        // Tipo de mesa
        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(2);
        reserva.setTipoMesa(tipoMesa);
        
        // Configurar reserva
        reserva.setTipoReserva(tipo);
        
        // Fecha en el futuro con horarios válidos
        Calendar cal = Calendar.getInstance();
        if (tipo == TipoReserva.EVENTO) {
            cal.add(Calendar.DAY_OF_YEAR, 8); // 8 días para eventos (mínimo 7)
            cal.set(Calendar.HOUR_OF_DAY, 14); // 2:00 PM (dentro del rango 10:00 AM - 8:00 PM)
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            // Ajustar cantidad de personas para eventos
            reserva.setCantidadPersonas(25); // Mínimo 20 para eventos
        } else {
            cal.add(Calendar.DAY_OF_YEAR, 1); // 1 día para reservas comunes
            cal.set(Calendar.HOUR_OF_DAY, 19); // 7:00 PM (dentro del rango 11:00 AM - 11:00 PM)
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            reserva.setCantidadPersonas(4); // Para reservas comunes (máximo 8)
        }
        reserva.setFechaHoraRegistro(cal.getTime());
        
        reserva.setNumeroMesas(1);
        reserva.setUsuarioCreacion("test_insercion");
        
        return reserva;
    }
}
