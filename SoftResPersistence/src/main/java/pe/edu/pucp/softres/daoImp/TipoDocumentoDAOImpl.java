package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softres.dao.TipoDocumentoDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;

/**
 *
 * @author frank
 */
public class TipoDocumentoDAOImpl extends DAOImplBase implements TipoDocumentoDAO {

    private TipoDocumentoDTO tipoDocumento;

    public TipoDocumentoDAOImpl() {
        super("RES_TIPO_DOCUMENTOS");
        this.retornarLlavePrimaria = true;
        this.tipoDocumento = null;

    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_DOC_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("NOMBRE", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoDocumento.getNombre());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoDocumento.getNombre());
        this.statement.setInt(2, this.tipoDocumento.getIdTipoDocumento());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoDocumento.getIdTipoDocumento());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoDocumento.getIdTipoDocumento());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoDocumento = new TipoDocumentoDTO();
        this.tipoDocumento.setIdTipoDocumento(this.resultSet.getInt("TIPO_DOC_ID"));
        this.tipoDocumento.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoDocumento = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoDocumento);
    }

    @Override
    public Integer insertar(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return super.insertar();
    }

    @Override
    public TipoDocumentoDTO obtenerPorId(Integer id) {
        this.tipoDocumento = new TipoDocumentoDTO();
        this.tipoDocumento.setIdTipoDocumento(id);
        super.obtenerPorId();
        return this.tipoDocumento;
    }

    @Override
    public List<TipoDocumentoDTO> listar() {
        return (List<TipoDocumentoDTO>) super.listarTodos();

    }

    @Override
    public Integer modificar(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoDocumentoDTO tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM RES_TIPO_DOCUMENTOS "
                    + "WHERE TIPO_DOC_ID = ?";
        return sql;
    }
}
