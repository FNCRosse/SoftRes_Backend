
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.SedeDTO;
import java.util.List;
import pe.edu.pucp.softres.parametros.SedeParametros;

/**
 *
 * @author frank
 */
public interface SedeDAO {
    Integer insertar(SedeDTO sede);
    SedeDTO obtenerPorId(Integer idSede);
    List<SedeDTO> listar(SedeParametros sedeparametros);
    Integer modificar(SedeDTO sede);
    Integer eliminar(SedeDTO sede);
}
