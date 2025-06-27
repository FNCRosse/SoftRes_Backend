package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.LocalDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;
import pe.edu.pucp.softres.model.SedeDTO;
import pe.edu.pucp.softres.parametros.LocalParametrosBuilder;

/**
 *
 * @author frank
 */
public class LocalDAOImpl extends DAOImplBase implements LocalDAO {

    private LocalDTO local;

    public LocalDAOImpl() {
        super("RES_LOCALES");
        this.retornarLlavePrimaria = true;
        this.local = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("LOCAL_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("SEDE_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("NOMBRE", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("DIRECCION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("TELEFONO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.BOOLEANO, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.local.getSede().getIdSede());
        this.statement.setString(2, this.local.getNombre());
        this.statement.setString(3, this.local.getDireccion());
        this.statement.setString(4, this.local.getTelefono());
        this.statement.setBoolean(5, this.local.getEstado());

        this.statement.setObject(6, this.local.getFechaCreacion());

        if (this.local.getUsuarioCreacion() != null) {
            this.statement.setString(7, this.local.getUsuarioCreacion());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }

        if (this.local.getFechaModificacion() != null) {
            this.statement.setObject(8, this.local.getFechaModificacion());
        } else {
            this.statement.setNull(8, java.sql.Types.TIMESTAMP);
        }

        if (this.local.getUsuarioModificacion() != null) {
            this.statement.setString(9, this.local.getUsuarioModificacion());
        } else {
            this.statement.setNull(9, java.sql.Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.local.getSede().getIdSede());
        this.statement.setString(2, this.local.getNombre());
        this.statement.setString(3, this.local.getDireccion());
        this.statement.setString(4, this.local.getTelefono());
        this.statement.setBoolean(5, this.local.getEstado());

        this.statement.setObject(6, this.local.getFechaCreacion());

        if (this.local.getUsuarioCreacion() != null) {
            this.statement.setString(7, this.local.getUsuarioCreacion());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }

        if (this.local.getFechaModificacion() != null) {
            this.statement.setObject(8,this.local.getFechaModificacion());
        } else {
            this.statement.setNull(8, java.sql.Types.TIMESTAMP);
        }

        if (this.local.getUsuarioModificacion() != null) {
            this.statement.setString(9, this.local.getUsuarioModificacion());
        } else {
            this.statement.setNull(9, java.sql.Types.VARCHAR);
        }

        this.statement.setInt(10, this.local.getIdLocal()); // WHERE LOCAL_ID = ?
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setBoolean(1, local.getEstado());
        this.statement.setObject(2, local.getFechaModificacion());
        this.statement.setString(3, local.getUsuarioModificacion());
        this.statement.setInt(4, this.local.getIdLocal());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.local.getIdLocal());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.local = new LocalDTO();

        this.local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));

        SedeDTO sede = new SedeDTO();
        sede.setIdSede(this.resultSet.getInt("SEDE_ID"));
        this.local.setSede(sede);

        this.local.setNombre(this.resultSet.getString("NOMBRE"));
        this.local.setDireccion(this.resultSet.getString("DIRECCION"));
        this.local.setTelefono(this.resultSet.getString("TELEFONO"));
        this.local.setEstado(this.resultSet.getBoolean("ESTADO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.local.setFechaCreacion(tsCreacion);
        this.local.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));
        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.local.setFechaModificacion(tsModificacion);
        this.local.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    protected void instanciarObjetoDelResultSetParaListado() throws SQLException {
        this.local = new LocalDTO();

        this.local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));

        SedeDTO sede = new SedeDTO();
        sede.setIdSede(this.resultSet.getInt("SEDE_ID"));
        sede.setNombre(this.resultSet.getString("NOMBRE_SEDE"));
        this.local.setSede(sede);

