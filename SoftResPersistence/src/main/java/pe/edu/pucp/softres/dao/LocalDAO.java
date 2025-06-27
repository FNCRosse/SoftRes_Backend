
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;
import java.util.List;

/**
 *
 * @author frank
 */
public interface LocalDAO {
    Integer insertar(LocalDTO local);
    LocalDTO obtenerPorId(Integer idLocal);
    List<LocalDTO> listar(LocalParametros parametros);
    Integer modificar(LocalDTO local);
    Integer eliminar(LocalDTO local);
}
