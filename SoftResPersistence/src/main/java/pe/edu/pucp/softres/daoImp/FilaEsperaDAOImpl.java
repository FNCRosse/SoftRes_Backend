package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.FilaEsperaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;
import pe.edu.pucp.softres.parametros.FilaEsperaParametrosBuilder;

/**
 *
 * @author frank
 */
public class FilaEsperaDAOImpl extends DAOImplBase implements FilaEsperaDAO {

    private FilaEsperaDTO filaEspera;

    public FilaEsperaDAOImpl() {
        super("RES_FILASESPERA");
        this.retornarLlavePrimaria = true;
        this.filaEspera = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        // nombre,TipoDato,esllavePrimaria,esAutoGenerado;
        this.listaColumnas.add(new Columna("FILA_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", TipoDato.ENTERO, true, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.filaEspera.getUsuario().getIdUsuario());
        this.statement.setString(2, this.filaEspera.getEstado().name());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.filaEspera.getEstado().name());
        this.statement.setInt(2, this.filaEspera.getIdFila());
        this.statement.setInt(3, this.filaEspera.getUsuario().getIdUsuario());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setString(1, this.filaEspera.getEstado().name());
        this.statement.setInt(2, this.filaEspera.getIdFila());
        this.statement.setInt(3, this.filaEspera.getUsuario().getIdUsuario());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.filaEspera.getIdFila());
        this.statement.setInt(2, this.filaEspera.getUsuario().getIdUsuario());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.filaEspera = new FilaEsperaDTO();
        this.filaEspera.setIdFila(this.resultSet.getInt("FILA_ID"));

        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));
        this.filaEspera.setUsuario(usuario);

        String estadoStr = this.resultSet.getString("ESTADO");
        this.filaEspera.setEstado(estadoStr != null ? EstadoFilaEspera.valueOf(estadoStr) : null);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.filaEspera = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.filaEspera);
    }

    @Override
    public Integer insertar(FilaEsperaDTO filaEspera) {
        this.filaEspera = filaEspera;
        return super.insertar();
    }

    @Override
    public FilaEsperaDTO obtenerPorId(Integer idFila, Integer idUsuario) {
        this.filaEspera = new FilaEsperaDTO();
        this.filaEspera.setIdFila(idFila);
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(idUsuario);
        this.filaEspera.setUsuario(usuario);
        super.obtenerPorId(); // usa ambos en WHERE

        return this.filaEspera;
    }

    @Override
    public ArrayList<FilaEsperaDTO> listar(FilaEsperaParametros filaEsperaParametro) {
        Object parametros = new FilaEsperaParametrosBuilder().
                setIdFIla(filaEsperaParametro.getIdFila()).
                setIdUsuario(filaEsperaParametro.getIdUsuario()).
                setEstado(filaEsperaParametro.getEstado()).
                buildFilaEsperaParametros();
        String sql = this.generarSQLParaListar();
        return (ArrayList<FilaEsperaDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT f.FILA_ID, ";
        sql = sql.concat("u.NOMBRE_COMP, ");
        sql = sql.concat("u.USUARIO_ID, ");
        sql = sql.concat("f.ESTADO ");
        sql = sql.concat("FROM RES_FILASESPERA f ");
        sql = sql.concat("INNER JOIN RES_USUARIOS u ON f.usuario_id = u.usuario_id ");
        sql = sql.concat("WHERE (f.ESTADO = ? OR ? IS NULL) AND "); // Estado
        sql = sql.concat("(f.FILA_ID = ? OR ? IS NULL) AND "); // Fila
        sql = sql.concat("(u.USUARIO_ID = ? OR ?  IS NULL) "); // Usuario
        sql = sql.concat(" ORDER BY f.ESTADO DESC, f.FILA_ID ASC");
        return sql;
    }

    @Override
    public Integer modificar(FilaEsperaDTO filaEspera) {
        this.filaEspera = filaEspera;
        return super.modificar();
    }

    @Override
    public Integer eliminar(FilaEsperaDTO filaEspera) {
        this.filaEspera = filaEspera;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "UPDATE RES_FILASESPERA "
                    + "SET ESTADO = ? "
                    + "WHERE FILA_ID = ? AND USUARIO_ID = ?";
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        FilaEsperaParametros parametros = (FilaEsperaParametros) objParametros;
        try {
            // f.ESTADO = ? OR ? = -1
            if (parametros.getEstado() != null) {
                this.statement.setString(1, parametros.getEstado().name());
                this.statement.setString(2, parametros.getEstado().name());
            } else {
                this.statement.setNull(1, java.sql.Types.VARCHAR);
                this.statement.setNull(2, java.sql.Types.VARCHAR);
            }

            // f.FILA_ID = ? OR ? IS NULL
            if (parametros.getIdFila() != null) {
                this.statement.setInt(3, parametros.getIdFila());
                this.statement.setInt(4, parametros.getIdFila());
            } else {
                this.statement.setNull(3, java.sql.Types.INTEGER);
                this.statement.setNull(4, java.sql.Types.INTEGER);
            }

            // u.USUARIO_ID = ? OR ? IS NULL
            if (parametros.getIdUsuario() != null) {
                this.statement.setInt(5, parametros.getIdUsuario());
                this.statement.setInt(6, parametros.getIdUsuario());
            } else {
                this.statement.setNull(5, java.sql.Types.INTEGER);
                this.statement.setNull(6, java.sql.Types.INTEGER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FilaEsperaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
