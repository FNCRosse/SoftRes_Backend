
package pe.edu.pucp.softres.dao;

import java.util.ArrayList;
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.parametros.NotificacionParametros;

/**
 *
 * @author frank
 */
public interface NotificacionDAO {
    Integer insertar(NotificacionDTO notificacion);
    NotificacionDTO obtenerPorId(Integer idNotificacion,Integer idUsuario);
    ArrayList<NotificacionDTO> listar(NotificacionParametros notificacionParametro);
    Integer modificar(NotificacionDTO notificacion);
    Integer eliminar(NotificacionDTO notificacion);
}
