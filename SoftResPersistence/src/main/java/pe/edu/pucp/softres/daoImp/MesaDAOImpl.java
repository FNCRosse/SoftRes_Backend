package pe.edu.pucp.softres.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import pe.edu.pucp.softres.dao.MesaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametrosBuilder;
import pe.edu.pucp.softres.db.DBManager;

/**
 *
 * @author frank
 */
public class MesaDAOImpl extends DAOImplBase implements MesaDAO {

    private MesaDTO mesa;
    private DataSource dataSource;

    public MesaDAOImpl() {
        super("RES_MESAS");
        this.retornarLlavePrimaria = true;
        this.mesa = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MESA_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("LOCAL_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TMESA_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("NUMEROMESA", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("CAPACIDAD", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.CADENA, false, false)); // ENUM as string
        this.listaColumnas.add(new Columna("X", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("Y", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.mesa.getLocal().getIdLocal());
        this.statement.setInt(2, this.mesa.getTipoMesa().getIdTipoMesa());
        this.statement.setString(3, this.mesa.getNumeroMesa());
        this.statement.setInt(4, this.mesa.getCapacidad());
        this.statement.setString(5, this.mesa.getEstado().name());

        if (this.mesa.getX() != null) {
            this.statement.setInt(6, this.mesa.getX());
        } else {
            this.statement.setNull(6, java.sql.Types.INTEGER);
        }

        if (this.mesa.getY() != null) {
            this.statement.setInt(7, this.mesa.getY());
        } else {
            this.statement.setNull(7, java.sql.Types.INTEGER);
        }

        if (mesa.getFechaCreacion() != null) {
            this.statement.setObject(8,mesa.getFechaCreacion());
        } else {
            this.statement.setNull(8, java.sql.Types.TIMESTAMP);
        }

        if (this.mesa.getUsuarioCreacion() != null) {
            this.statement.setString(9, this.mesa.getUsuarioCreacion());
        } else {
            this.statement.setNull(9, java.sql.Types.VARCHAR);
        }

        if (this.mesa.getFechaModificacion() != null) {
            this.statement.setObject(10,this.mesa.getFechaModificacion());
        } else {
            this.statement.setNull(10, java.sql.Types.TIMESTAMP);
        }

        if (this.mesa.getUsuarioModificacion() != null) {
            this.statement.setString(11, this.mesa.getUsuarioModificacion());
        } else {
            this.statement.setNull(11, java.sql.Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        incluirValorDeParametrosParaInsercion();
        this.statement.setInt(12, this.mesa.getIdMesa());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setString(1, mesa.getEstado().toString());
        this.statement.setObject(2, mesa.getFechaModificacion());
        this.statement.setString(3, mesa.getUsuarioModificacion());
        this.statement.setInt(4, this.mesa.getIdMesa());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.mesa.getIdMesa());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.mesa = new MesaDTO();

        this.mesa.setIdMesa(this.resultSet.getInt("MESA_ID"));

        // Crear y asignar LocalDTO
        LocalDTO local = new LocalDTO();
        local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));
        this.mesa.setLocal(local);

        // Crear y asignar TipoMesaDTO
        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(this.resultSet.getInt("TMESA_ID"));
        this.mesa.setTipoMesa(tipoMesa);

        this.mesa.setNumeroMesa(this.resultSet.getString("NUMEROMESA"));
        this.mesa.setCapacidad(this.resultSet.getInt("CAPACIDAD"));

        String estadoStr = this.resultSet.getString("ESTADO");
        this.mesa.setEstado(estadoStr != null ? EstadoMesa.valueOf(estadoStr) : null);

        int x = this.resultSet.getInt("X");
        this.mesa.setX(this.resultSet.wasNull() ? null : x);

        int y = this.resultSet.getInt("Y");
        this.mesa.setY(this.resultSet.wasNull() ? null : y);

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.mesa.setFechaCreacion(tsCreacion);

        this.mesa.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.mesa.setFechaModificacion(tsModificacion);

        this.mesa.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }
    
    protected void instanciarObjetoDelResultSetParaListado() throws SQLException {
        this.mesa = new MesaDTO();

        this.mesa.setIdMesa(this.resultSet.getInt("MESA_ID"));

        // Crear y asignar LocalDTO
        LocalDTO local = new LocalDTO();
        local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));
        local.setNombre(this.resultSet.getString("NOMBRE_LOCAL"));
        this.mesa.setLocal(local);

        // Crear y asignar TipoMesaDTO
        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(this.resultSet.getInt("TMESA_ID"));
        tipoMesa.setNombre(this.resultSet.getString("NOMBRE_TIPO_MESA"));
        this.mesa.setTipoMesa(tipoMesa);

        this.mesa.setNumeroMesa(this.resultSet.getString("NUMEROMESA"));
        this.mesa.setCapacidad(this.resultSet.getInt("CAPACIDAD"));

        String estadoStr = this.resultSet.getString("ESTADO");
        this.mesa.setEstado(estadoStr != null ? EstadoMesa.valueOf(estadoStr) : null);

        int x = this.resultSet.getInt("X");
        this.mesa.setX(this.resultSet.wasNull() ? null : x);

        int y = this.resultSet.getInt("Y");
        this.mesa.setY(this.resultSet.wasNull() ? null : y);

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.mesa.setFechaCreacion(tsCreacion);

        this.mesa.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.mesa.setFechaModificacion(tsModificacion);

        this.mesa.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.mesa = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetParaListado();
        lista.add(this.mesa);
    }

