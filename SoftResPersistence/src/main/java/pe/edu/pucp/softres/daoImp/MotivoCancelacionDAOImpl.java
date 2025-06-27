/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.List;
import pe.edu.pucp.softres.dao.MotivoCancelacionDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;

/**
 *
 * @author Rosse
 */
public class MotivoCancelacionDAOImpl extends DAOImplBase implements MotivoCancelacionDAO{
    private MotivosCancelacionDTO mCancelacion;
    
    public MotivoCancelacionDAOImpl() {
        super("RES_MOTIVOS_CANCELACION");
        this.retornarLlavePrimaria = true;
        this.mCancelacion = null;
    }
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MOTIVO_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("DESCRIPCION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.mCancelacion.getDescripcion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.mCancelacion.getDescripcion());
        this.statement.setInt(2, this.mCancelacion.getIdMotivo());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.mCancelacion.getIdMotivo());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.mCancelacion.getIdMotivo());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.mCancelacion = new MotivosCancelacionDTO();
        this.mCancelacion.setIdMotivo(this.resultSet.getInt("MOTIVO_ID"));
        this.mCancelacion.setDescripcion(this.resultSet.getString("DESCRIPCION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.mCancelacion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.mCancelacion);
    }

    @Override
    public Integer insertar(MotivosCancelacionDTO mCancelacion) {
        this.mCancelacion = mCancelacion;
        return super.insertar();
    }

    @Override
    public MotivosCancelacionDTO obtenerPorId(Integer id) {
        this.mCancelacion = new MotivosCancelacionDTO();
        this.mCancelacion.setIdMotivo(id);
        super.obtenerPorId();
        return this.mCancelacion;
    }

    @Override
    public List<MotivosCancelacionDTO> listar() {
        return (List<MotivosCancelacionDTO>) super.listarTodos();

    }

    @Override
    public Integer modificar(MotivosCancelacionDTO mCancelacion) {
        this.mCancelacion = mCancelacion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MotivosCancelacionDTO mCancelacion) {
        this.mCancelacion = mCancelacion;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM RES_MOTIVOS_CANCELACION "
                    + "WHERE MOTIVO_ID = ?";
        return sql;
    }
}
