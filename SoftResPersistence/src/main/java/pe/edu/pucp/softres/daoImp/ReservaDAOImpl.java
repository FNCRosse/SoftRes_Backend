package pe.edu.pucp.softres.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import pe.edu.pucp.softres.dao.ReservaDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.db.DBManager;
import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.ReservaParametrosBuilder;

/**
 *
 * @author frank
 */
public class ReservaDAOImpl extends DAOImplBase implements ReservaDAO {

    private ReservaDTO reserva;
    private DataSource dataSource; // Asegúrate que se setea en algún lado

    public ReservaDAOImpl() {
        super("RES_RESERVAS");
        this.retornarLlavePrimaria = true;
        this.reserva = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("RESERVA_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("LOCAL_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("FILA_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TIPO_RESERVA", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_REGISTRO", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("CANT_PERSONAS", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TMESA_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("NUM_MESAS", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("OBSERVACIONES", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("MOTIVO_CANCELACION_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("NOMBRE_EVENTO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("DESCP_EVENTO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // Usuario
        if (this.reserva.getUsuario() != null && this.reserva.getUsuario().getIdUsuario() != null) {
            this.statement.setInt(1, this.reserva.getUsuario().getIdUsuario());
        } else {
            this.statement.setNull(1, Types.INTEGER);
        }
        // Local
        if (this.reserva.getLocal() != null && this.reserva.getLocal().getIdLocal() != null) {
            this.statement.setInt(2, this.reserva.getLocal().getIdLocal());
        } else {
            this.statement.setNull(2, Types.INTEGER);
        }
        // Fila Espera
        if (this.reserva.getFilaEspera() != null && this.reserva.getFilaEspera().getIdFila() != null) {
            this.statement.setInt(3, this.reserva.getFilaEspera().getIdFila());
        } else {
            this.statement.setNull(3, Types.INTEGER);
        }
        // Tipo Reserva (enum)
        if (this.reserva.getTipoReserva() != null) {
            this.statement.setString(4, this.reserva.getTipoReserva().name());
        } else {
            this.statement.setNull(4, Types.VARCHAR);
        }
        // Fecha-Hora
        if (this.reserva.getFechaHoraRegistro() != null) {
            this.statement.setObject(5, this.reserva.getFechaHoraRegistro());
        } else {
            this.statement.setNull(5, Types.TIMESTAMP);
        }
        // Cantidad personas
        if (this.reserva.getCantidadPersonas() != null) {
            this.statement.setInt(6, this.reserva.getCantidadPersonas());
        } else {
            this.statement.setNull(6, Types.INTEGER);
        }
        // Tipo Mesa
        if (this.reserva != null && this.reserva.getTipoMesa() != null && this.reserva.getTipoMesa().getIdTipoMesa() != null) {
            this.statement.setInt(7, this.reserva.getTipoMesa().getIdTipoMesa());
        } else {
            this.statement.setNull(7, Types.INTEGER);
        }
        // Número Mesas
        if (this.reserva.getNumeroMesas() != null) {
            this.statement.setInt(8, this.reserva.getNumeroMesas());
        } else {
            this.statement.setNull(8, Types.INTEGER);
        }
        // Observaciones
        if (this.reserva.getObservaciones() != null) {
            this.statement.setString(9, this.reserva.getObservaciones());
        } else {
            this.statement.setNull(9, Types.VARCHAR);
        }
        // Estado (enum)
        if (this.reserva.getEstado() != null) {
            this.statement.setString(10, this.reserva.getEstado().name());
        } else {
            this.statement.setNull(10, Types.VARCHAR);
        }
        // Motivo cancelación
        if (this.reserva.getMotivoCancelacion() != null && this.reserva.getMotivoCancelacion().getIdMotivo() != null) {
            this.statement.setInt(11, this.reserva.getMotivoCancelacion().getIdMotivo());
        } else {
            this.statement.setNull(11, Types.INTEGER);
        }
        // Nombre Evento
        if (this.reserva.getNombreEvento() != null) {
            this.statement.setString(12, this.reserva.getNombreEvento());
        } else {
            this.statement.setNull(12, Types.VARCHAR);
        }
        // Descripción Evento
        if (this.reserva.getDescripcionEvento() != null) {
            this.statement.setString(13, this.reserva.getDescripcionEvento());
        } else {
            this.statement.setNull(13, Types.VARCHAR);
        }
        // Fecha Creacion
        if (this.reserva.getFechaCreacion() != null) {
            this.statement.setObject(14, this.reserva.getFechaCreacion());
        } else {
            this.statement.setNull(14, Types.TIMESTAMP);
        }
        // Usuario Creacion
        if (this.reserva.getUsuarioCreacion() != null) {
            this.statement.setString(15, this.reserva.getUsuarioCreacion());
        } else {
            this.statement.setNull(15, Types.VARCHAR);
        }
        // Fecha Modificación
        if (this.reserva.getFechaModificacion() != null) {
            this.statement.setObject(16, this.reserva.getFechaModificacion());
        } else {
            this.statement.setNull(16, Types.TIMESTAMP);
        }
        // Usuario Modificación
        if (this.reserva.getUsuarioModificacion() != null) {
            this.statement.setString(17, this.reserva.getUsuarioModificacion());
        } else {
            this.statement.setNull(17, Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        incluirValorDeParametrosParaInsercion();
        this.statement.setInt(18, this.reserva.getIdReserva());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setString(1, this.reserva.getEstado().toString());
        this.statement.setObject(2, this.reserva.getFechaModificacion());
        this.statement.setString(3, this.reserva.getUsuarioModificacion());
        this.statement.setInt(4, this.reserva.getMotivoCancelacion().getIdMotivo());
        this.statement.setInt(5, this.reserva.getIdReserva());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.reserva.getIdReserva());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.reserva = new ReservaDTO();

        this.reserva.setIdReserva(this.resultSet.getInt("RESERVA_ID"));

        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));
        // Intentar obtener campos con JOIN si están disponibles
        try {
            usuario.setNombreComp(this.resultSet.getString("NOMBRE_USUARIO"));
            usuario.setNumeroDocumento(this.resultSet.getString("NUMERO_DOC"));
        } catch (SQLException e) {
            // Los campos no están disponibles, usar solo el ID
        }
        this.reserva.setUsuario(usuario);

        LocalDTO local = new LocalDTO();
        local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));
        // Intentar obtener nombre si está disponible
        try {
            local.setNombre(this.resultSet.getString("NOMBRE_LOCAL"));
        } catch (SQLException e) {
            // Campo no disponible
        }
        this.reserva.setLocal(local);

        // FilaEspera puede ser null
        try {
            int filaId = this.resultSet.getInt("FILA_ID");
            if (!this.resultSet.wasNull()) {
                FilaEsperaDTO filaEspera = new FilaEsperaDTO();
                filaEspera.setIdFila(filaId);
                try {
                    String estadoFila = this.resultSet.getString("ESTADO_FILA");
                    filaEspera.setEstado(estadoFila != null ? EstadoFilaEspera.valueOf(estadoFila) : null);
                } catch (SQLException e) {
                    // Campo no disponible
                }
                this.reserva.setFilaEspera(filaEspera);
            }
        } catch (SQLException e) {
            // FILA_ID no disponible
            this.reserva.setFilaEspera(null);
        }

        String tipoReservaStr = this.resultSet.getString("TIPO_RESERVA");
        this.reserva.setTipoReserva(tipoReservaStr != null ? TipoReserva.valueOf(tipoReservaStr) : null);

        Timestamp tsRegistro = this.resultSet.getTimestamp("FECHA_HORA_REGISTRO");
        this.reserva.setFechaHoraRegistro(tsRegistro);

        this.reserva.setCantidadPersonas(this.resultSet.getInt("CANT_PERSONAS"));

        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(this.resultSet.getInt("TMESA_ID"));
        // Intentar obtener nombre si está disponible
        try {
            tipoMesa.setNombre(this.resultSet.getString("NOMBRE_TIPO_MESA"));
        } catch (SQLException e) {
            // Campo no disponible
        }
        this.reserva.setTipoMesa(tipoMesa);

        this.reserva.setNumeroMesas(this.resultSet.getInt("NUM_MESAS"));
        this.reserva.setObservaciones(this.resultSet.getString("OBSERVACIONES"));

        // Intentar ambos nombres de columna para el estado
        String estadoStr = null;
        try {
            estadoStr = this.resultSet.getString("ESTADO_RESERVA");
        } catch (SQLException e) {
            try {
                estadoStr = this.resultSet.getString("ESTADO");
            } catch (SQLException e2) {
                // Ninguno disponible
            }
        }
        this.reserva.setEstado(estadoStr != null ? EstadoReserva.valueOf(estadoStr) : null);

        // Motivo de cancelación
        try {
            int motivoId = this.resultSet.getInt("MOTIVO_ID");
            if (!this.resultSet.wasNull()) {
                MotivosCancelacionDTO motivo = new MotivosCancelacionDTO();
                motivo.setIdMotivo(motivoId);
                try {
                    motivo.setDescripcion(this.resultSet.getString("MOTIVO_CANCELACION"));
                } catch (SQLException e) {
                    // Descripción no disponible
                }
                this.reserva.setMotivoCancelacion(motivo);
            } else {
                this.reserva.setMotivoCancelacion(null);
            }
        } catch (SQLException e) {
            // Intentar con el nombre original
            try {
                int motivoId = this.resultSet.getInt("MOTIVO_CANCELACION_ID");
                if (!this.resultSet.wasNull()) {
                    MotivosCancelacionDTO motivo = new MotivosCancelacionDTO();
                    motivo.setIdMotivo(motivoId);
                    this.reserva.setMotivoCancelacion(motivo);
                } else {
                    this.reserva.setMotivoCancelacion(null);
                }
            } catch (SQLException e2) {
                this.reserva.setMotivoCancelacion(null);
            }
        }

        this.reserva.setNombreEvento(this.resultSet.getString("NOMBRE_EVENTO"));
        this.reserva.setDescripcionEvento(this.resultSet.getString("DESCP_EVENTO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.reserva.setFechaCreacion(tsCreacion);

        this.reserva.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.reserva.setFechaModificacion(tsModificacion);

        this.reserva.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    protected void instanciarObjetoDelResultSetParaListado() throws SQLException {
        this.reserva = new ReservaDTO();
        this.reserva.setIdReserva(this.resultSet.getInt("RESERVA_ID"));

        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));
        usuario.setNombreComp(this.resultSet.getString("NOMBRE_USUARIO"));
        usuario.setNumeroDocumento(this.resultSet.getString("NUMERO_DOC"));
        this.reserva.setUsuario(usuario);

        LocalDTO local = new LocalDTO();
        local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));
        local.setNombre(this.resultSet.getString("NOMBRE_LOCAL"));
        this.reserva.setLocal(local);