    @Override
    public Integer insertar(MesaDTO mesa) {
        this.mesa = mesa;
        return super.insertar();
    }

    @Override
    public MesaDTO obtenerPorId(Integer id) {
        this.mesa = new MesaDTO();
        this.mesa.setIdMesa(id);
        super.obtenerPorId();
        return this.mesa;
    }

    @Override
    public Integer modificar(MesaDTO mesa) {
        this.mesa = mesa;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MesaDTO mesa) {
        this.mesa = mesa;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        return super.generarSQLParaEliminacion();
    }

    @Override
    public List<MesaDTO> listar(MesaParametros mesaparametros) {
        MesaParametros parametros = new MesaParametrosBuilder()
                .setEstado(mesaparametros.getEstado())
                .setIdLocal(mesaparametros.getIdLocal())
                .setIdTipoMesa(mesaparametros.getIdTipoMesa())
                .setNombre(mesaparametros.getNombre())
                .buildMesaParametros();

        String sql = this.generarSQLParaListar();
        return (List<MesaDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }
    
    private void incluirValorDeParametrosParaListar(Object objParametros) {
        MesaParametros parametros = (MesaParametros) objParametros;
        try {
            // 1-2: Estado
            if (parametros.getEstado() != null) {
                this.statement.setString(1, parametros.getEstado().toString());
                this.statement.setString(2, parametros.getEstado().toString());
            } else {
                this.statement.setNull(1, java.sql.Types.BOOLEAN);
                this.statement.setNull(2, java.sql.Types.BOOLEAN);
            }

            // 3-4: ID de local
            if (parametros.getIdLocal()!= null) {
                this.statement.setInt(3, parametros.getIdLocal());
                this.statement.setInt(4, parametros.getIdLocal());
            } else {
                this.statement.setNull(3, java.sql.Types.INTEGER);
                this.statement.setNull(4, java.sql.Types.INTEGER);
            }
            // 5-6: id de tipo de mesa
            if (parametros.getIdTipoMesa()!= null) {
                this.statement.setInt(5, parametros.getIdTipoMesa());
                this.statement.setInt(6, parametros.getIdTipoMesa());
            } else {
                this.statement.setNull(5, java.sql.Types.INTEGER);
                this.statement.setNull(6, java.sql.Types.INTEGER);
            }
            // 7-8: nombre de mesa
            if (parametros.getNombre()!= null && !parametros.getNombre().trim().isEmpty()) {
                String nombre = parametros.getNombre().trim();
                this.statement.setString(7, nombre);
                this.statement.setString(8, nombre);
            } else {
                this.statement.setNull(7, java.sql.Types.VARCHAR);
                this.statement.setNull(8, java.sql.Types.VARCHAR);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LocalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String generarSQLParaListar() {
        String sql = "SELECT "
                + "m.MESA_ID, "
                + "m.NUMEROMESA, "
                + "m.CAPACIDAD, "
                + "m.ESTADO, "
                + "m.X, "
                + "m.Y, "
                + "m.FECHA_CREACION, "
                + "m.USUARIO_CREACION, "
                + "m.FECHA_MODIFICACION, "
                + "m.USUARIO_MODIFICACION, "
                + "tm.TMESA_ID, "
                + "tm.NOMBRE AS NOMBRE_TIPO_MESA, "
                + "l.LOCAL_ID, "
                + "l.NOMBRE AS NOMBRE_LOCAL "
                + "FROM RES_MESAS m "
                + "INNER JOIN RES_TIPOS_MESAS tm ON m.TMESA_ID = tm.TMESA_ID "
                + "INNER JOIN RES_LOCALES l ON m.LOCAL_ID = l.LOCAL_ID "
                + "WHERE (? IS NULL OR m.ESTADO = ?) "
                + "AND (? IS NULL OR l.LOCAL_ID = ?) "
                + "AND (? IS NULL OR tm.TMESA_ID = ?) "
                + "AND (? IS NULL OR m.NUMEROMESA LIKE CONCAT('%', ?, '%')) "
                + "ORDER BY l.NOMBRE ASC, m.NUMEROMESA ASC";
        return sql;
    }

    @Override
    public List<MesaDTO> asignarMesas(ReservaDTO reserva) {
        List<MesaDTO> mesasAsignadas = new ArrayList<>();

        String sqlSeleccionarMesas
                = "SELECT m.MESA_ID, m.LOCAL_ID, m.TMESA_ID, m.NUMEROMESA, m.CAPACIDAD, m.ESTADO, m.X, m.Y, "
                + "m.FECHA_CREACION, m.USUARIO_CREACION, m.FECHA_MODIFICACION, m.USUARIO_MODIFICACION "
                + "FROM RES_MESAS m "
                + "WHERE m.ESTADO = 'DISPONIBLE' "
                + "AND m.LOCAL_ID = ? "
                + "AND m.TMESA_ID = ? "
                + "AND m.MESA_ID NOT IN ( "
                + "  SELECT rxm.MESA_ID "
                + "  FROM RES_RESERVAS_x_MESAS rxm "
                + "  JOIN RES_RESERVAS r ON r.RESERVA_ID = rxm.RESERVA_ID "
                + "  WHERE r.FECHA_HORA_REGISTRO BETWEEN ? AND ? "
                + "  AND r.ESTADO IN ('PENDIENTE', 'CONFIRMADA') "
                + ") "
                + "ORDER BY m.CAPACIDAD ASC";

        String sqlInsertarAsignacion
                = "INSERT INTO RES_RESERVAS_X_MESAS (RESERVA_ID, MESA_ID) VALUES (?, ?)";
        
        String sqlActualizarEstadoMesa
                = "UPDATE RES_MESAS SET ESTADO = 'RESERVADA' WHERE MESA_ID = ?";

        try (Connection conn = DBManager.getInstance().getConnection(); 
             PreparedStatement stmtMesas = conn.prepareStatement(sqlSeleccionarMesas); 
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsertarAsignacion);
             PreparedStatement stmtUpdate = conn.prepareStatement(sqlActualizarEstadoMesa)) {

            // Calcular ventana de tiempo (2 horas antes y después para evitar conflictos)
            long dosHoras = 2 * 60 * 60 * 1000; // 2 horas en milisegundos
            Timestamp fechaInicio = new Timestamp(reserva.getFechaHoraRegistro().getTime() - dosHoras);
            Timestamp fechaFin = new Timestamp(reserva.getFechaHoraRegistro().getTime() + dosHoras);

            stmtMesas.setInt(1, reserva.getLocal().getIdLocal());
            stmtMesas.setInt(2, reserva.getTipoMesa().getIdTipoMesa());
            stmtMesas.setTimestamp(3, fechaInicio);
            stmtMesas.setTimestamp(4, fechaFin);

            ResultSet rs = stmtMesas.executeQuery();
            int personasPendientes = reserva.getCantidadPersonas();
            int mesasNecesarias = reserva.getNumeroMesas() != null ? reserva.getNumeroMesas() : 1;

            while (rs.next() && personasPendientes > 0 && mesasAsignadas.size() < mesasNecesarias) {
                MesaDTO mesa = new MesaDTO();
                mesa.setIdMesa(rs.getInt("MESA_ID"));

                LocalDTO local = new LocalDTO();
                local.setIdLocal(rs.getInt("LOCAL_ID"));
                mesa.setLocal(local);

                TipoMesaDTO tipoMesa = new TipoMesaDTO();
                tipoMesa.setIdTipoMesa(rs.getInt("TMESA_ID"));
                mesa.setTipoMesa(tipoMesa);

                mesa.setNumeroMesa(rs.getString("NUMEROMESA"));
                mesa.setCapacidad(rs.getInt("CAPACIDAD"));

                String estadoStr = rs.getString("ESTADO");
                mesa.setEstado(estadoStr != null ? EstadoMesa.valueOf(estadoStr) : null);

                int x = rs.getInt("X");
                mesa.setX(rs.wasNull() ? null : x);

                int y = rs.getInt("Y");
                mesa.setY(rs.wasNull() ? null : y);

                Timestamp tsCreacion = rs.getTimestamp("FECHA_CREACION");
                mesa.setFechaCreacion(tsCreacion);

                mesa.setUsuarioCreacion(rs.getString("USUARIO_CREACION"));

                Timestamp tsModificacion = rs.getTimestamp("FECHA_MODIFICACION");
                mesa.setFechaModificacion(tsModificacion);

                mesa.setUsuarioModificacion(rs.getString("USUARIO_MODIFICACION"));

                // Insertar en tabla de relación
                stmtInsert.setInt(1, reserva.getIdReserva());
                stmtInsert.setInt(2, mesa.getIdMesa());
                stmtInsert.executeUpdate();
                
                // Actualizar estado de la mesa a RESERVADA
                stmtUpdate.setInt(1, mesa.getIdMesa());
                stmtUpdate.executeUpdate();
                mesa.setEstado(EstadoMesa.RESERVADA);

                personasPendientes -= mesa.getCapacidad();
                mesasAsignadas.add(mesa);
            }
        } catch (SQLException e) {
            Logger.getLogger(MesaDAOImpl.class.getName()).log(Level.SEVERE, null, e);
        }

        return mesasAsignadas;
    }
}