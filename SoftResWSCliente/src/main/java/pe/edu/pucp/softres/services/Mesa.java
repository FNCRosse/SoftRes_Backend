package pe.edu.pucp.softres.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import pe.edu.pucp.softres.bo.client.MesaBO;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

/**
 * Servicio SOAP para gestión de mesas
 * Usa el cliente REST siguiendo el patrón de capas correcto
 * Proporciona una interfaz SOAP completa sobre los servicios REST de mesas
 * 
 * @author Rosse
 */
@WebService(serviceName = "mesas")
@XmlSeeAlso({MesaDTO.class, MesaParametros.class, EstadoMesa.class})
public class Mesa {

    private final MesaBO mesaBO;

    public Mesa() {
        this.mesaBO = new MesaBO();
    }

    /**
     * Inserta una nueva mesa en el sistema
     * @param mesa Datos de la mesa a insertar
     * @return ID de la mesa creada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "insertar")
    public Integer insertarMesa(MesaDTO mesa) throws IOException, InterruptedException {
        try {
            if (mesa != null && mesa.getFechaCreacion() == null) {
                mesa.setFechaCreacion(new Date());
            }
            MesaDTO result = this.mesaBO.insertar(mesa);
            return result != null && result.getIdMesa() != null ? result.getIdMesa() : 0;
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al insertar mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene una mesa por su ID
     * @param mesaId ID de la mesa a buscar
     * @return Datos de la mesa encontrada
     * @throws IOException Si hay error de comunicación o la mesa no existe
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "obtenerPorId")
    public MesaDTO obtenerMesaPorId(Integer mesaId) throws IOException, InterruptedException {
        try {
            return this.mesaBO.obtenerPorId(mesaId);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al obtener mesa: " + e.getMessage(), e);
        }
    }

    /**
     * Lista mesas según parámetros de búsqueda
     * @param parametros Criterios de búsqueda
     * @return Lista de mesas que coinciden con los criterios
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "listar")
    public List<MesaDTO> listarMesas(MesaParametros parametros) throws IOException, InterruptedException {
        try {
            return this.mesaBO.listar(parametros);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al listar mesas: " + e.getMessage(), e);
        }
    }

    // Los métodos específicos de listado por estado se pueden implementar 
    // usando el método listar con parámetros específicos cuando estén disponibles

    /**
     * Modifica una mesa existente
     * @param mesa Datos actualizados de la mesa
     * @return ID de la mesa modificada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "modificar")
    public Integer modificarMesa(MesaDTO mesa) throws IOException, InterruptedException {
        try {
            if (mesa != null && mesa.getFechaModificacion() == null) {
                mesa.setFechaModificacion(new Date());
            }
            return this.mesaBO.modificar(mesa);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al modificar mesa: " + e.getMessage(), e);
        }
    }

    // Los métodos de cambio de estado se pueden implementar cuando estén disponibles en el BO

    /**
     * Elimina una mesa (eliminación lógica)
     * @param mesa Mesa a eliminar
     * @return ID de la mesa eliminada, 0 si falló
     * @throws IOException Si hay error de comunicación
     * @throws InterruptedException Si la operación es interrumpida
     */
    @WebMethod(operationName = "eliminar")
    public Integer eliminarMesa(MesaDTO mesa) throws IOException, InterruptedException {
        try {
            if (mesa != null && mesa.getFechaModificacion() == null) {
                mesa.setFechaModificacion(new Date());
            }
            return this.mesaBO.eliminar(mesa);
        } catch (IllegalArgumentException e) {
            throw new IOException("Error de validación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IOException("Error al eliminar mesa: " + e.getMessage(), e);
        }
    }

    // Los métodos de conveniencia para capacidad se pueden implementar cuando estén disponibles en el BO
} 