
package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softres.dao.RolDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.RolDTO;
/**
 *
 * @author frank
 */
public class RolDAOImpl extends DAOImplBase implements RolDAO{
    
    private RolDTO rol;
    
    public RolDAOImpl(){
        super("RES_ROLES");
        this.retornarLlavePrimaria = true;
        this.rol = null;
        
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("ROL_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("NOMBRE", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("ES_CLIENTE", TipoDato.BOOLEANO, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.rol.getNombre());
        this.statement.setBoolean(2, this.rol.getEsCliente());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.rol.getNombre());
        this.statement.setBoolean(2, this.rol.getEsCliente());
        this.statement.setInt(3, this.rol.getIdRol());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.rol.getIdRol());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.rol.getIdRol());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.rol = new RolDTO();
        this.rol.setIdRol(this.resultSet.getInt("ROL_ID"));
        this.rol.setNombre(this.resultSet.getString("NOMBRE"));
        this.rol.setEsCliente(this.resultSet.getBoolean("ES_CLIENTE"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.rol = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.rol);
    }
    
    @Override
    public Integer insertar(RolDTO rol) {
        this.rol = rol;
        return super.insertar();
    }

    @Override
    public RolDTO obtenerPorId(Integer id) {
        this.rol = new RolDTO();
        this.rol.setIdRol(id);
        super.obtenerPorId();
        return this.rol;
    }

    @Override
    public ArrayList<RolDTO> listar() {
        return (ArrayList<RolDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(RolDTO rol) {
        this.rol = rol;
        return super.modificar();
    }

    @Override
    public Integer eliminar(RolDTO rol) {
        this.rol = rol;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM RES_ROLES " +
             "WHERE ROL_ID = ?";
        return sql;
    }
}
