/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.ReservaxMesaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 *
 * @author Rosse
 */
public class ReservaxMesaDAOImpl extends DAOImplBase implements ReservaxMesaDAO {

    private ReservaxMesasDTO reservaxmesas;

    public ReservaxMesaDAOImpl() {
        super("RES_RESERVAS_x_MESAS");
        this.retornarLlavePrimaria = false;
        this.reservaxmesas = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("RESERVA_ID", TipoDato.ENTERO, true, false));
        this.listaColumnas.add(new Columna("MESA_ID", TipoDato.ENTERO, true, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.reservaxmesas.getReserva().getIdReserva());
        this.statement.setInt(2, this.reservaxmesas.getMesa().getIdMesa());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.reservaxmesas.getReserva().getIdReserva());
        this.statement.setInt(2, this.reservaxmesas.getMesa().getIdMesa());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        ReservaDTO reserva = new ReservaDTO();
        reserva.setIdReserva(this.resultSet.getInt("RESERVA_ID"));
        MesaDTO mesa = new MesaDTO();
        mesa.setIdMesa(this.resultSet.getInt("MESA_ID"));
        this.reservaxmesas = new ReservaxMesasDTO();
        this.reservaxmesas.setReserva(reserva);
        this.reservaxmesas.setMesa(mesa);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.reservaxmesas = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.reservaxmesas);
    }

    @Override
    public Integer insertar(ReservaxMesasDTO reservaxmesas) {
        this.reservaxmesas = reservaxmesas;
        return super.insertar();
    }

    @Override
    public List<ReservaxMesasDTO> listar(Integer idReserva) {
        String sql = this.generarSQLParaListar();
        return (List<ReservaxMesasDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, idReserva);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT RESERVA_ID, MESA_ID FROM res_reservas_x_mesas WHERE RESERVA_ID = ? OR ? = -1";
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        Integer idReserva = (Integer) objParametros;
        try {
            // 1-2: 
            if (idReserva != null) {
                this.statement.setInt(1, idReserva);
                this.statement.setInt(2, idReserva);
            } else {
                this.statement.setNull(1, java.sql.Types.INTEGER);
                this.statement.setNull(2, java.sql.Types.INTEGER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Integer eliminar(ReservaxMesasDTO reservaxmesas) {
        this.reservaxmesas = reservaxmesas;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "DELETE FROM RES_RESERVAS_x_MESAS "
                + "WHERE RESERVA_ID = ? AND MESA_ID =? ";
        return sql;
    }

}
