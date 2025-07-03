/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import pe.edu.pucp.softres.bo.client.ReservaBO;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 * Servicio SOAP para gestión de reservas
 * Usa el cliente REST siguiendo el patrón de capas correcto
 * Proporciona una interfaz SOAP sobre los servicios REST
 * 
 * @author Rosse
 */
@WebService(serviceName = "reservas")
@XmlSeeAlso({ReservaDTO.class, ReservaParametros.class,ReservaxMesasDTO.class})
public class Reserva {
    private final ReservaBO reservaBO;

    public Reserva() {
        this.reservaBO = new ReservaBO();
    }

    /**
     * Inserta una nueva reserva en el sistema
     * @param reserva Datos de la reserva a insertar
     * @return ID de la reserva creada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "insertar")
    public Integer insertarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            if (reserva != null) {
                reserva.setFechaCreacion(new Date());
            }
            return this.reservaBO.insertar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al insertar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una reserva por su ID
     * @param reservaId ID de la reserva a buscar
     * @return Datos de la reserva encontrada
     * @throws IOException Si hay error de comunicación o la reserva no existe
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "obtenerPorId")
    public ReservaDTO obtenerReservaPorId(Integer reservaId) throws IOException, InterruptedException {
        try {
            return this.reservaBO.obtenerPorId(reservaId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Lista reservas según parámetros de búsqueda
     * @param parametros Criterios de búsqueda
     * @return Lista de reservas que coinciden con los criterios
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "listar")
    public List<ReservaDTO> listarReserva(ReservaParametros parametros) throws IOException, InterruptedException {
        try {
            return this.reservaBO.listar(parametros);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar reservas: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica una reserva existente
     * @param reserva Datos actualizados de la reserva
     * @return ID de la reserva modificada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "modificar")
    public Integer modificarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            if (reserva != null) {
                reserva.setFechaModificacion(new Date());
            }
            return this.reservaBO.modificar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al modificar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Cancela una reserva (eliminación lógica)
     * @param reserva Reserva a cancelar
     * @return ID de la reserva cancelada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "cancelar")
    public Integer cancelarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            if (reserva != null) {
                reserva.setFechaModificacion(new Date());
                reserva.setEstado(EstadoReserva.CANCELADA);
            }
            return this.reservaBO.cancelar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al cancelar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una reserva (eliminación lógica - mismo comportamiento que cancelar)
     * @param reserva Reserva a eliminar
     * @return ID de la reserva eliminada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "eliminar")
    public Integer eliminarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        try {
            if (reserva != null) {
                reserva.setFechaModificacion(new Date());
                reserva.setEstado(EstadoReserva.CANCELADA);
            }
            return this.reservaBO.eliminar(reserva);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al eliminar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Confirma una reserva usando el método PUT con parámetros en URL
     * @param reservaId ID de la reserva a confirmar
     * @param usuarioConfirmadorId ID del usuario que confirma la reserva
     * @return ID de la reserva confirmada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "confirmar")
    public Integer confirmarReserva(Integer reservaId, Integer usuarioConfirmadorId) throws IOException, InterruptedException {
        try {
            return this.reservaBO.confirmarReserva(reservaId, usuarioConfirmadorId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al confirmar reserva: " + e.getMessage(), e);
        }
    }

    /**
     * Confirma una reserva usando el método POST alternativo
     * @param reservaId ID de la reserva a confirmar
     * @param usuarioConfirmadorId ID del usuario que confirma la reserva
     * @return ID de la reserva confirmada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "confirmarPost")
    public Integer confirmarReservaPost(Integer reservaId, Integer usuarioConfirmadorId) throws IOException, InterruptedException {
        try {
            return this.reservaBO.confirmarReservaPost(reservaId, usuarioConfirmadorId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al confirmar reserva (POST): " + e.getMessage(), e);
        }
    }
}