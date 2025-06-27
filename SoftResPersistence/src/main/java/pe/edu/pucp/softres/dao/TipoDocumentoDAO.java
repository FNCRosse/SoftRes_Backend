
package pe.edu.pucp.softres.dao;

import java.util.List;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;

/**
 *
 * @author frank
 */
public interface TipoDocumentoDAO {
    Integer insertar(TipoDocumentoDTO tipoDocumento);
    TipoDocumentoDTO obtenerPorId(Integer id);
    List<TipoDocumentoDTO> listar();
    Integer modificar(TipoDocumentoDTO tipoDocumento);
    Integer eliminar(TipoDocumentoDTO tipoDocumento);
}
