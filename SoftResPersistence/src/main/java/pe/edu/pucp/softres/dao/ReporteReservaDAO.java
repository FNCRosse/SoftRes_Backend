
package pe.edu.pucp.softres.dao;

import java.util.ArrayList;
import pe.edu.pucp.softres.model.ReportesReservasDTO;
import pe.edu.pucp.softres.parametros.ReporteReservasParametros;


public interface ReporteReservaDAO {
    ArrayList<ReportesReservasDTO> listarPorSede(ReporteReservasParametros reporteReservaParametro);
    ArrayList<ReportesReservasDTO> listarPorFechas(ReporteReservasParametros reporteReservaParametro);
    ArrayList<ReportesReservasDTO> listarPorTipoCliente(ReporteReservasParametros reporteReservaParametro);
    Integer insertarDatosParaPrueba(ReportesReservasDTO reporteReserva);
    Integer eliminarDatosParaPrueba(ReportesReservasDTO reporteReserva);
}