        FilaEsperaDTO filaEspera = new FilaEsperaDTO();
        filaEspera.setIdFila(this.resultSet.getInt("FILA_ID"));
        String estadoFila = this.resultSet.getString("ESTADO_FILA");
        filaEspera.setEstado(estadoFila != null ? EstadoFilaEspera.valueOf(estadoFila) : null);
        this.reserva.setFilaEspera(filaEspera);

        String tipoReservaStr = this.resultSet.getString("TIPO_RESERVA");
        this.reserva.setTipoReserva(tipoReservaStr != null ? TipoReserva.valueOf(tipoReservaStr) : null);

        Timestamp tsRegistro = this.resultSet.getTimestamp("FECHA_HORA_REGISTRO");
        this.reserva.setFechaHoraRegistro(tsRegistro);

        this.reserva.setCantidadPersonas(this.resultSet.getInt("CANT_PERSONAS"));

        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        tipoMesa.setIdTipoMesa(this.resultSet.getInt("TMESA_ID"));
        tipoMesa.setNombre(this.resultSet.getString("NOMBRE_TIPO_MESA"));
        this.reserva.setTipoMesa(tipoMesa);

        this.reserva.setNumeroMesas(this.resultSet.getInt("NUM_MESAS"));
        this.reserva.setObservaciones(this.resultSet.getString("OBSERVACIONES"));