        this.local.setNombre(this.resultSet.getString("NOMBRE"));
        this.local.setDireccion(this.resultSet.getString("DIRECCION"));
        this.local.setTelefono(this.resultSet.getString("TELEFONO"));
        this.local.setCantidadMesas(this.resultSet.getInt("CANTIDAD_MESAS"));
        this.local.setEstado(this.resultSet.getBoolean("ESTADO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.local.setFechaCreacion(tsCreacion);
        this.local.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));
        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.local.setFechaModificacion(tsModificacion);
        this.local.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.local = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetParaListado();
        lista.add(this.local);
    }

    @Override
    public Integer insertar(LocalDTO local) {
        this.local = local;
        return super.insertar();
    }

    @Override
    public LocalDTO obtenerPorId(Integer id) {
        this.local = new LocalDTO();
        this.local.setIdLocal(id);
        super.obtenerPorId();
        return this.local;
    }

    @Override
    public ArrayList<LocalDTO> listar(LocalParametros localParametro) {
        LocalParametros parametros = new LocalParametrosBuilder()
                .setIdSede(localParametro.getIdSede())
                .setEstado(localParametro.getEstado())
                .setNombre(localParametro.getNombre())
                .buildLocalParametros();

        String sql = this.generarSQLParaListar();
        return (ArrayList<LocalDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT "
                + "l.LOCAL_ID, "
                + "l.NOMBRE, "
                + "l.DIRECCION, "
                + "l.SEDE_ID, "
                + "s.NOMBRE AS NOMBRE_SEDE, "
                + "l.TELEFONO, "
                + "COUNT(m.MESA_ID) AS CANTIDAD_MESAS, "
                + "l.FECHA_CREACION, "
                + "l.USUARIO_CREACION, "
                + "l.FECHA_MODIFICACION, "
                + "l.USUARIO_MODIFICACION, "
                + "l.ESTADO "
                + "FROM RES_LOCALES l "
                + "INNER JOIN RES_SEDES s ON s.SEDE_ID = l.SEDE_ID "
                + "LEFT JOIN RES_MESAS m ON m.LOCAL_ID = l.LOCAL_ID "
                + "WHERE (? IS NULL OR l.ESTADO = ?) "
                + "AND (? IS NULL OR l.SEDE_ID = ?) "
                + "AND (? IS NULL OR l.NOMBRE LIKE CONCAT('%', ?, '%')) "
                + "GROUP BY l.LOCAL_ID, l.NOMBRE, l.DIRECCION, l.TELEFONO, l.ESTADO, "
                + "l.SEDE_ID, s.NOMBRE, l.FECHA_CREACION, l.USUARIO_CREACION, "
                + "l.FECHA_MODIFICACION, l.USUARIO_MODIFICACION "
                + "ORDER BY l.ESTADO DESC, l.SEDE_ID ASC, l.NOMBRE ASC";
        return sql;
    }

    @Override
    public Integer modificar(LocalDTO local) {
        this.local = local;
        return super.modificar();
    }

    @Override
    public Integer eliminar(LocalDTO local) {
        this.local = local;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        return super.generarSQLParaEliminacion();
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        LocalParametros parametros = (LocalParametros) objParametros;
        try {
            // 1-2: Estado
            if (parametros.getEstado() != null) {
                this.statement.setBoolean(1, parametros.getEstado());
                this.statement.setBoolean(2, parametros.getEstado());
            } else {
                this.statement.setNull(1, java.sql.Types.BOOLEAN);
                this.statement.setNull(2, java.sql.Types.BOOLEAN);
            }

            // 3-4: ID de sede
            if (parametros.getIdSede() != null) {
                this.statement.setInt(3, parametros.getIdSede());
                this.statement.setInt(4, parametros.getIdSede());
            } else {
                this.statement.setNull(3, java.sql.Types.INTEGER);
                this.statement.setNull(4, java.sql.Types.INTEGER);
            }
            // 5-6: Nombre del local 
            if (parametros.getNombre() != null && !parametros.getNombre().trim().isEmpty()) {
                String nombre = parametros.getNombre().trim();
                this.statement.setString(5, nombre);
                this.statement.setString(6, nombre);
            } else {
                this.statement.setNull(5, java.sql.Types.VARCHAR);
                this.statement.setNull(6, java.sql.Types.VARCHAR);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LocalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
