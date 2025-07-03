package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.List;
import pe.edu.pucp.softres.bo.client.ReservaxMesaBO;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 * Servicio SOAP para gestión de asignación de mesas a reservas
 * Usa el cliente REST siguiendo el patrón de capas correcto
 * Proporciona una interfaz SOAP completa para verificación de disponibilidad,
 * asignación de mesas, cambio de estados y estadísticas
 * 
 * @author Rosse
 */
@WebService(serviceName = "reservaxmesa")
@XmlSeeAlso({ReservaDTO.class, MesaDTO.class, EstadoMesa.class,ReservaxMesasDTO.class})
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
     * Libera las mesas asociadas a una reserva (método simplificado más confiable)
     * @param reservaId ID de la reserva
     * @return true si se liberaron las mesas exitosamente, false caso contrario
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
}