        String estadoStr = this.resultSet.getString("ESTADO_RESERVA");
        this.reserva.setEstado(estadoStr != null ? EstadoReserva.valueOf(estadoStr) : null);

        int motivoId = this.resultSet.getInt("MOTIVO_ID");
        String descMotivio = this.resultSet.getString("MOTIVO_CANCELACION");
        if (!this.resultSet.wasNull()) {
            MotivosCancelacionDTO motivo = new MotivosCancelacionDTO();
            motivo.setIdMotivo(motivoId);
            motivo.setDescripcion(descMotivio);
            this.reserva.setMotivoCancelacion(motivo);
        } else {
            this.reserva.setMotivoCancelacion(null);
        }

        this.reserva.setNombreEvento(this.resultSet.getString("NOMBRE_EVENTO"));
        this.reserva.setDescripcionEvento(this.resultSet.getString("DESCP_EVENTO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.reserva.setFechaCreacion(tsCreacion);

        this.reserva.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.reserva.setFechaModificacion(tsModificacion);

        this.reserva.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.reserva = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetParaListado();
        lista.add(this.reserva);
    }

    @Override
    public Integer insertar(ReservaDTO reserva) {
        this.reserva = reserva;
        return super.insertar();
    }

    @Override
    public ReservaDTO obtenerPorId(Integer id) {
        this.reserva = new ReservaDTO();
        this.reserva.setIdReserva(id);
        super.obtenerPorId();
        return this.reserva;
    }

    @Override
    protected String generarSQLParaObtenerPorId() {
        String sql = "SELECT "
                + "    r.RESERVA_ID, r.TIPO_RESERVA, r.FECHA_HORA_REGISTRO, r.CANT_PERSONAS, r.NUM_MESAS, "
                + "    r.OBSERVACIONES, r.NOMBRE_EVENTO, r.DESCP_EVENTO, r.ESTADO AS ESTADO_RESERVA, "
                + "    r.FECHA_CREACION, r.USUARIO_CREACION, r.FECHA_MODIFICACION, r.USUARIO_MODIFICACION, "
                + "    u.USUARIO_ID, u.NOMBRE_COMP AS NOMBRE_USUARIO, u.NUMERO_DOC, "
                + "    l.LOCAL_ID, l.NOMBRE AS NOMBRE_LOCAL, "
                + "    c.MOTIVO_ID, c.DESCRIPCION AS MOTIVO_CANCELACION, "
                + "    tm.TMESA_ID, tm.NOMBRE AS NOMBRE_TIPO_MESA, "
                + "    fe.FILA_ID, fe.ESTADO AS ESTADO_FILA "
                + "FROM RES_RESERVAS r "
                + "INNER JOIN RES_USUARIOS u ON r.USUARIO_ID = u.USUARIO_ID "
                + "INNER JOIN RES_LOCALES l ON r.LOCAL_ID = l.LOCAL_ID "
                + "INNER JOIN RES_TIPOS_MESAS tm ON r.TMESA_ID = tm.TMESA_ID "
                + "LEFT JOIN RES_MOTIVOS_CANCELACION c ON r.MOTIVO_CANCELACION_ID = c.MOTIVO_ID "
                + "LEFT JOIN RES_FILASESPERA fe ON r.FILA_ID = fe.FILA_ID "
                + "WHERE r.RESERVA_ID = ?";
        return sql;
    }

    @Override
    public List<ReservaDTO> listar(ReservaParametros reservaParametro) {
        // Evita NullPointerException si reservaParametro es null
        if (reservaParametro == null) {
            reservaParametro = new ReservaParametros();
        }

        ReservaParametros parametros = new ReservaParametrosBuilder()
                .setTipoReserva(reservaParametro.getTipoReserva())
                .setFechaInicio(reservaParametro.getFechaInicio())
                .setFechaFin(reservaParametro.getFechaFin())
                .setIdLocal(reservaParametro.getIdLocal())
                .setdniCliente(reservaParametro.getDniCliente())
                .setUsuarioId(reservaParametro.getUsuarioId())
                .setEstado(reservaParametro.getEstado())
                .buildReservaParametros();

        String sql = this.generarSQLParaListar();
        return (ArrayList<ReservaDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        String sql
                = "SELECT "
                + "    r.RESERVA_ID, r.TIPO_RESERVA, r.FECHA_HORA_REGISTRO, r.CANT_PERSONAS, r.NUM_MESAS, "
                + "    r.OBSERVACIONES, r.NOMBRE_EVENTO, r.DESCP_EVENTO, r.ESTADO AS ESTADO_RESERVA, "
                + "    r.FECHA_CREACION, r.USUARIO_CREACION, r.FECHA_MODIFICACION, r.USUARIO_MODIFICACION, "
                + "    u.USUARIO_ID, u.NOMBRE_COMP AS NOMBRE_USUARIO, u.NUMERO_DOC, "
                + "    l.LOCAL_ID, l.NOMBRE AS NOMBRE_LOCAL, "
                + "    c.MOTIVO_ID, c.DESCRIPCION AS MOTIVO_CANCELACION, "
                + "    tm.TMESA_ID, tm.NOMBRE AS NOMBRE_TIPO_MESA, "
                + "    fe.FILA_ID, fe.ESTADO AS ESTADO_FILA "
                + "FROM RES_RESERVAS r "
                + "INNER JOIN RES_USUARIOS u ON r.USUARIO_ID = u.USUARIO_ID "
                + "INNER JOIN RES_LOCALES l ON r.LOCAL_ID = l.LOCAL_ID "
                + "INNER JOIN RES_TIPOS_MESAS tm ON r.TMESA_ID = tm.TMESA_ID "
                + "LEFT JOIN RES_MOTIVOS_CANCELACION c ON r.MOTIVO_CANCELACION_ID = c.MOTIVO_ID "
                + "LEFT JOIN RES_FILASESPERA fe ON r.FILA_ID = fe.FILA_ID "
                + "WHERE "
                + "    (? IS NULL OR r.ESTADO = ?) "
                + "    AND (? IS NULL OR r.TIPO_RESERVA = ?) "
                + "    AND (? IS NULL OR r.LOCAL_ID = ?) "
                + "    AND (? IS NULL OR u.NUMERO_DOC = ?) "
                + "    AND (? IS NULL OR r.USUARIO_ID = ?) "
                + "    AND ((? IS NULL AND ? IS NULL) OR (r.FECHA_HORA_REGISTRO BETWEEN ? AND ?)) "
                + "ORDER BY r.ESTADO DESC, r.FECHA_HORA_REGISTRO ASC";
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        ReservaParametros parametros = (ReservaParametros) objParametros;
        try {
            // 1-2 Estado
            if (parametros.getEstado() != null) {
                this.statement.setString(1, parametros.getEstado().name());
                this.statement.setString(2, parametros.getEstado().name());
            } else {
                this.statement.setNull(1, Types.VARCHAR);
                this.statement.setNull(2, Types.VARCHAR);
            }
            // 3-4 TipoReserva
            if (parametros.getTipoReserva() != null) {
                this.statement.setString(3, parametros.getTipoReserva().name());
                this.statement.setString(4, parametros.getTipoReserva().name());
            } else {
                this.statement.setNull(3, Types.VARCHAR);
                this.statement.setNull(4, Types.VARCHAR);
            }
            // 5-6 Local
            if (parametros.getIdLocal() != null) {
                this.statement.setInt(5, parametros.getIdLocal());
                this.statement.setInt(6, parametros.getIdLocal());
            } else {
                this.statement.setNull(5, Types.INTEGER);
                this.statement.setNull(6, Types.INTEGER);
            }
            // 7-8 DNI Cliente
            if (parametros.getDniCliente() != null) {
                this.statement.setString(7, parametros.getDniCliente());
                this.statement.setString(8, parametros.getDniCliente());
            } else {
                this.statement.setNull(7, Types.VARCHAR);
                this.statement.setNull(8, Types.VARCHAR);
            }
            // 9-10 Usuario ID
            if (parametros.getUsuarioId() != null) {
                this.statement.setInt(9, parametros.getUsuarioId());
                this.statement.setInt(10, parametros.getUsuarioId());
            } else {
                this.statement.setNull(9, Types.INTEGER);
                this.statement.setNull(10, Types.INTEGER);
            }
            // 11-14 Fechas
            if (parametros.getFechaInicio() != null && parametros.getFechaFin() != null) {
                this.statement.setObject(11, parametros.getFechaInicio());
                this.statement.setObject(12, parametros.getFechaFin());
                this.statement.setObject(13, parametros.getFechaInicio());
                this.statement.setObject(14, parametros.getFechaFin());
            } else {
                this.statement.setNull(11, Types.TIMESTAMP);
                this.statement.setNull(12, Types.TIMESTAMP);
                this.statement.setNull(13, Types.TIMESTAMP);
                this.statement.setNull(14, Types.TIMESTAMP);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Integer modificar(ReservaDTO reserva) {
        this.reserva = reserva;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ReservaDTO reserva) {
        this.reserva = reserva;
        eliminarComentariosPorReserva(this.reserva.getIdReserva());
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "UPDATE RES_RESERVAS "
                + "SET ESTADO = ? "
                + ", FECHA_MODIFICACION = ? "
                + ", USUARIO_MODIFICACION = ? "
                + ", MOTIVO_CANCELACION_ID = ? "
                + " WHERE RESERVA_ID = ?";
        return sql;
    }

    private void eliminarComentariosPorReserva(Integer idReserva) {
        String sql = "DELETE FROM RES_COMENTARIOS WHERE RESERVA_ID = ?";
        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ReservaDAOImpl.class.getName())
                    .log(Level.SEVERE, "Error al eliminar comentarios por reserva", e);
        }
    }

    @Override
    public boolean intentarAsignarMesas(ReservaDTO reserva) {
        // Esta consulta es una obra de arte:
        // 1. Selecciona las mesas DISPONIBLES del LOCAL y TIPO correctos.
        // 2. EXCLUYE las mesas que ya están asignadas a otra reserva que se cruza en el tiempo.
        // 3. Limita el resultado al número de mesas que se necesitan.
        String sql = "SELECT MESA_ID FROM RES_MESAS WHERE ESTADO = 'DISPONIBLE' AND LOCAL_ID = ? AND TMESA_ID = ? "
                + "AND MESA_ID NOT IN (SELECT MESA_ID FROM RES_RESERVAS_x_MESAS rxm "
                + "INNER JOIN RES_RESERVAS r ON rxm.RESERVA_ID = r.RESERVA_ID "
                + "WHERE r.ESTADO IN ('CONFIRMADA', 'PENDIENTE') "
                + "AND r.FECHA_HORA_REGISTRO BETWEEN ? AND ?) "
                + "LIMIT ?";

        String sqlInsert = "INSERT INTO RES_RESERVAS_x_MESAS (RESERVA_ID, MESA_ID) VALUES (?, ?)";
        String sqlUpdateMesa = "UPDATE RES_MESAS SET ESTADO = 'RESERVADA' WHERE MESA_ID = ?";

        List<Integer> idsMesasDisponibles = new ArrayList<>();

        try (Connection conn = DBManager.getInstance().getConnection(); PreparedStatement stmtSelect = conn.prepareStatement(sql)) {

            // Ventana de tiempo de 2 horas para evitar solapamientos
            Timestamp inicioVentana = new Timestamp(reserva.getFechaHoraRegistro().getTime() - (2 * 60 * 60 * 1000));
            Timestamp finVentana = new Timestamp(reserva.getFechaHoraRegistro().getTime() + (2 * 60 * 60 * 1000));

            stmtSelect.setInt(1, reserva.getLocal().getIdLocal());
            stmtSelect.setInt(2, reserva.getTipoMesa().getIdTipoMesa());
            stmtSelect.setTimestamp(3, inicioVentana);
            stmtSelect.setTimestamp(4, finVentana);
            stmtSelect.setInt(5, reserva.getNumeroMesas());

            try (ResultSet rs = stmtSelect.executeQuery()) {
                while (rs.next()) {
                    idsMesasDisponibles.add(rs.getInt("MESA_ID"));
                }
            }

            // Si no encontramos suficientes mesas, la operación falla.
            if (idsMesasDisponibles.size() < reserva.getNumeroMesas()) {
                return false;
            }

            // Si encontramos mesas, las asignamos dentro de una transacción.
            conn.setAutoCommit(false);
            try (PreparedStatement stmtInsertAsignacion = conn.prepareStatement(sqlInsert); PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdateMesa)) {

                for (Integer idMesa : idsMesasDisponibles) {
                    // Asignar en RES_RESERVAS_x_MESAS
                    stmtInsertAsignacion.setInt(1, reserva.getIdReserva());
                    stmtInsertAsignacion.setInt(2, idMesa);
                    stmtInsertAsignacion.addBatch();

                    // Actualizar estado de la mesa a RESERVADA
                    stmtUpdate.setInt(1, idMesa);
                    stmtUpdate.addBatch();
                }
                stmtInsertAsignacion.executeBatch();
                stmtUpdate.executeBatch();
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            Logger.getLogger(ReservaDAOImpl.class.getName()).log(Level.SEVERE, "Error al intentar asignar mesas", e);
            return false;
        }
    }
}
