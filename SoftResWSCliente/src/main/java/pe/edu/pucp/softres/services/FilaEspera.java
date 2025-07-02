package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.FilaEsperaBO;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;
import pe.edu.pucp.softres.model.TipoReserva;

/**
 * Servicio SOAP para gestión de fila de espera
 * Usa el cliente REST siguiendo el patrón de capas correcto
 * Proporciona una interfaz SOAP sobre los servicios REST
 * 
 * @author Rosse
 */
@WebService(serviceName = "filaEspera")
@XmlSeeAlso({FilaEsperaDTO.class, FilaEsperaParametros.class})
public class FilaEspera {

    private final FilaEsperaBO filaEsperaBO;

    public FilaEspera() {
        this.filaEsperaBO = new FilaEsperaBO();
    }

    /**
     * Inserta una nueva solicitud en la fila de espera
     * @param filaEspera Datos de la solicitud
     * @return Datos de la solicitud creada
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "insertar")
    public FilaEsperaDTO insertarFilaEspera(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        try {
            if (filaEspera != null && filaEspera.getFechaRegistro() == null) {
                filaEspera.setFechaRegistro(new Date());
            }
            return this.filaEsperaBO.insertar(filaEspera);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al insertar en fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una entrada de fila de espera por fila y usuario
     * @param idFila ID de la fila de espera
     * @param idUsuario ID del usuario
     * @return Datos de la entrada encontrada
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "obtenerPorId")
    public FilaEsperaDTO obtenerFilaEsperaPorId(Integer idFila, Integer idUsuario) throws IOException, InterruptedException {
        try {
            return this.filaEsperaBO.obtenerPorId(idFila, idUsuario);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener entrada de fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Lista entradas de fila de espera según parámetros
     * @param parametros Criterios de búsqueda
     * @return Lista de entradas que coinciden con los criterios
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "listar")
    public List<FilaEsperaDTO> listarFilaEspera(FilaEsperaParametros parametros) throws IOException, InterruptedException {
        try {
            return this.filaEsperaBO.listar(parametros);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Lista entradas pendientes ordenadas por prioridad (VIPs primero)
     * @param localId ID del local (opcional)
     * @return Lista de entradas pendientes ordenadas por prioridad
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "listarPendientesPorPrioridad")
    public List<FilaEsperaDTO> listarPendientesPorPrioridad(Integer localId) throws IOException, InterruptedException {
        try {
            return this.filaEsperaBO.listarPendientesPorPrioridad(localId);
        } catch (Exception e) {
            throw new IOException("Error al listar pendientes por prioridad: " + e.getMessage(), e);
        }
    }

    /**
     * Busca el siguiente compatible en la fila de espera
     * @param localId ID del local
     * @param fechaHora Fecha y hora de disponibilidad
     * @param tipoReserva Tipo de reserva (opcional)
     * @param tipoMesaId ID del tipo de mesa (opcional)
     * @return Siguiente entrada compatible o null si no hay
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "buscarSiguienteCompatible")
    public FilaEsperaDTO buscarSiguienteCompatible(Integer localId, Date fechaHora, 
                                                   TipoReserva tipoReserva, Integer tipoMesaId) 
            throws IOException, InterruptedException {
        try {
            return this.filaEsperaBO.buscarSiguienteCompatible(localId, fechaHora, tipoReserva, tipoMesaId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al buscar siguiente compatible: " + e.getMessage(), e);
        }
    }

    /**
     * Notifica al siguiente usuario compatible en la fila
     * @param localId ID del local
     * @param fechaHora Fecha y hora de disponibilidad
     * @param tipoReserva Tipo de reserva (opcional)
     * @param tipoMesaId ID del tipo de mesa (opcional)
     * @return true si se notificó a alguien, false si no había candidatos
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "notificarSiguiente")
    public boolean notificarSiguiente(Integer localId, Date fechaHora, 
                                     TipoReserva tipoReserva, Integer tipoMesaId) 
            throws IOException, InterruptedException {
        try {
            return this.filaEsperaBO.notificarSiguiente(localId, fechaHora, tipoReserva, tipoMesaId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al notificar siguiente: " + e.getMessage(), e);
        }
    }

    /**
     * Confirma disponibilidad de un usuario notificado
     * @param idFila ID de la fila de espera
     * @param idUsuario ID del usuario
     * @return Resultado de la confirmación (1 si éxito, 0 si falló)
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "confirmarDisponibilidad")
    public Integer confirmarDisponibilidad(Integer idFila, Integer idUsuario) 
            throws IOException, InterruptedException {
        try {
            return this.filaEsperaBO.confirmarDisponibilidad(idFila, idUsuario);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al confirmar disponibilidad: " + e.getMessage(), e);
        }
    }

    /**
     * Modifica una entrada de fila de espera existente
     * @param filaEspera Datos actualizados
     * @return ID de la entrada modificada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "modificar")
    public Integer modificarFilaEspera(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        try {
            if (filaEspera != null) {
                filaEspera.setFechaRegistro(new Date());
            }
            return this.filaEsperaBO.modificar(filaEspera);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al modificar fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Elimina una entrada de fila de espera (eliminación lógica)
     * @param filaEspera Entrada a eliminar
     * @return ID de la entrada eliminada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "eliminar")
    public Integer eliminarFilaEspera(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        try {
            if (filaEspera != null) {
                filaEspera.setFechaRegistro(new Date());
            }
            return this.filaEsperaBO.eliminar(filaEspera);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al eliminar de fila de espera: " + e.getMessage(), e);
        }
    }

    /**
     * Método de conveniencia para verificar si hay usuarios en espera para un local
     * @param localId ID del local
     * @return true si hay usuarios en espera, false caso contrario
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "hayUsuariosEnEspera")
    public boolean hayUsuariosEnEspera(Integer localId) throws IOException, InterruptedException {
        try {
            FilaEsperaParametros params = new FilaEsperaParametros();
            params.setIdLocal(localId);
            List<FilaEsperaDTO> entradas = this.filaEsperaBO.listar(params);
            return entradas != null && !entradas.isEmpty();
        } catch (Exception e) {
            throw new IOException("Error al verificar usuarios en espera: " + e.getMessage(), e);
        }
    }

    /**
     * Método de conveniencia para obtener la cantidad de usuarios en espera
     * @param localId ID del local
     * @return Cantidad de usuarios en espera
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "contarUsuariosEnEspera")
    public int contarUsuariosEnEspera(Integer localId) throws IOException, InterruptedException {
        try {
            FilaEsperaParametros params = new FilaEsperaParametros();
            params.setIdLocal(localId);
            List<FilaEsperaDTO> entradas = this.filaEsperaBO.listar(params);
            return entradas != null ? entradas.size() : 0;
        } catch (Exception e) {
            throw new IOException("Error al contar usuarios en espera: " + e.getMessage(), e);
        }
    }
}
