package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.sql.Types;
import java.sql.Timestamp;
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
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
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
        // Configuración completa según el esquema SQL RES_FILASESPERA
        this.listaColumnas.add(new Columna("FILA_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("LOCAL_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TIPO_RESERVA", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("CANT_PERSONAS", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TMESA_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("FECHA_HORA_DESEADA", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("FECHA_REGISTRO", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("FECHA_NOTIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("OBSERVACIONES", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // USUARIO_ID
        if (this.filaEspera.getUsuario() != null && this.filaEspera.getUsuario().getIdUsuario() != null) {
            this.statement.setInt(1, this.filaEspera.getUsuario().getIdUsuario());
        } else {
            this.statement.setNull(1, Types.INTEGER);
        }
        
        // LOCAL_ID
        if (this.filaEspera.getLocal() != null && this.filaEspera.getLocal().getIdLocal() != null) {
            this.statement.setInt(2, this.filaEspera.getLocal().getIdLocal());
        } else {
            this.statement.setNull(2, Types.INTEGER);
        }
        
        // TIPO_RESERVA
        if (this.filaEspera.getTipoReserva() != null) {
            this.statement.setString(3, this.filaEspera.getTipoReserva().name());
        } else {
            this.statement.setNull(3, Types.VARCHAR);
        }
        
        // CANT_PERSONAS
        if (this.filaEspera.getCantidadPersonas() != null) {
            this.statement.setInt(4, this.filaEspera.getCantidadPersonas());
        } else {
            this.statement.setNull(4, Types.INTEGER);
        }
        
        // TMESA_ID
        if (this.filaEspera.getTipoMesa() != null && this.filaEspera.getTipoMesa().getIdTipoMesa() != null) {
            this.statement.setInt(5, this.filaEspera.getTipoMesa().getIdTipoMesa());
        } else {
            this.statement.setNull(5, Types.INTEGER);
        }
        
        // FECHA_HORA_DESEADA
        if (this.filaEspera.getFechaHoraDeseada() != null) {
            this.statement.setObject(6, this.filaEspera.getFechaHoraDeseada());
        } else {
            this.statement.setNull(6, Types.TIMESTAMP);
        }
        
        // FECHA_REGISTRO (normalmente NOW() en BD, pero podemos controlarlo aquí)
        if (this.filaEspera.getFechaRegistro() != null) {
            this.statement.setObject(7, this.filaEspera.getFechaRegistro());
        } else {
            this.statement.setObject(7, new java.util.Date()); // Default a fecha actual
        }
        
        // FECHA_NOTIFICACION
        if (this.filaEspera.getFechaNotificacion() != null) {
            this.statement.setObject(8, this.filaEspera.getFechaNotificacion());
        } else {
            this.statement.setNull(8, Types.TIMESTAMP);
        }
        
        // ESTADO
        if (this.filaEspera.getEstado() != null) {
            this.statement.setString(9, this.filaEspera.getEstado().name());
        } else {
            this.statement.setString(9, "PENDIENTE"); // Default
        }
        
        // OBSERVACIONES
        if (this.filaEspera.getObservaciones() != null) {
            this.statement.setString(10, this.filaEspera.getObservaciones());
        } else {
            this.statement.setNull(10, Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        // Todos los campos para modificación
        
        // USUARIO_ID
        if (this.filaEspera.getUsuario() != null && this.filaEspera.getUsuario().getIdUsuario() != null) {
            this.statement.setInt(1, this.filaEspera.getUsuario().getIdUsuario());
        } else {
            this.statement.setNull(1, Types.INTEGER);
        }
        
        // LOCAL_ID
        if (this.filaEspera.getLocal() != null && this.filaEspera.getLocal().getIdLocal() != null) {
            this.statement.setInt(2, this.filaEspera.getLocal().getIdLocal());
        } else {
            this.statement.setNull(2, Types.INTEGER);
        }
        
        // TIPO_RESERVA
        if (this.filaEspera.getTipoReserva() != null) {
            this.statement.setString(3, this.filaEspera.getTipoReserva().name());
        } else {
            this.statement.setNull(3, Types.VARCHAR);
        }
        
        // CANT_PERSONAS
        if (this.filaEspera.getCantidadPersonas() != null) {
            this.statement.setInt(4, this.filaEspera.getCantidadPersonas());
        } else {
            this.statement.setNull(4, Types.INTEGER);
        }
        
        // TMESA_ID
        if (this.filaEspera.getTipoMesa() != null && this.filaEspera.getTipoMesa().getIdTipoMesa() != null) {
            this.statement.setInt(5, this.filaEspera.getTipoMesa().getIdTipoMesa());
        } else {
            this.statement.setNull(5, Types.INTEGER);
        }
        
        // FECHA_HORA_DESEADA
        if (this.filaEspera.getFechaHoraDeseada() != null) {
            this.statement.setObject(6, this.filaEspera.getFechaHoraDeseada());
        } else {
            this.statement.setNull(6, Types.TIMESTAMP);
        }
        
        // FECHA_REGISTRO
        if (this.filaEspera.getFechaRegistro() != null) {
            this.statement.setObject(7, this.filaEspera.getFechaRegistro());
        } else {
            this.statement.setNull(7, Types.TIMESTAMP);
        }
        
        // FECHA_NOTIFICACION
        if (this.filaEspera.getFechaNotificacion() != null) {
            this.statement.setObject(8, this.filaEspera.getFechaNotificacion());
        } else {
            this.statement.setNull(8, Types.TIMESTAMP);
        }
        
        // ESTADO
        if (this.filaEspera.getEstado() != null) {
            this.statement.setString(9, this.filaEspera.getEstado().name());
        } else {
            this.statement.setString(9, "PENDIENTE");
        }
        
        // OBSERVACIONES
        if (this.filaEspera.getObservaciones() != null) {
            this.statement.setString(10, this.filaEspera.getObservaciones());
        } else {
            this.statement.setNull(10, Types.VARCHAR);
        }
        
        // WHERE clause - FILA_ID
        this.statement.setInt(11, this.filaEspera.getIdFila());
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
        
        // FILA_ID
        this.filaEspera.setIdFila(this.resultSet.getInt("FILA_ID"));

        // USUARIO_ID
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));
        this.filaEspera.setUsuario(usuario);

        // LOCAL_ID
        LocalDTO local = new LocalDTO();
        local.setIdLocal(this.resultSet.getInt("LOCAL_ID"));
        this.filaEspera.setLocal(local);

        // TIPO_RESERVA
        String tipoReservaStr = this.resultSet.getString("TIPO_RESERVA");
        this.filaEspera.setTipoReserva(tipoReservaStr != null ? TipoReserva.valueOf(tipoReservaStr) : null);

        // CANT_PERSONAS
        int cantPersonas = this.resultSet.getInt("CANT_PERSONAS");
        this.filaEspera.setCantidadPersonas(this.resultSet.wasNull() ? null : cantPersonas);

        // TMESA_ID
        TipoMesaDTO tipoMesa = new TipoMesaDTO();
        int tipoMesaId = this.resultSet.getInt("TMESA_ID");
        if (!this.resultSet.wasNull()) {
            tipoMesa.setIdTipoMesa(tipoMesaId);
            this.filaEspera.setTipoMesa(tipoMesa);
        }

        // FECHA_HORA_DESEADA
        Timestamp tsFechaDeseada = this.resultSet.getTimestamp("FECHA_HORA_DESEADA");
        this.filaEspera.setFechaHoraDeseada(tsFechaDeseada);

        // FECHA_REGISTRO
        Timestamp tsFechaRegistro = this.resultSet.getTimestamp("FECHA_REGISTRO");
        this.filaEspera.setFechaRegistro(tsFechaRegistro);

        // FECHA_NOTIFICACION
        Timestamp tsFechaNotificacion = this.resultSet.getTimestamp("FECHA_NOTIFICACION");
        this.filaEspera.setFechaNotificacion(tsFechaNotificacion);

        // ESTADO
        String estadoStr = this.resultSet.getString("ESTADO");
        if (estadoStr != null) {
            try {
                this.filaEspera.setEstado(EstadoFilaEspera.valueOf(estadoStr));
            } catch (IllegalArgumentException e) {
                this.filaEspera.setEstado(EstadoFilaEspera.PENDIENTE); // Default fallback
            }
        } else {
            this.filaEspera.setEstado(EstadoFilaEspera.PENDIENTE);
        }

        // OBSERVACIONES
        this.filaEspera.setObservaciones(this.resultSet.getString("OBSERVACIONES"));
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
        if (filaEsperaParametro == null) {
            filaEsperaParametro = new FilaEsperaParametros();
        }
        
        Object parametros = new FilaEsperaParametrosBuilder().
                setIdFila(filaEsperaParametro.getIdFila()).
                setIdUsuario(filaEsperaParametro.getIdUsuario()).
                setEstado(filaEsperaParametro.getEstado()).
                buildFilaEsperaParametros();
        String sql = this.generarSQLParaListar();
        return (ArrayList<FilaEsperaDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        // Seleccionar todas las columnas necesarias para instanciarObjetoDelResultSet
        String sql = "SELECT f.FILA_ID, f.USUARIO_ID, f.LOCAL_ID, f.TIPO_RESERVA, ";
        sql += "f.CANT_PERSONAS, f.TMESA_ID, f.FECHA_HORA_DESEADA, ";
        sql += "f.FECHA_REGISTRO, f.FECHA_NOTIFICACION, f.ESTADO, f.OBSERVACIONES ";
        sql += "FROM RES_FILASESPERA f ";
        sql += "WHERE (f.ESTADO = ? OR ? IS NULL) AND ";
        sql += "(f.FILA_ID = ? OR ? IS NULL) AND ";
        sql += "(f.USUARIO_ID = ? OR ? IS NULL) ";
        sql += "ORDER BY f.ESTADO DESC, f.FILA_ID ASC";
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

    @Override
    protected String generarSQLParaObtenerPorId() {
        String sql = "SELECT f.FILA_ID, f.USUARIO_ID, f.LOCAL_ID, f.TIPO_RESERVA, ";
        sql += "f.CANT_PERSONAS, f.TMESA_ID, f.FECHA_HORA_DESEADA, ";
        sql += "f.FECHA_REGISTRO, f.FECHA_NOTIFICACION, f.ESTADO, f.OBSERVACIONES ";
        sql += "FROM RES_FILASESPERA f ";
        sql += "WHERE f.FILA_ID = ? AND f.USUARIO_ID = ?";
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        FilaEsperaParametros parametros = (FilaEsperaParametros) objParametros;
        try {
            // f.ESTADO = ? OR ? IS NULL
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

            // f.USUARIO_ID = ? OR ? IS NULL
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