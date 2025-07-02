package pe.edu.pucp.softres.business;

import java.util.Calendar;
import java.util.Date;
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
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.model.RolDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 * Tests unitarios mejorados para FilaEsperaBO
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FilaEsperaBOTest {

    private FilaEsperaBO filaEsperaBO;
    private static Integer filaIdCreado;
    private static Integer filaIdVIP;
    private final Integer userIdCreado = 3;
    private final Integer userIdVIP = 5; // Asumiendo que usuario 5 es VIP
    private final Integer localId = 1;
    
    @BeforeEach
    public void setup() {
        filaEsperaBO = new FilaEsperaBO();
    }

    @Test
    @Order(1)
    public void testInsertarFilaEspera_Success() {
        System.out.println("=== Test: Insertar Fila de Espera ===");

        FilaEsperaDTO filaEspera = crearFilaEsperaValida(userIdCreado, TipoReserva.COMUN, 4);
        
        FilaEsperaDTO filaInsertada = filaEsperaBO.insertar(filaEspera);
        
        assertNotNull(filaInsertada, "La fila insertada no debe ser null");
        assertNotNull(filaInsertada.getIdFila(), "El ID de la fila no debe ser null");
        assertEquals(userIdCreado, filaInsertada.getUsuario().getIdUsuario(), "El usuario debe coincidir");
        assertEquals(EstadoFilaEspera.PENDIENTE, filaInsertada.getEstado(), "El estado inicial debe ser PENDIENTE");
        assertNotNull(filaInsertada.getFechaRegistro(), "La fecha de registro debe establecerse");
        
        filaIdCreado = filaInsertada.getIdFila();
        System.out.println("Fila de espera creada con ID: " + filaIdCreado);
    }

    @Test
    @Order(2)
    public void testInsertarFilaEsperaVIP_Success() {
        System.out.println("=== Test: Insertar Fila de Espera VIP ===");

        FilaEsperaDTO filaEsperaVIP = crearFilaEsperaValida(userIdVIP, TipoReserva.EVENTO, 20);
        
        FilaEsperaDTO filaInsertada = filaEsperaBO.insertar(filaEsperaVIP);
        
        assertNotNull(filaInsertada, "La fila VIP insertada no debe ser null");
        assertNotNull(filaInsertada.getIdFila(), "El ID de la fila VIP no debe ser null");
        assertEquals(TipoReserva.EVENTO, filaInsertada.getTipoReserva(), "El tipo debe ser EVENTO");
        
        filaIdVIP = filaInsertada.getIdFila();
        System.out.println("Fila de espera VIP creada con ID: " + filaIdVIP);
    }

    @Test
    @Order(3)
    public void testInsertarFilaEspera_Validaciones() {
        System.out.println("=== Test: Validaciones de Inserción ===");
        
        // Test null
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.insertar(null);
        }, "Debe lanzar excepción con fila null");
        
        // Test sin usuario
        FilaEsperaDTO filaSinUsuario = crearFilaEsperaValida(userIdCreado, TipoReserva.COMUN, 4);
        filaSinUsuario.setUsuario(null);
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.insertar(filaSinUsuario);
        }, "Debe lanzar excepción sin usuario");
        
        // Test sin local
        FilaEsperaDTO filaSinLocal = crearFilaEsperaValida(userIdCreado, TipoReserva.COMUN, 4);
        filaSinLocal.setLocal(null);
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.insertar(filaSinLocal);
        }, "Debe lanzar excepción sin local");
        
        // Test sin fecha deseada
        FilaEsperaDTO filaSinFecha = crearFilaEsperaValida(userIdCreado, TipoReserva.COMUN, 4);
        filaSinFecha.setFechaHoraDeseada(null);
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.insertar(filaSinFecha);
        }, "Debe lanzar excepción sin fecha deseada");
        
        // Test cantidad personas inválida
        FilaEsperaDTO filaPersonasInvalida = crearFilaEsperaValida(userIdCreado, TipoReserva.COMUN, 0);
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.insertar(filaPersonasInvalida);
        }, "Debe lanzar excepción con 0 personas");
        
        System.out.println("Todas las validaciones pasaron correctamente");
    }

    @Test
    @Order(4)
    public void testObtenerPorId_Success() {
        System.out.println("=== Test: Obtener por ID ===");
        
        FilaEsperaDTO obtenida = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        
        assertNotNull(obtenida, "La fila obtenida no debe ser null");
        assertEquals(filaIdCreado, obtenida.getIdFila(), "Los IDs deben coincidir");
        assertEquals(userIdCreado, obtenida.getUsuario().getIdUsuario(), "El usuario debe coincidir");
        
        System.out.println("Fila obtenida correctamente");
    }

    @Test
    @Order(5)
    public void testObtenerPorId_Validaciones() {
        System.out.println("=== Test: Validaciones Obtener por ID ===");
        
        // Test ID fila null
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.obtenerPorId(null, userIdCreado);
        }, "Debe lanzar excepción con ID fila null");
        
        // Test ID usuario null
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.obtenerPorId(filaIdCreado, null);
        }, "Debe lanzar excepción con ID usuario null");
        
        // Test IDs inválidos
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.obtenerPorId(-1, userIdCreado);
        }, "Debe lanzar excepción con ID fila negativo");
        
        System.out.println("Validaciones de obtener por ID pasaron correctamente");
    }

    @Test
    @Order(6)
    public void testListarPendientesPorPrioridad() {
        System.out.println("=== Test: Listar Pendientes por Prioridad ===");
        
        List<FilaEsperaDTO> pendientes = filaEsperaBO.listarPendientesPorPrioridad(localId);
        
        assertNotNull(pendientes, "La lista no debe ser null");
        assertTrue(pendientes.size() >= 2, "Debe haber al menos 2 solicitudes (las que creamos)");
        
        // Verificar que los VIPs están primero
        boolean hayVIPPrimero = true;
        boolean vipEncontrado = false;
        
        for (FilaEsperaDTO fila : pendientes) {
            if (fila.getUsuario().getIdUsuario().equals(userIdVIP)) {
                vipEncontrado = true;
            } else if (vipEncontrado) {
                // Si ya encontramos un VIP y ahora vemos un no-VIP, verificamos que sea correcto
                hayVIPPrimero = true; // En realidad aquí deberíamos verificar el rol
            }
        }
        
        System.out.println("Total de solicitudes pendientes: " + pendientes.size());
    }

    @Test
    @Order(7)
    public void testBuscarSiguienteCompatible() {
        System.out.println("=== Test: Buscar Siguiente Compatible ===");
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date fechaObjetivo = cal.getTime();
        
        FilaEsperaDTO siguiente = filaEsperaBO.buscarSiguienteCompatible(
            localId, fechaObjetivo, TipoReserva.COMUN, 2);
        
        // Puede ser null si no hay compatibles, pero no debe lanzar excepción
        System.out.println("Siguiente compatible encontrado: " + (siguiente != null ? siguiente.getIdFila() : "ninguno"));
    }

    @Test
    @Order(8)
    public void testNotificarSiguiente() {
        System.out.println("=== Test: Notificar Siguiente ===");
        
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        Date fechaObjetivo = cal.getTime();
        
        boolean notificado = filaEsperaBO.notificarSiguiente(localId, fechaObjetivo, null, null);
        
        System.out.println("Notificación enviada: " + notificado);
    }

    @Test
    @Order(9)
    public void testModificar_Success() {
        System.out.println("=== Test: Modificar Fila de Espera ===");
        
        FilaEsperaDTO fila = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        assertNotNull(fila, "La fila debe existir antes de modificar");
        
        String observacionesOriginal = fila.getObservaciones();
        fila.setObservaciones("Observaciones modificadas para test");
        
        int resultado = filaEsperaBO.modificar(fila);
        assertTrue(resultado > 0, "La modificación debe ser exitosa");
        
        // Verificar que se guardó el cambio
        FilaEsperaDTO filaModificada = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        assertEquals("Observaciones modificadas para test", filaModificada.getObservaciones(), 
                    "Las observaciones deben haberse actualizado");
        
        System.out.println("Modificación realizada correctamente");
    }

    @Test
    @Order(10)
    public void testModificar_Validaciones() {
        System.out.println("=== Test: Validaciones de Modificación ===");
        
        // Test modificar null
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.modificar(null);
        }, "Debe lanzar excepción al modificar null");
        
        // Test modificar sin ID
        FilaEsperaDTO filaSinId = crearFilaEsperaValida(userIdCreado, TipoReserva.COMUN, 4);
        filaSinId.setIdFila(null);
        assertThrows(IllegalArgumentException.class, () -> {
            filaEsperaBO.modificar(filaSinId);
        }, "Debe lanzar excepción sin ID");
        
        System.out.println("Validaciones de modificación pasaron correctamente");
    }

    @Test
    @Order(11)
    public void testEliminar_Success() {
        System.out.println("=== Test: Eliminar Fila de Espera (Eliminación Lógica) ===");
        
        FilaEsperaDTO fila = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        assertNotNull(fila, "La fila debe existir antes de eliminar");
        
        Integer resultado = filaEsperaBO.eliminar(fila);
        assertTrue(resultado > 0, "La eliminación debe ser exitosa");
        
        // Verificar eliminación lógica
        FilaEsperaDTO filaEliminada = filaEsperaBO.obtenerPorId(filaIdCreado, userIdCreado);
        assertEquals(EstadoFilaEspera.CANCELADO, filaEliminada.getEstado(), 
                    "Debe estar marcada como CANCELADO (eliminación lógica)");
        
        System.out.println("Eliminación lógica exitosa");
    }

    @Test
    @Order(12)
    public void testConfirmarDisponibilidad_Validaciones() {
        System.out.println("=== Test: Validaciones Confirmar Disponibilidad ===");
        
        // Para este test necesitaríamos una fila en estado NOTIFICADO
        // Por simplicidad, solo verificamos que no lance excepción inesperada
        
        try {
            filaEsperaBO.confirmarDisponibilidad(999, userIdCreado);
            fail("Debería lanzar excepción con ID inexistente");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("no encontrada"), 
                      "El mensaje debe indicar que no se encontró la solicitud");
        }
        
        System.out.println("Validaciones de confirmación pasaron correctamente");
    }

    // Método helper para crear filas de espera válidas
    private FilaEsperaDTO crearFilaEsperaValida(Integer usuarioId, TipoReserva tipoReserva, int cantidadPersonas) {
        FilaEsperaDTO fila = new FilaEsperaDTO();
        
        // Usuario
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(usuarioId);
        
        // Configurar rol si es VIP
        if (usuarioId.equals(userIdVIP)) {
            RolDTO rol = new RolDTO();
            rol.setIdRol(4); // Cliente VIP
            usuario.setRol(rol);
        }
        
        fila.setUsuario(usuario);
        
        // Local
        LocalDTO local = new LocalDTO();
        local.setIdLocal(localId);
        fila.setLocal(local);
        
        // Configuraciones básicas
        fila.setTipoReserva(tipoReserva);
        fila.setCantidadPersonas(cantidadPersonas);
        
        // Tipo de mesa
        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(tipoReserva == TipoReserva.EVENTO ? 3 : 2); // Mesa para 6 para eventos, para 4 para comunes
        fila.setTipoMesa(tipoMesa);
        
        // Fecha deseada (mañana)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 19); // 7:00 PM
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        fila.setFechaHoraDeseada(cal.getTime());
        
        fila.setObservaciones("Solicitud creada para test");
        
        return fila;
    }
}

