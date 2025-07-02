/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.ReservaxMesaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.db.DBManager;

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
        String sql = "SELECT RESERVA_ID, MESA_ID FROM RES_RESERVAS_x_MESAS WHERE RESERVA_ID = ? OR ? = -1";
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
            Logger.getLogger(ReservaxMesaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
                + "WHERE RESERVA_ID = ? AND MESA_ID = ?";
        return sql;
    }

    /**
     * Obtiene las mesas asignadas a una reserva con información completa
     */
    public List<MesaDTO> obtenerMesasDeReservaConDetalles(Integer idReserva) {
        List<MesaDTO> mesas = new ArrayList<>();
        
        if (idReserva == null || idReserva <= 0) {
            return mesas;
        }

        String sql = "SELECT m.MESA_ID, m.LOCAL_ID, m.TMESA_ID, m.NUMEROMESA, m.CAPACIDAD, " +
                     "m.ESTADO, m.X, m.Y, m.FECHA_CREACION, m.USUARIO_CREACION, " +
                     "m.FECHA_MODIFICACION, m.USUARIO_MODIFICACION, " +
                     "l.NOMBRE AS NOMBRE_LOCAL, tm.NOMBRE AS NOMBRE_TIPO_MESA " +
                     "FROM RES_RESERVAS_x_MESAS rm " +
                     "INNER JOIN RES_MESAS m ON rm.MESA_ID = m.MESA_ID " +
                     "INNER JOIN RES_LOCALES l ON m.LOCAL_ID = l.LOCAL_ID " +
                     "INNER JOIN RES_TIPOS_MESAS tm ON m.TMESA_ID = tm.TMESA_ID " +
                     "WHERE rm.RESERVA_ID = ?";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idReserva);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MesaDTO mesa = new MesaDTO();
                    mesa.setIdMesa(rs.getInt("MESA_ID"));
                    
                    // Local
                    LocalDTO local = new LocalDTO();
                    local.setIdLocal(rs.getInt("LOCAL_ID"));
                    local.setNombre(rs.getString("NOMBRE_LOCAL"));
                    mesa.setLocal(local);
                    
                    // Tipo de mesa
                    TipoMesaDTO tipoMesa = new TipoMesaDTO();
                    tipoMesa.setIdTipoMesa(rs.getInt("TMESA_ID"));
                    tipoMesa.setNombre(rs.getString("NOMBRE_TIPO_MESA"));
                    mesa.setTipoMesa(tipoMesa);
                    
                    // Datos básicos de la mesa
                    mesa.setNumeroMesa(rs.getString("NUMEROMESA"));
                    mesa.setCapacidad(rs.getInt("CAPACIDAD"));
                    
                    String estadoStr = rs.getString("ESTADO");
                    mesa.setEstado(estadoStr != null ? EstadoMesa.valueOf(estadoStr) : null);
                    
                    int x = rs.getInt("X");
                    mesa.setX(rs.wasNull() ? null : x);
                    
                    int y = rs.getInt("Y");
                    mesa.setY(rs.wasNull() ? null : y);
                    
                    mesa.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
                    mesa.setUsuarioCreacion(rs.getString("USUARIO_CREACION"));
                    mesa.setFechaModificacion(rs.getTimestamp("FECHA_MODIFICACION"));
                    mesa.setUsuarioModificacion(rs.getString("USUARIO_MODIFICACION"));
                    
                    mesas.add(mesa);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(ReservaxMesaDAOImpl.class.getName()).log(Level.SEVERE, 
                "Error obteniendo mesas de reserva " + idReserva, e);
        }

        return mesas;
    }

    /**
     * Cambia el estado de todas las mesas asignadas a una reserva
     */
    public boolean cambiarEstadoMesasDeReserva(Integer idReserva, EstadoMesa nuevoEstado) {
        if (idReserva == null || idReserva <= 0 || nuevoEstado == null) {
            return false;
        }

        String sql = "UPDATE RES_MESAS " +
                     "SET ESTADO = ?, FECHA_MODIFICACION = CURRENT_TIMESTAMP, USUARIO_MODIFICACION = ? " +
                     "WHERE MESA_ID IN (SELECT MESA_ID FROM RES_RESERVAS_x_MESAS WHERE RESERVA_ID = ?)";

        try (Connection conn = DBManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, nuevoEstado.name());
            stmt.setString(2, "sistema_reservas");
            stmt.setInt(3, idReserva);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            Logger.getLogger(ReservaxMesaDAOImpl.class.getName()).log(Level.SEVERE, 
                "Error cambiando estado de mesas para reserva " + idReserva, e);
            return false;
        }
    }

    /**
     * Libera todas las mesas de una reserva (elimina relaciones y cambia estado a DISPONIBLE)
     */
    public boolean liberarMesasDeReserva(Integer idReserva) {
        if (idReserva == null || idReserva <= 0) {
            return false;
        }

        Connection conn = null;
        try {
            conn = DBManager.getInstance().getConnection();
            conn.setAutoCommit(false); // Usar transacción
            
            // 1. Cambiar estado de las mesas a DISPONIBLE
            String sqlCambiarEstado = "UPDATE RES_MESAS " +
                                     "SET ESTADO = 'DISPONIBLE', FECHA_MODIFICACION = CURRENT_TIMESTAMP, " +
                                     "USUARIO_MODIFICACION = 'sistema_liberacion' " +
                                     "WHERE MESA_ID IN (SELECT MESA_ID FROM RES_RESERVAS_x_MESAS WHERE RESERVA_ID = ?)";
            
            try (PreparedStatement stmtEstado = conn.prepareStatement(sqlCambiarEstado)) {
                stmtEstado.setInt(1, idReserva);
                stmtEstado.executeUpdate();
            }
            
            // 2. Eliminar relaciones reserva-mesa
            String sqlEliminarRelaciones = "DELETE FROM RES_RESERVAS_x_MESAS WHERE RESERVA_ID = ?";
            
            try (PreparedStatement stmtEliminar = conn.prepareStatement(sqlEliminarRelaciones)) {
                stmtEliminar.setInt(1, idReserva);
                stmtEliminar.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    Logger.getLogger(ReservaxMesaDAOImpl.class.getName()).log(Level.SEVERE, 
                        "Error en rollback", rollbackEx);
                }
            }
            Logger.getLogger(ReservaxMesaDAOImpl.class.getName()).log(Level.SEVERE, 
                "Error liberando mesas de reserva " + idReserva, e);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    Logger.getLogger(ReservaxMesaDAOImpl.class.getName()).log(Level.SEVERE, 
                        "Error cerrando conexión", e);
                }
            }
        }
    }
}
