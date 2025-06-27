
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;
import java.util.List;

/**
 *
 * @author frank
 */
public interface ReservaDAO {
    Integer insertar(ReservaDTO reserva);
    ReservaDTO obtenerPorId(Integer idReserva);
    List<ReservaDTO> listar(ReservaParametros parametros);
    Integer modificar(ReservaDTO reserva);
    Integer eliminar(ReservaDTO reserva);
    Integer asignarMesas(ReservaDTO reserva);
}
