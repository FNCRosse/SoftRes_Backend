package pe.edu.pucp.softres.services;

import java.io.IOException;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import pe.edu.pucp.softres.bo.client.ReservaxMesaBO;
import pe.edu.pucp.softres.bo.client.ReservaxMesaBO.DisponibilidadResponse;
import pe.edu.pucp.softres.bo.client.ReservaxMesaBO.EstadisticasResponse;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;

/**
 * Servicio SOAP para gestión de asignación de mesas a reservas
 * Usa el cliente REST siguiendo el patrón de capas correcto
 * Proporciona una interfaz SOAP completa para verificación de disponibilidad,
 * asignación de mesas, cambio de estados y estadísticas
 * 
 * @author Rosse
 */
@WebService(serviceName = "reservaxmesa")
@XmlSeeAlso({ReservaDTO.class, MesaDTO.class, EstadoMesa.class})
public class ReservaxMesa {
    private final ReservaxMesaBO reservaxMesaBO;
    
    public ReservaxMesa() {
        this.reservaxMesaBO = new ReservaxMesaBO();
    }

    /**
     * Verifica la disponibilidad de mesas para una reserva
     * @param reserva Datos de la reserva para verificar disponibilidad
     * @return true si hay disponibilidad, false caso contrario
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "verificarDisponibilidad")
    public boolean verificarDisponibilidad(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.verificarDisponibilidad(reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al verificar disponibilidad: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene información detallada sobre la disponibilidad de mesas
     * @param reserva Datos de la reserva para verificar disponibilidad
     * @return Objeto con información detallada de disponibilidad
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "obtenerDetalleDisponibilidad")
    public DisponibilidadResponse obtenerDetalleDisponibilidad(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.obtenerDetalleDisponibilidad(reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener detalle de disponibilidad: " + e.getMessage(), e);
        }
    }

    /**
     * Asigna mesas a una reserva específica
     * @param reservaId ID de la reserva
     * @param reserva Datos de la reserva para asignar mesas
     * @return true si se asignaron las mesas exitosamente
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "asignarMesas")
    public boolean asignarMesas(Integer reservaId, ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.asignarMesas(reservaId, reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al asignar mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene las mesas asignadas a una reserva
     * @param reservaId ID de la reserva
     * @return Lista de mesas asignadas a la reserva
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "obtenerMesasDeReserva")
    public List<MesaDTO> obtenerMesasDeReserva(Integer reservaId) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.obtenerMesasDeReserva(reservaId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener mesas de reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Cambia el estado de las mesas de una reserva
     * @param reservaId ID de la reserva
     * @param nuevoEstado Nuevo estado para las mesas
     * @return true si se cambió el estado exitosamente
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "cambiarEstadoMesas")
    public boolean cambiarEstadoMesas(Integer reservaId, String nuevoEstado) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.cambiarEstadoMesas(reservaId, nuevoEstado);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al cambiar estado de mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Libera las mesas asociadas a una reserva
     * @param reservaId ID de la reserva
     * @return true si se liberaron las mesas exitosamente
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "liberarMesas")
    public boolean liberarMesas(Integer reservaId) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.liberarMesas(reservaId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al liberar mesas: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene estadísticas de ocupación de un local
     * @param localId ID del local
     * @return Objeto con estadísticas de ocupación
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "obtenerEstadisticasOcupacion")
    public EstadisticasResponse obtenerEstadisticasOcupacion(Integer localId) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.obtenerEstadisticasOcupacion(localId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener estadísticas: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si hay suficiente capacidad disponible en un local
     * @param localId ID del local
     * @param capacidadNecesaria Capacidad requerida
     * @return true si hay suficiente capacidad
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "hayCapacidadSuficiente")
    public boolean hayCapacidadSuficiente(Integer localId, Integer capacidadNecesaria) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.hayCapacidadSuficiente(localId, capacidadNecesaria);
        } catch (Exception e) {
            throw new IOException("Error al verificar capacidad: " + e.getMessage(), e);
        }
    }

    /**
     * Procesa una reserva completa (verificación, asignación y cambio de estado)
     * @param reservaId ID de la reserva
     * @param reserva Datos de la reserva
     * @return true si se procesó exitosamente
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "procesarReservaCompleta")
    public boolean procesarReservaCompleta(Integer reservaId, ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.procesarReservaCompleta(reservaId, reserva);
        } catch (Exception e) {
            throw new IOException("Error al procesar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Finaliza una reserva liberando las mesas
     * @param reservaId ID de la reserva
     * @return true si se finalizó exitosamente
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "finalizarReserva")
    public boolean finalizarReserva(Integer reservaId) throws IOException, InterruptedException {
        try {
            return this.reservaxMesaBO.finalizarReserva(reservaId);
        } catch (Exception e) {
            throw new IOException("Error al finalizar reserva: " + e.getMessage(), e);
        }
    }
}
