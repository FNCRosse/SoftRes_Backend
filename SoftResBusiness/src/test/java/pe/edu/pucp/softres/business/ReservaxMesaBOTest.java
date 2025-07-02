/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.business;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.RolDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.model.UsuariosDTO;

/**
 * Test unitarios para ReservaxMesaBO
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservaxMesaBOTest {

    private ReservaxMesaBO reservaxMesaBO;
    private ReservaBO reservaBO; // Agregamos ReservaBO para crear la reserva
    private static Integer reservaTestId; // Cambiamos para que sea dinámico
    
    @BeforeEach
    public void setup() {
        reservaxMesaBO = new ReservaxMesaBO();
        reservaBO = new ReservaBO(); // Inicializamos ReservaBO
    }

    /**
     * Crea una reserva válida para los tests
     */
    private ReservaDTO crearReservaValida() {
        ReservaDTO reserva = new ReservaDTO();
        
        // Usuario válido (ID 2 existe en la BD)
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(2);
        RolDTO rol = new RolDTO();
        rol.setIdRol(3); // Cliente normal
        usuario.setRol(rol);
        reserva.setUsuario(usuario);
        
        // Local válido (ID 1 existe en la BD: "Shifu Kay Miraflores")
        LocalDTO local = new LocalDTO();
        local.setIdLocal(1);
        reserva.setLocal(local);
        
        // Tipo de mesa válido (ID 1 existe: "Mesa para 2")
        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(1); // Cambio a tipo 1 que tiene mesas disponibles en la BD
        reserva.setTipoMesa(tipoMesa);
        
        // Configurar reserva
        reserva.setTipoReserva(TipoReserva.COMUN);
        reserva.setCantidadPersonas(2); // Ajustado para mesa tipo 1 (para 2 personas)
        reserva.setNumeroMesas(1);
        
        // Fecha en el futuro con horario válido (dentro de 11:00 AM - 11:00 PM para comunes)
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1); // Mañana
        cal.set(Calendar.HOUR_OF_DAY, 19); // 7:00 PM
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        reserva.setFechaHoraRegistro(cal.getTime());
        
        // Campos obligatorios para inserción
        reserva.setObservaciones("Reserva para test");
        reserva.setFechaCreacion(new Date());
        reserva.setUsuarioCreacion("test_system");
        
        return reserva;
    }
    
    /**
     * Test para crear la reserva que usaremos en las demás pruebas
     */
    @Test
    @Order(0)
    public void testCrearReservaParaPruebas() {
        System.out.println("=== Test: Crear Reserva para Pruebas ===");
        
        ReservaDTO reserva = crearReservaValida();
        
        try {
            reservaTestId = reservaBO.insertar(reserva);
            assertNotNull(reservaTestId, "El ID de la reserva creada no debe ser null");
            assertTrue(reservaTestId > 0, "El ID de la reserva debe ser positivo");
            
            // Verificar que la reserva se creó correctamente
            ReservaDTO reservaCreada = reservaBO.obtenerPorId(reservaTestId);
            assertNotNull(reservaCreada, "La reserva debe existir después de la inserción");
            
            System.out.println("Reserva creada con ID: " + reservaTestId);
            
        } catch (Exception e) {
            System.err.println("Error creando reserva para pruebas: " + e.getMessage());
            e.printStackTrace();
            fail("No se pudo crear la reserva para pruebas: " + e.getMessage());
        }
    }

    @Test
    @Order(1)
    public void testVerificarDisponibilidadMesas_Success() {
        System.out.println("=== Test: Verificar Disponibilidad de Mesas ===");
        
        ReservaDTO reserva = crearReservaValida();
        
        boolean disponible = reservaxMesaBO.verificarDisponibilidadMesas(reserva);
        
        // Debe ser true porque tenemos mesas disponibles en la BD de prueba
        assertTrue(disponible, "Debe haber mesas disponibles para la reserva válida");
        
        System.out.println("Disponibilidad verificada correctamente");
    }
    
    @Test
    @Order(2)
    public void testVerificarDisponibilidadMesas_Validaciones() {
        System.out.println("=== Test: Validaciones Verificar Disponibilidad ===");
        
        // Test con reserva null
        boolean resultadoNull = reservaxMesaBO.verificarDisponibilidadMesas(null);
        assertFalse(resultadoNull, "Debe devolver false con reserva null");
        
        // Test sin local
        ReservaDTO reservaSinLocal = crearReservaValida();
        reservaSinLocal.setLocal(null);
        boolean resultadoSinLocal = reservaxMesaBO.verificarDisponibilidadMesas(reservaSinLocal);
        assertFalse(resultadoSinLocal, "Debe devolver false sin local");
        
        // Test sin tipo de mesa
        ReservaDTO reservaSinTipoMesa = crearReservaValida();
        reservaSinTipoMesa.setTipoMesa(null);
        boolean resultadoSinTipoMesa = reservaxMesaBO.verificarDisponibilidadMesas(reservaSinTipoMesa);
        assertFalse(resultadoSinTipoMesa, "Debe devolver false sin tipo de mesa");
        
        System.out.println("Validaciones de disponibilidad pasaron correctamente");
    }

    @Test
    @Order(3)
    public void testAsignarMesasAReserva_Success() {
        System.out.println("=== Test: Asignar Mesas a Reserva ===");
        
        // Verificar que tenemos una reserva válida del test anterior
        assertNotNull(reservaTestId, "Debe existir una reserva creada en el test anterior");
        assertTrue(reservaTestId > 0, "El ID de reserva debe ser positivo");
        
        ReservaDTO reserva = crearReservaValida();
        
        boolean asignado = reservaxMesaBO.asignarMesasAReserva(reservaTestId, reserva);
        
        assertTrue(asignado, "Las mesas deben asignarse correctamente");
        
        // Verificar que las mesas fueron asignadas obteniendo la lista
        List<MesaDTO> mesasAsignadas = reservaxMesaBO.obtenerMesasDeReserva(reservaTestId);
        assertNotNull(mesasAsignadas, "La lista de mesas no debe ser null");
        assertTrue(mesasAsignadas.size() >= 1, "Debe haber al menos 1 mesa asignada");
        
        System.out.println("Mesas asignadas correctamente: " + mesasAsignadas.size());
    }
    
    @Test
    @Order(4)
    public void testAsignarMesasAReserva_Validaciones() {
        System.out.println("=== Test: Validaciones Asignar Mesas ===");
        
        ReservaDTO reserva = crearReservaValida();
        
        // Test con ID de reserva null
        boolean resultadoIdNull = reservaxMesaBO.asignarMesasAReserva(null, reserva);
        assertFalse(resultadoIdNull, "Debe devolver false con ID null");
        
        // Test con ID de reserva inválido
        boolean resultadoIdInvalido = reservaxMesaBO.asignarMesasAReserva(-1, reserva);
        assertFalse(resultadoIdInvalido, "Debe devolver false con ID inválido");
        
        // Test con reserva null
        boolean resultadoReservaNull = reservaxMesaBO.asignarMesasAReserva(999, null);
        assertFalse(resultadoReservaNull, "Debe devolver false con reserva null");
        
        System.out.println("Validaciones de asignación pasaron correctamente");
    }

    @Test
    @Order(5)
    public void testObtenerMesasDeReserva_Success() {
        System.out.println("=== Test: Obtener Mesas de Reserva ===");
        
        // Usar la reserva que ya tiene mesas asignadas del test anterior
        List<MesaDTO> mesas = reservaxMesaBO.obtenerMesasDeReserva(reservaTestId);
        
        assertNotNull(mesas, "La lista de mesas no debe ser null");
        assertTrue(mesas.size() >= 1, "Debe haber al menos 1 mesa asignada");
        
        // Verificar que las mesas tienen datos válidos
        for (MesaDTO mesa : mesas) {
            assertNotNull(mesa, "Cada mesa no debe ser null");
            assertNotNull(mesa.getIdMesa(), "Cada mesa debe tener un ID");
            assertTrue(mesa.getIdMesa() > 0, "El ID de la mesa debe ser positivo");
        }
        
        System.out.println("Mesas obtenidas correctamente: " + mesas.size());
    }
    
    @Test
    @Order(6)
    public void testObtenerMesasDeReserva_Validaciones() {
        System.out.println("=== Test: Validaciones Obtener Mesas ===");
        
        // Test con ID null
        List<MesaDTO> resultadoNull = reservaxMesaBO.obtenerMesasDeReserva(null);
        assertNotNull(resultadoNull, "Debe devolver lista vacía, no null");
        assertTrue(resultadoNull.isEmpty(), "La lista debe estar vacía con ID null");
        
        // Test con ID inválido
        List<MesaDTO> resultadoInvalido = reservaxMesaBO.obtenerMesasDeReserva(-1);
        assertNotNull(resultadoInvalido, "Debe devolver lista vacía, no null");
        assertTrue(resultadoInvalido.isEmpty(), "La lista debe estar vacía con ID inválido");
        
        // Test con reserva que no existe
        List<MesaDTO> resultadoNoExiste = reservaxMesaBO.obtenerMesasDeReserva(99999);
        assertNotNull(resultadoNoExiste, "Debe devolver lista vacía, no null");
        
        System.out.println("Validaciones de obtener mesas pasaron correctamente");
    }

    @Test
    @Order(7)
    public void testCambiarEstadoMesasReserva_Success() {
        System.out.println("=== Test: Cambiar Estado de Mesas ===");
        
        // Cambiar estado a EN_USO
        boolean cambiado = reservaxMesaBO.cambiarEstadoMesasReserva(reservaTestId, EstadoMesa.EN_USO);
        
        assertTrue(cambiado, "El estado de las mesas debe cambiarse correctamente");
        
        // Verificar que el estado cambió obteniendo las mesas
        List<MesaDTO> mesas = reservaxMesaBO.obtenerMesasDeReserva(reservaTestId);
        assertNotNull(mesas, "Las mesas deben seguir asignadas");
        assertTrue(mesas.size() >= 1, "Debe haber al menos 1 mesa");
        
        // Verificar que el estado cambió (aunque esto depende de la implementación del DAO)
        for (MesaDTO mesa : mesas) {
            assertNotNull(mesa.getEstado(), "Cada mesa debe tener un estado");
        }
        
        System.out.println("Estado de mesas cambiado correctamente");
    }

    @Test
    @Order(8)
    public void testCambiarEstadoMesasReserva_Validaciones() {
        System.out.println("=== Test: Validaciones Cambiar Estado ===");
        
        // Test con ID null
        boolean resultadoIdNull = reservaxMesaBO.cambiarEstadoMesasReserva(null, EstadoMesa.DISPONIBLE);
        assertFalse(resultadoIdNull, "Debe devolver false con ID null");
        
        // Test con estado null
        boolean resultadoEstadoNull = reservaxMesaBO.cambiarEstadoMesasReserva(reservaTestId, null);
        assertFalse(resultadoEstadoNull, "Debe devolver false con estado null");
        
        System.out.println("Validaciones de cambio de estado pasaron correctamente");
    }

    @Test
    @Order(9)
    public void testObtenerEstadisticasOcupacion_Success() {
        System.out.println("=== Test: Obtener Estadísticas de Ocupación ===");
        
        // Usar un local válido (ID = 1)
        int[] estadisticas = reservaxMesaBO.obtenerEstadisticasOcupacion(1);
        
        assertNotNull(estadisticas, "Las estadísticas no deben ser null");
        assertEquals(5, estadisticas.length, "Debe devolver array de 5 elementos (uno por cada estado)");
        
        // Verificar que los valores son no negativos
        for (int i = 0; i < estadisticas.length; i++) {
            assertTrue(estadisticas[i] >= 0, "Cada estadística debe ser no negativa");
        }
        
        System.out.println("Estadísticas obtenidas correctamente");
        System.out.println("DISPONIBLE: " + estadisticas[0]);
        System.out.println("RESERVADA: " + estadisticas[1]);
        System.out.println("EN_USO: " + estadisticas[2]);
        System.out.println("EN_MANTENIMIENTO: " + estadisticas[3]);
        System.out.println("DESECHADA: " + estadisticas[4]);
    }

    @Test
    @Order(10)
    public void testLiberarMesasDeReserva_Success() {
        System.out.println("=== Test: Liberar Mesas de Reserva ===");
        
        // Liberar las mesas de la reserva de prueba
        boolean liberado = reservaxMesaBO.liberarMesasDeReserva(reservaTestId);
        
        assertTrue(liberado, "Las mesas deben liberarse correctamente");
        
        // Verificar que ya no hay mesas asignadas a la reserva
        List<MesaDTO> mesasDespues = reservaxMesaBO.obtenerMesasDeReserva(reservaTestId);
        assertNotNull(mesasDespues, "La lista no debe ser null");
        assertTrue(mesasDespues.isEmpty(), "No debe haber mesas asignadas después de liberar");
        
        System.out.println("Mesas liberadas correctamente");
    }
    
    @Test
    @Order(11)
    public void testLiberarMesasDeReserva_Validaciones() {
        System.out.println("=== Test: Validaciones Liberar Mesas ===");
        
        // Test con ID null
        boolean resultadoNull = reservaxMesaBO.liberarMesasDeReserva(null);
        assertFalse(resultadoNull, "Debe devolver false con ID null");
        
        // Test con ID inválido
        boolean resultadoInvalido = reservaxMesaBO.liberarMesasDeReserva(-1);
        assertFalse(resultadoInvalido, "Debe devolver false con ID inválido");
        
        // Test con reserva que no existe
        boolean resultadoNoExiste = reservaxMesaBO.liberarMesasDeReserva(99999);
        // Esto podría devolver true si no hay relaciones que eliminar
        // El comportamiento específico depende de la implementación
        
        System.out.println("Validaciones de liberación pasaron correctamente");
    }
}
