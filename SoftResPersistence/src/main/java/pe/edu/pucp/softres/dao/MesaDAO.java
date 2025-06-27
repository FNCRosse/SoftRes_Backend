
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;
import java.util.List;

/**
 *
 * @author frank
 */
public interface MesaDAO {
    Integer insertar(MesaDTO mesa);
    MesaDTO obtenerPorId(Integer idMesa);
    List<MesaDTO> listar(MesaParametros parametros);
    Integer modificar(MesaDTO mesa);
    Integer eliminar(MesaDTO mesa);
    List<MesaDTO> asignarMesas(ReservaDTO reserva);
}
