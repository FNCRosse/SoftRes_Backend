
package pe.edu.pucp.softres.daoImp;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.TipoMesaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.db.DBManager;
import pe.edu.pucp.softres.parametros.TipoMesaParametros;
import pe.edu.pucp.softres.parametros.TipoMesaParametrosBuilder;
/**
 *
 * @author frank
 */
public class TipoMesaDAOImpl extends DAOImplBase implements TipoMesaDAO{
    
    private TipoMesaDTO tipoMesa;

    public TipoMesaDAOImpl() {
        super("RES_TIPOS_MESAS");
        this.retornarLlavePrimaria = true;
        this.tipoMesa = null;

    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TMESA_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("NOMBRE", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        if(this.tipoMesa.getNombre()!= null){
            this.statement.setString(1, this.tipoMesa.getNombre());
        } else {
            this.statement.setNull(1, java.sql.Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        if(this.tipoMesa.getNombre()!= null){
            this.statement.setString(1, this.tipoMesa.getNombre());
        } else {
            this.statement.setNull(1, java.sql.Types.VARCHAR);
        }
        this.statement.setInt(2, this.tipoMesa.getIdTipoMesa());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoMesa.getIdTipoMesa());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoMesa.getIdTipoMesa());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoMesa = new TipoMesaDTO();
        this.tipoMesa.setIdTipoMesa(this.resultSet.getInt("TMESA_ID"));
        this.tipoMesa.setNombre(this.resultSet.getString("NOMBRE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoMesa = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoMesa);
    }

    @Override
    public Integer insertar(TipoMesaDTO usuario) {
        this.tipoMesa = usuario;
        return super.insertar();
    }

    @Override
    public TipoMesaDTO obtenerPorId(Integer id) {
        this.tipoMesa = new TipoMesaDTO();
        this.tipoMesa.setIdTipoMesa(id);
        super.obtenerPorId();
        return this.tipoMesa;
    }

    @Override
    public List<TipoMesaDTO> listar() {
        return (List<TipoMesaDTO>) super.listarTodos();
    }

    
    @Override
    public Integer modificar(TipoMesaDTO tipoMesa) {
        this.tipoMesa = tipoMesa;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoMesaDTO tipoMesa) {
        this.tipoMesa = tipoMesa;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM RES_TIPOS_MESAS " +
             "WHERE TMESA_ID = ?";
        return sql;
    }
}
