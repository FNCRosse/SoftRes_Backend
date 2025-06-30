package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.NotificacionDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.model.TipoNotificacion;
import pe.edu.pucp.softres.model.EstadoNotificacion;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.NotificacionParametros;
import pe.edu.pucp.softres.parametros.NotificacionParametrosBuilder;

/**
 *
 * @author frank
 */
public class NotificacionDAOImpl extends DAOImplBase implements NotificacionDAO {

    private NotificacionDTO notificacion;

    public NotificacionDAOImpl() {
        super("RES_NOTIFICACIONES");
        this.retornarLlavePrimaria = true;
        this.notificacion = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        // nombre,TipoDato,esllavePrimaria,esAutoGenerado;
        this.listaColumnas.add(new Columna("NOTIFICACION_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", TipoDato.ENTERO, true, false));
        this.listaColumnas.add(new Columna("MENSAJE", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("LEIDA", TipoDato.BOOLEANO, false, false));
        // tipo -> CONFIRMACION,CANCELACION,RECORDATORIO,MODIFICACION
        // Estado -> PENDIENTE,ENVIADO,FALLIDO
        this.listaColumnas.add(new Columna("TIPO_NOTIFICACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        if (this.notificacion == null) {
            throw new IllegalStateException("this.notificacion es null");
        }
        if (this.notificacion.getUsuario() == null) {
            throw new IllegalStateException("Usuario de la notificación es null");
        }
        this.statement.setInt(1, this.notificacion.getUsuario().getIdUsuario());
        this.statement.setString(2, this.notificacion.getMensaje());
        this.statement.setBoolean(3, this.notificacion.getLeida());
        this.statement.setString(4, this.notificacion.getTipoNotificacion().name());
        this.statement.setString(5, this.notificacion.getEstado().name());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.notificacion.getMensaje());
        this.statement.setBoolean(2, this.notificacion.getLeida());
        this.statement.setString(3, this.notificacion.getTipoNotificacion().name());
        this.statement.setString(4, this.notificacion.getEstado().name());
        this.statement.setInt(5, this.notificacion.getIdNotificacion());
        this.statement.setInt(6, this.notificacion.getUsuario().getIdUsuario());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setString(1, this.notificacion.getEstado().name());
        this.statement.setInt(2, this.notificacion.getIdNotificacion());
        this.statement.setInt(3, this.notificacion.getUsuario().getIdUsuario());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.notificacion.getIdNotificacion());
        this.statement.setInt(2, this.notificacion.getUsuario().getIdUsuario());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.notificacion = new NotificacionDTO();
        this.notificacion.setIdNotificacion(this.resultSet.getInt("NOTIFICACION_ID"));
        UsuariosDTO user = new UsuariosDTO();
        user.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));
        this.notificacion.setUsuario(user);
        this.notificacion.setMensaje(this.resultSet.getString("MENSAJE"));
        this.notificacion.setLeida(this.resultSet.getBoolean("LEIDA"));
        String tipoNotificacionStr = this.resultSet.getString("TIPO_NOTIFICACION");
        this.notificacion.setTipoNotificacion(tipoNotificacionStr != null ? TipoNotificacion.valueOf(tipoNotificacionStr) : null);

        String estadoStr = this.resultSet.getString("ESTADO");
        this.notificacion.setEstado(estadoStr != null ? EstadoNotificacion.valueOf(estadoStr) : null);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.notificacion = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.notificacion);
    }

    @Override
    public Integer insertar(NotificacionDTO notificacion) {
        this.notificacion = notificacion;
        return super.insertar();
    }

    @Override
    public NotificacionDTO obtenerPorId(Integer idNotificacion, Integer idUsuario) {
        this.notificacion = new NotificacionDTO();
        this.notificacion.setIdNotificacion(idNotificacion);
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(idUsuario);
        this.notificacion.setUsuario(usuario);
        super.obtenerPorId();
        return this.notificacion;
    }

    @Override
    public ArrayList<NotificacionDTO> listar(NotificacionParametros notificacionParametro) {
        Object parametros = new NotificacionParametrosBuilder().
                setIdUsuario(notificacionParametro.getIdUsuario()).
                setTipoNotificacion(notificacionParametro.getTipoNotificacion()).
                setEstado(notificacionParametro.getEstado()).
                setEsLeida(notificacionParametro.getEsLeida()).
                buildNotificacionParametros();
        String sql = this.generarSQLParaListar();
        return (ArrayList<NotificacionDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT n.NOTIFICACION_ID, ";
        sql = sql.concat("u.NOMBRE_COMP, ");
        sql = sql.concat("u.USUARIO_ID, ");
        sql = sql.concat("n.MENSAJE, ");
        sql = sql.concat("n.LEIDA, ");
        sql = sql.concat("n.TIPO_NOTIFICACION, ");
        sql = sql.concat("n.ESTADO ");
        sql = sql.concat("FROM RES_NOTIFICACIONES n ");
        sql = sql.concat("INNER JOIN RES_USUARIOS u ON n.usuario_id = u.usuario_id ");
        sql = sql.concat("WHERE (n.ESTADO = ? OR ? is NULL) AND "); // Estado
        sql = sql.concat("(u.USUARIO_ID = ? OR ?  IS NULL) AND "); // Usuario
        sql = sql.concat("(n.LEIDA = ? OR ?  IS NULL) AND "); // Leida
        sql = sql.concat("(n.TIPO_NOTIFICACION = ? OR ? is NULL) "); // Tipo de notificacion
        sql = sql.concat(" ORDER BY n.ESTADO DESC, n.NOTIFICACION_ID ASC, n.LEIDA DESC ");
        return sql;
    }

    @Override
    public Integer modificar(NotificacionDTO notificacion) {
        this.notificacion = notificacion;
        return super.modificar();
    }

    @Override
    public Integer eliminar(NotificacionDTO notificacion) {
        this.notificacion = notificacion;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        String sql = "UPDATE RES_NOTIFICACIONES "
                    + "SET ESTADO = ? "
                    + "WHERE NOTIFICACION_ID = ? AND USUARIO_ID = ?";
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        NotificacionParametros parametros = (NotificacionParametros) objParametros;
        try {
            if (parametros.getEstado() != null) {
                this.statement.setString(1, parametros.getEstado().name());
                this.statement.setString(2, parametros.getEstado().name());
            } else {
                this.statement.setNull(1, java.sql.Types.VARCHAR);
                this.statement.setNull(2, java.sql.Types.VARCHAR);
            }

            if (parametros.getIdUsuario() != null) {
                this.statement.setInt(3, parametros.getIdUsuario());
                this.statement.setInt(4, parametros.getIdUsuario());
            } else {
                this.statement.setNull(3, java.sql.Types.INTEGER);
                this.statement.setNull(4, java.sql.Types.INTEGER);
            }
            
            if (parametros.getEsLeida()!= null) {
                this.statement.setBoolean(5, parametros.getEsLeida());
                this.statement.setBoolean(6, parametros.getEsLeida());
            } else {
                this.statement.setNull(5, java.sql.Types.BOOLEAN);
                this.statement.setNull(6, java.sql.Types.BOOLEAN);
            }

            if (parametros.getTipoNotificacion() != null) {
                this.statement.setString(7, parametros.getTipoNotificacion().name());
                this.statement.setString(8, parametros.getTipoNotificacion().name());
            } else {
                this.statement.setNull(7, java.sql.Types.VARCHAR);
                this.statement.setNull(8, java.sql.Types.VARCHAR);
            }
        } catch (SQLException ex) {
            Logger.getLogger(NotificacionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void marcarLeida(Integer idNotificacion) {
//        
//    }
}
