package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.SedeDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.SedeDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;
import pe.edu.pucp.softres.parametros.SedeParametros;
import pe.edu.pucp.softres.parametros.SedeParametrosBuilder;

/**
 *
 * @author frank
 */
public class SedeDAOImpl extends DAOImplBase implements SedeDAO {

    private SedeDTO sede;

    public SedeDAOImpl() {
        super("RES_SEDES");
        this.retornarLlavePrimaria = true;
        this.sede = null;

    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("SEDE_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("NOMBRE", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("DISTRITO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.BOOLEANO, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.sede.getNombre());
        this.statement.setString(2, this.sede.getDistrito());
        this.statement.setBoolean(3, this.sede.getEstado());
        this.statement.setObject(4, this.sede.getFechaCreacion());
        if (this.sede.getUsuarioCreacion() != null) {
            this.statement.setString(5, this.sede.getUsuarioCreacion());
        } else {
            this.statement.setNull(5, java.sql.Types.VARCHAR);
        }
        if (this.sede.getFechaModificacion() != null) {
            this.statement.setObject(6, this.sede.getFechaModificacion());
        } else {
            this.statement.setNull(6, java.sql.Types.TIMESTAMP);
        }
        if (this.sede.getUsuarioModificacion() != null) {
            this.statement.setString(7, this.sede.getUsuarioModificacion());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.sede.getNombre());
        this.statement.setString(2, this.sede.getDistrito());
        this.statement.setBoolean(3, this.sede.getEstado());
        this.statement.setObject(4, this.sede.getFechaCreacion());
        if (this.sede.getUsuarioCreacion() != null) {
            this.statement.setString(5, this.sede.getUsuarioCreacion());
        } else {
            this.statement.setNull(5, java.sql.Types.VARCHAR);
        }
        if (this.sede.getFechaModificacion() != null) {
            this.statement.setObject(6, this.sede.getFechaModificacion());
        } else {
            this.statement.setNull(6, java.sql.Types.TIMESTAMP);
        }
        if (this.sede.getUsuarioModificacion() != null) {
            this.statement.setString(7, this.sede.getUsuarioModificacion());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }
        this.statement.setInt(8, this.sede.getIdSede());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setBoolean(1, this.sede.getEstado());
        this.statement.setObject(2, this.sede.getFechaModificacion());
        this.statement.setString(3, this.sede.getUsuarioModificacion());
        this.statement.setInt(4, this.sede.getIdSede());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.sede.getIdSede());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.sede = new SedeDTO();
        this.sede.setIdSede(this.resultSet.getInt("SEDE_ID"));
        this.sede.setNombre(this.resultSet.getString("NOMBRE"));
        this.sede.setDistrito(this.resultSet.getString("DISTRITO"));
        this.sede.setEstado(this.resultSet.getBoolean("ESTADO"));
        this.sede.setFechaCreacion(this.resultSet.getTimestamp("FECHA_CREACION"));
        this.sede.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));
        this.sede.setFechaModificacion(this.resultSet.getTimestamp("FECHA_MODIFICACION"));
        this.sede.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }
    
    protected void instanciarObjetoDelResultSetAParaListado() throws SQLException {
        this.sede = new SedeDTO();
        this.sede.setIdSede(this.resultSet.getInt("SEDE_ID"));
        this.sede.setNombre(this.resultSet.getString("NOMBRE"));
        this.sede.setDistrito(this.resultSet.getString("DISTRITO"));
        this.sede.setEstado(this.resultSet.getBoolean("ESTADO"));
        this.sede.setFechaCreacion(this.resultSet.getTimestamp("FECHA_CREACION"));
        this.sede.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));
        this.sede.setFechaModificacion(this.resultSet.getTimestamp("FECHA_MODIFICACION"));
        this.sede.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
        this.sede.setHorarios(this.resultSet.getString("HORARIOS"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.sede = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetAParaListado();
        lista.add(this.sede);
    }

    @Override
    public Integer insertar(SedeDTO sede) {
        this.sede = sede;
        return super.insertar();
    }

    @Override
    public SedeDTO obtenerPorId(Integer id) {
        this.sede = new SedeDTO();
        this.sede.setIdSede(id);
        super.obtenerPorId();
        return this.sede;
    }

    @Override
    public List<SedeDTO> listar(SedeParametros sedeparametros) {
        SedeParametros parametros = new SedeParametrosBuilder()
                .setEstado(sedeparametros.getEstado())
                .setNombre(sedeparametros.getNombre())
                .buildSedeParametros();

        String sql = this.generarSQLParaListar();
        return (List<SedeDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT ";
        sql = sql.concat("s.SEDE_ID, ");
        sql = sql.concat("s.NOMBRE, ");
        sql = sql.concat("s.DISTRITO, ");
        sql = sql.concat("GROUP_CONCAT( ");
        sql = sql.concat("CONCAT(h.DIA_SEMANA, ': ', TIME_FORMAT(h.HORA_INI, '%H:%i'), '-', TIME_FORMAT(h.HORA_FIN, '%H:%i')) ");
        sql = sql.concat("ORDER BY FIELD(h.DIA_SEMANA, 'LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO', 'DOMINGO') ");
        sql = sql.concat("SEPARATOR ' | ') AS HORARIOS, ");
        sql = sql.concat("s.ESTADO, ");
        sql = sql.concat("s.FECHA_CREACION, ");
        sql = sql.concat("s.USUARIO_CREACION, ");
        sql = sql.concat("s.FECHA_MODIFICACION, ");
        sql = sql.concat("s.USUARIO_MODIFICACION ");
        sql = sql.concat("FROM RES_SEDES s ");
        sql = sql.concat("LEFT JOIN RES_HORARIOS_x_SEDES hs ON s.SEDE_ID = hs.SEDE_ID ");
        sql = sql.concat("LEFT JOIN RES_HORARIOS_ATENCION h ON hs.HORARIO_ID = h.HORARIO_ID ");
        sql = sql.concat("WHERE (s.ESTADO = ? OR ? IS NULL) ");
        sql = sql.concat("AND (s.NOMBRE LIKE CONCAT('%', ?, '%') OR ? IS NULL) ");
        sql = sql.concat("GROUP BY s.SEDE_ID ");
        sql = sql.concat("ORDER BY s.ESTADO DESC, s.SEDE_ID ASC, s.NOMBRE ASC");
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        SedeParametros parametros = (SedeParametros) objParametros;
        try {
            // 1-2: Estado
            if (parametros.getEstado() != null) {
                this.statement.setBoolean(1, parametros.getEstado());
                this.statement.setBoolean(2, parametros.getEstado());
            } else {
                this.statement.setNull(1, java.sql.Types.BOOLEAN);
                this.statement.setNull(2, java.sql.Types.BOOLEAN);
            }
            // 3-4: Nombre 
            if (parametros.getNombre() != null && !parametros.getNombre().trim().isEmpty()) {
                String nombre = parametros.getNombre().trim();
                this.statement.setString(3, nombre);
                this.statement.setString(4, nombre);
            } else {
                this.statement.setNull(3, java.sql.Types.VARCHAR);
                this.statement.setNull(4, java.sql.Types.VARCHAR);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LocalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Integer modificar(SedeDTO sede
    ) {
        this.sede = sede;
        return super.modificar();
    }

    @Override
    public Integer eliminar(SedeDTO sede
    ) {
        this.sede = sede;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        return super.generarSQLParaEliminacion();
    }
}
