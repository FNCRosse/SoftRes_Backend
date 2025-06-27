
package pe.edu.pucp.softres.dao;

import java.util.ArrayList;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 *
 * @author frank
 */
public interface FilaEsperaDAO {
    Integer insertar(FilaEsperaDTO filaEspera);
    FilaEsperaDTO obtenerPorId(Integer idFilaEspera, Integer idUsuario);
    ArrayList<FilaEsperaDTO> listar(FilaEsperaParametros filaEsperaParametros);
    Integer modificar(FilaEsperaDTO filaEspera);
    Integer eliminar(FilaEsperaDTO filaEspera);
}
