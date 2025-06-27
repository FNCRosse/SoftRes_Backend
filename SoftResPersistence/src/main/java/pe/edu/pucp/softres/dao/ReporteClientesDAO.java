
package pe.edu.pucp.softres.dao;
import java.util.ArrayList;
import  pe.edu.pucp.softres.model.ReportesClientesDTO;
import pe.edu.pucp.softres.parametros.ReporteClientesParametros;

/**
 *
 * @author frank
 */
public interface ReporteClientesDAO {

    
    public ArrayList<ReportesClientesDTO> listarPorParametros(ReporteClientesParametros parametros);

    public void insertarDatosParaPrueba();

    public void eliminarDatosParaPrueba();

    public void generarReporteClientes(ReporteClientesParametros parametros);
}