
package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.ReporteReservaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.ReportesReservasDTO;
import pe.edu.pucp.softres.parametros.ReporteReservasParametros;
import pe.edu.pucp.softres.parametros.ReporteReservasParametrosBuilder;

public class ReporteReservasDAOImpl extends DAOImplBase implements ReporteReservaDAO {
    
    private ReportesReservasDTO reporteReserva;
    
    public ReporteReservasDAOImpl(){
        super("RES_REPORTE_RESERVAS");
        this.retornarLlavePrimaria = true;
        this.reporteReserva = null;
    }
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("SEDE_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("LOCAL_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TIPO_RESERVA", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("ROL_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TOTAL_RESERVAS", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("CANTIDAD_CANCELACIONES", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("PORC_CANCELADAS", TipoDato.REAL, false, false));
        this.listaColumnas.add(new Columna("PORCENTAJE_OCUPACION", TipoDato.REAL, false, false));
    }
    @Override
    public ArrayList<ReportesReservasDTO> listarPorSede(ReporteReservasParametros reporteReservaParametro){
        Object parametros = new ReporteReservasParametrosBuilder().
                setFechaFin(reporteReservaParametro.getFechaFin()).
                setFechaInicio(reporteReservaParametro.getFechaInicio()).
                setIdRol(reporteReservaParametro.getIdRol()).
                setIdSede(reporteReservaParametro.getIdSede()).
                BuilReporteReservasParametros();
        String sql = this.generarSQLParaListarPorSede();
        return (ArrayList<ReportesReservasDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }
    
    @Override
    public ArrayList<ReportesReservasDTO> listarPorFechas(ReporteReservasParametros reporteReservaParametro){
        Object parametros = new ReporteReservasParametrosBuilder().
                setFechaFin(reporteReservaParametro.getFechaFin()).
                setFechaInicio(reporteReservaParametro.getFechaInicio()).
                setIdRol(reporteReservaParametro.getIdRol()).
                setIdSede(reporteReservaParametro.getIdSede()).
                BuilReporteReservasParametros();
        String sql = this.generarSQLParaListarPorFechas();
        return (ArrayList<ReportesReservasDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }
    
    @Override
    public ArrayList<ReportesReservasDTO> listarPorTipoCliente(ReporteReservasParametros reporteReservaParametro){
        Object parametros = new ReporteReservasParametrosBuilder().
                setFechaFin(reporteReservaParametro.getFechaFin()).
                setFechaInicio(reporteReservaParametro.getFechaInicio()).
                setIdRol(reporteReservaParametro.getIdRol()).
                setIdSede(reporteReservaParametro.getIdSede()).
                BuilReporteReservasParametros();
        String sql = this.generarSQLParaListarPorCliente();
        return (ArrayList<ReportesReservasDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }
    private String generarSQLParaListarPorSede() {
        String sql = "SELECT rr.SEDE_ID, ";
        sql = sql.concat("s.NOMBRE AS NOMBRE_SEDE, ");
        sql = sql.concat("rr.LOCAL_ID, ");
        sql = sql.concat("l.NOMBRE AS NOMBRE_LOCAL, ");
        sql = sql.concat("rr.TIPO_RESERVA, ");
        sql = sql.concat("rr.ROL_ID, ");
        sql = sql.concat("r.NOMBRE AS NOMBRE_ROL, ");
        sql = sql.concat("rr.TOTAL_RESERVAS, ");
        sql = sql.concat("rr.CANTIDAD_CANCELACIONES, ");
        sql = sql.concat("rr.PORC_CANCELADAS, ");
        sql = sql.concat("rr.PORCENTAJE_OCUPACION FROM ");
        sql = sql.concat("res_reporte_reservas rr ");
        sql = sql.concat("INNER JOIN res_sedes s ON rr");
        sql = sql.concat(".SEDE_ID = s.SEDE_ID ");
        sql = sql.concat("INNER JOIN res_locales l ON rr");
        sql = sql.concat(".LOCAL_ID = l.LOCAL_ID ");
        sql = sql.concat("INNER JOIN res_roles r ON rr");
        sql = sql.concat(".ROL_ID = r.ROL_ID ");
        sql = sql.concat("WHERE(rr.SEDE_ID = @sedeId OR @sedeId IS NULL) ");
        sql = sql.concat("ORDER BY ");
        sql = sql.concat("rr.SEDE_ID ASC, rr.LOCAL_ID ASC;");
        return sql;
    }
    private String generarSQLParaListarPorFechas() {
        String sql = "SELECT s.SEDE_ID, s.NOMBRE AS NOMBRE_SEDE, l.LOCAL_ID, l.NOMBRE AS NOMBRE_LOCAL, COUNT(*) AS TOTAL_RESERVAS, SUM(CASE WHEN r.MOTIVO_CANCELACION_ID IS NOT NULL THEN 1 ELSE 0 END) AS CANTIDAD_CANCELACIONES, ROUND(SUM(CASE WHEN r.MOTIVO_CANCELACION_ID IS NOT NULL THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS PORC_CANCELADAS FROM res_reservas r INNER JOIN res_locales l ON r.LOCAL_ID = l.LOCAL_ID INNER JOIN res_sedes s ON l.SEDE_ID = s.SEDE_ID WHERE r.FECHA_HORA_REGISTRO BETWEEN @fechaInicio AND @fechaFin GROUP BY s.SEDE_ID, s.NOMBRE, l.LOCAL_ID, l.NOMBRE ORDER BY s.SEDE_ID, l.LOCAL_ID;";
        return sql;
    }
    private String generarSQLParaListarPorCliente() {
        String sql = "SELECT u.USUARIO_ID, u.NOMBRE_COMP AS NOMBRE_CLIENTE, COUNT(r.RESERVA_ID) AS TOTAL_RESERVAS, SUM(CASE WHEN r.MOTIVO_CANCELACION_ID IS NOT NULL THEN 1 ELSE 0 END) AS CANTIDAD_CANCELACIONES, ROUND(SUM(CASE WHEN r.MOTIVO_CANCELACION_ID IS NOT NULL THEN 1 ELSE 0 END) * 100.0 / COUNT(r.RESERVA_ID), 2) AS PORC_CANCELADAS FROM res_reservas r INNER JOIN res_usuarios u ON r.USUARIO_CREACION = u.USUARIO_ID WHERE (@usuarioId IS NULL OR u.USUARIO_ID = @usuarioId) GROUP BY u.USUARIO_ID, u.NOMBRE_COMP ORDER BY TOTAL_RESERVAS DESC;";
        return sql;
    }
    @Override
    public Integer insertarDatosParaPrueba(ReportesReservasDTO reporteReserva){
        this.reporteReserva = reporteReserva;
        return super.insertar();
    }
    
    @Override
    public Integer eliminarDatosParaPrueba(ReportesReservasDTO reporteReserva){
        this.reporteReserva = reporteReserva;
        return super.eliminar();
    }
    
//    @Override
//    public Integer generarReporteReserva(){
//        
//    }
    
    private void incluirValorDeParametrosParaListar(Object objParametros) {
        ReporteReservasParametros parametros = (ReporteReservasParametros) objParametros;
        try {
            if (parametros.getIdSede()!= null) {
                this.statement.setInt(1, parametros.getIdSede());
                this.statement.setInt(2, parametros.getIdSede());
            } else {
                this.statement.setNull(1, java.sql.Types.VARCHAR);
                this.statement.setNull(2, java.sql.Types.VARCHAR);
            }
            if (parametros.getIdRol()!= null) {
                this.statement.setInt(3, parametros.getIdRol());
                this.statement.setInt(4, parametros.getIdRol());
            } else {
                this.statement.setNull(3, java.sql.Types.INTEGER);
                this.statement.setNull(4, java.sql.Types.INTEGER);
            }
            if (parametros.getFechaInicio() != null && parametros.getFechaFin() != null) {
                this.statement.setObject(5, parametros.getFechaInicio());
                this.statement.setObject(6, parametros.getFechaFin());
                this.statement.setObject(7, parametros.getFechaInicio());
                this.statement.setObject(8, parametros.getFechaFin());
            } else {
                this.statement.setNull(5, java.sql.Types.TIMESTAMP);
                this.statement.setNull(6, java.sql.Types.TIMESTAMP);
                this.statement.setNull(7, java.sql.Types.TIMESTAMP);
                this.statement.setNull(8, java.sql.Types.TIMESTAMP);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
}
