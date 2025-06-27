
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;
import java.util.List;

/**
 *
 * @author frank
 */
public interface ComentarioDAO {
    Integer insertar(ComentariosDTO comentario);
    ComentariosDTO obtenerPorId(Integer id);
    List<ComentariosDTO> listar(ComentarioParametros parametros);
    Integer modificar(ComentariosDTO comentario);
    Integer eliminar(ComentariosDTO comentarioId);
}
