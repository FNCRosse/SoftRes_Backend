
package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.ReporteClientesDAO;
import pe.edu.pucp.softres.model.ReportesClientesDTO;
import pe.edu.pucp.softres.parametros.ReporteClientesParametros;

public class ReporteClientesDAOImpl extends DAOImplBase implements ReporteClientesDAO {

    public ReporteClientesDAOImpl() {
        super("VEN_REPORTES_CLIENTES");
    }

    @Override
    public void configurarListaDeColumnas() {
        
    }

    @Override
    public void insertarDatosParaPrueba() {
        String sql = "{CALL SP_VEN_INSERTAR_DATOS_PRUEBA_REPORTE_CLIENTES()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void eliminarDatosParaPrueba() {
        String sql = "{CALL SP_VEN_ELIMINAR_DATOS_PRUEBA_REPORTE_CLIENTES()}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, conTransaccion);
    }

    @Override
    public void generarReporteClientes(ReporteClientesParametros parametros) {
        String sql = "{CALL SP_VEN_GENERAR_REPORTE_CLIENTES(?, ?, ?, ?)}";
        Boolean conTransaccion = true;
        this.ejecutarProcedimientoAlmacenado(sql, this::incluirValoresParaGenerarReporteClientes, parametros, conTransaccion);
    }

    private void incluirValoresParaGenerarReporteClientes(Object objetoParametros) {
        ReporteClientesParametros parametros = (ReporteClientesParametros) objetoParametros;
        try {
            this.statement.setObject(1, parametros.getFechaInicio());
            this.statement.setObject(2, parametros.getFechaFin());
            this.statement.setInt(3, parametros.getIdLocal());
            this.statement.setInt(4, parametros.getIdRol());
        } catch (SQLException ex) {
            Logger.getLogger(ReporteClientesDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public ArrayList<ReportesClientesDTO> listarPorParametros(ReporteClientesParametros parametros) {
        throw new UnsupportedOperationException("Not supported yet."); // Método aún no implementado
    }
}
