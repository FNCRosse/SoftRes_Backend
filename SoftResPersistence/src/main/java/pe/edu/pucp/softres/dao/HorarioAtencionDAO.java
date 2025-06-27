
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.parametros.HorarioParametros;
import java.util.List;

/**
 *
 * @author frank
 */
public interface HorarioAtencionDAO {
    Integer insertar(HorarioAtencionDTO horario);
    HorarioAtencionDTO obtenerPorId(Integer idHorario);
    List<HorarioAtencionDTO> listar(HorarioParametros parametros);
    Integer modificar(HorarioAtencionDTO horario);
    Integer eliminar(HorarioAtencionDTO horario);
}
