/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.HorarioxSedeDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.model.HorariosxSedesDTO;
import pe.edu.pucp.softres.model.SedeDTO;

/**
 *
 * @author Rosse
 */
public class HorarioxSedeDAOImpl extends DAOImplBase implements HorarioxSedeDAO{
    
    private HorariosxSedesDTO horarioxsede;
    
    public HorarioxSedeDAOImpl(){
        super("RES_HORARIOS_x_SEDES");
        this.retornarLlavePrimaria = false;
        this.horarioxsede = null;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("HORARIO_ID", TipoDato.ENTERO, true, false));
        this.listaColumnas.add(new Columna("SEDE_ID", TipoDato.ENTERO, true, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.horarioxsede.getIdHorario());
        this.statement.setInt(2, this.horarioxsede.getIdSede());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.horarioxsede.getIdHorario());
        this.statement.setInt(2, this.horarioxsede.getIdSede());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.horarioxsede = new HorariosxSedesDTO();
        this.horarioxsede.setIdHorario(this.resultSet.getInt("HORARIO_ID"));
        this.horarioxsede.setIdSede(this.resultSet.getInt("SEDE_ID"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.horarioxsede = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.horarioxsede);
    }

    @Override
    public Integer insertar(HorariosxSedesDTO horarioxsede) {
        this.horarioxsede = horarioxsede;
        return super.insertar();
    }

    @Override
    public List<HorariosxSedesDTO> listar(Integer idSede) {
        String sql = this.generarSQLParaListar();
        return (ArrayList<HorariosxSedesDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, idSede);
    }
    private String generarSQLParaListar() {
        String sql = "SELECT HORARIO_ID, SEDE_ID FROM res_horarios_x_sedes WHERE SEDE_ID = ? OR ? = -1";
        return sql;
    }
    private void incluirValorDeParametrosParaListar(Object objParametros) {
        Integer idSede = (Integer) objParametros;
        try {
            // 1-2: Estado
            if (idSede != null) {
                this.statement.setInt(1, idSede);
                this.statement.setInt(2, idSede);
            } else {
                this.statement.setNull(1, java.sql.Types.INTEGER);
                this.statement.setNull(2, java.sql.Types.INTEGER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public Integer eliminar(HorariosxSedesDTO horarioxsede) {
        this.horarioxsede = horarioxsede;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM RES_HORARIOS_x_SEDES "
                    + "WHERE HORARIO_ID = ? AND SEDE_ID =? ";
        return sql;
    }
    
}
