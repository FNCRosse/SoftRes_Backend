package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.ComentarioDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;
import pe.edu.pucp.softres.parametros.ComentarioParametrosBuilder;

/**
 *
 * @author frank
 */
public class ComentarioDAOImpl extends DAOImplBase implements ComentarioDAO {

    private ComentariosDTO comentario;

    public ComentarioDAOImpl() {
        super("RES_COMENTARIOS");
        this.retornarLlavePrimaria = true;
        this.comentario = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("COMENTARIO_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("USUARIO_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("RESERVA_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("MENSAJE", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("PUNTUACION", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.BOOLEANO, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, comentario.getUsuario().getIdUsuario());
        this.statement.setInt(2, comentario.getReserva().getIdReserva());
        this.statement.setString(3, comentario.getMensaje());
        this.statement.setInt(4, comentario.getPuntuacion());
        this.statement.setBoolean(5, comentario.getEstado());
        this.statement.setObject(6, comentario.getFechaCreacion());
        this.statement.setString(7, comentario.getUsuarioCreacion());
        if (comentario.getFechaModificacion() != null) {
            this.statement.setObject(8, comentario.getFechaModificacion());
        } else {
            this.statement.setNull(8, java.sql.Types.TIMESTAMP);
        }
        this.statement.setString(9, comentario.getUsuarioModificacion());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        incluirValorDeParametrosParaInsercion();
        this.statement.setInt(10, comentario.getIdComentario());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setBoolean(1, comentario.getEstado());
        this.statement.setObject(2, comentario.getFechaModificacion());
        this.statement.setString(3, comentario.getUsuarioModificacion());
        this.statement.setInt(4, comentario.getIdComentario());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, comentario.getIdComentario());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        comentario = new ComentariosDTO();
        comentario.setIdComentario(resultSet.getInt("COMENTARIO_ID"));

        // Crear objeto UsuariosDTO y asignar el ID
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(resultSet.getInt("USUARIO_ID"));
        comentario.setUsuario(usuario);

        // Crear objeto ReservaDTO y asignar el ID
        ReservaDTO reserva = new ReservaDTO();
        reserva.setIdReserva(resultSet.getInt("RESERVA_ID"));
        comentario.setReserva(reserva);

        comentario.setMensaje(resultSet.getString("MENSAJE"));
        comentario.setPuntuacion(resultSet.getInt("PUNTUACION"));
        comentario.setEstado(resultSet.getBoolean("ESTADO"));
        Timestamp fechaCreacionTS = resultSet.getTimestamp("FECHA_CREACION");
        comentario.setFechaCreacion(fechaCreacionTS );
        comentario.setUsuarioCreacion(resultSet.getString("USUARIO_CREACION"));
        Timestamp fechaModificacionTS = resultSet.getTimestamp("FECHA_MODIFICACION");
        comentario.setFechaModificacion(fechaModificacionTS);
        comentario.setUsuarioModificacion(resultSet.getString("USUARIO_MODIFICACION"));

    }

    protected void instanciarObjetoDelResultSetParaListado() throws SQLException {
        comentario = new ComentariosDTO();
        comentario.setIdComentario(resultSet.getInt("COMENTARIO_ID"));

        // Crear objeto UsuariosDTO y asignar el ID
        UsuariosDTO usuario = new UsuariosDTO();
        usuario.setIdUsuario(resultSet.getInt("USUARIO_ID"));
        usuario.setNombreComp(resultSet.getString("NOMBRE_COMP"));
        usuario.setNumeroDocumento(resultSet.getString("NUMERO_DOC"));
        comentario.setUsuario(usuario);

        // Crear objeto ReservaDTO y asignar el ID
        ReservaDTO reserva = new ReservaDTO();
        reserva.setIdReserva(resultSet.getInt("RESERVA_ID"));
        LocalDTO local = new LocalDTO();
        local.setIdLocal(resultSet.getInt("LOCAL_ID"));
        local.setNombre(resultSet.getString("NOMBRE_LOCAL"));
        reserva.setLocal(local);
        comentario.setReserva(reserva);

        comentario.setMensaje(resultSet.getString("MENSAJE"));
        comentario.setPuntuacion(resultSet.getInt("PUNTUACION"));
        comentario.setEstado(resultSet.getBoolean("ESTADO"));
        Timestamp fechaCreacionTS = resultSet.getTimestamp("FECHA_CREACION");
        comentario.setFechaCreacion(fechaCreacionTS);
        comentario.setUsuarioCreacion(resultSet.getString("USUARIO_CREACION"));
        Timestamp fechaModificacionTS = resultSet.getTimestamp("FECHA_MODIFICACION");
        comentario.setFechaModificacion(fechaModificacionTS);
        comentario.setUsuarioModificacion(resultSet.getString("USUARIO_MODIFICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.comentario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        instanciarObjetoDelResultSetParaListado();
        lista.add(comentario);
    }

    @Override
    public Integer insertar(ComentariosDTO comentario) {
        this.comentario = comentario;
        return super.insertar();
    }

    @Override
    public ComentariosDTO obtenerPorId(Integer id) {
        this.comentario = new ComentariosDTO();
        this.comentario.setIdComentario(id);
        super.obtenerPorId();
        return this.comentario;
    }

    @Override
    public List<ComentariosDTO> listarTodos() {
        return (ArrayList<ComentariosDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(ComentariosDTO comentario) {
        this.comentario = comentario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ComentariosDTO comentario) {
        this.comentario = comentario;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        return super.generarSQLParaEliminacion();
    }
    
    @Override
    public List<ComentariosDTO> listar(ComentarioParametros comentparametros) {
        ComentarioParametros parametro;

        if (comentparametros == null) {
            parametro = new ComentarioParametrosBuilder().BuilComentarioParametros(); //Para listar todos
        } else {
            parametro = new ComentarioParametrosBuilder()
                    .setPuntuacion(comentparametros.getPuntuacion())
                    .setIdLocal(comentparametros.getIdLocal())
                    .setEstado(comentparametros.getEstado())
                    .setIdReserva(comentparametros.getIdReserva())
                    .setnumDocCliente(comentparametros.getnumDocCliente())
                    .BuilComentarioParametros();
        }
        String sql = this.generarSQLParaListar();
        return (List<ComentariosDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametro);
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        ComentarioParametros parametros = (ComentarioParametros) objParametros;
        try {
            if (parametros.getnumDocCliente() != null) {
                this.statement.setString(1, parametros.getnumDocCliente());
                this.statement.setString(2, parametros.getnumDocCliente());
            } else {
                this.statement.setNull(1, java.sql.Types.INTEGER);
                this.statement.setNull(2, java.sql.Types.INTEGER);
            }

            if (parametros.getIdLocal() != null) {
                this.statement.setInt(3, parametros.getIdLocal());
                this.statement.setInt(4, parametros.getIdLocal());
            } else {
                this.statement.setNull(3, java.sql.Types.INTEGER);
                this.statement.setNull(4, java.sql.Types.INTEGER);
            }
            
            if (parametros.getEstado() != null) {
                this.statement.setBoolean(5, parametros.getEstado());
                this.statement.setBoolean(6, parametros.getEstado());
            } else {
                this.statement.setNull(5, java.sql.Types.BOOLEAN);
                this.statement.setNull(6, java.sql.Types.BOOLEAN);
            }

            if (parametros.getPuntuacion() != null) {
                this.statement.setInt(7, parametros.getPuntuacion());
                this.statement.setInt(8, parametros.getPuntuacion());
            } else {
                this.statement.setNull(7, java.sql.Types.INTEGER);
                this.statement.setNull(8, java.sql.Types.INTEGER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String generarSQLParaListar() {
        String sql = "SELECT c.COMENTARIO_ID, "
                + "c.MENSAJE, c.PUNTUACION, c.ESTADO, c.FECHA_CREACION, "
                + "c.USUARIO_CREACION, c.FECHA_MODIFICACION, c.USUARIO_MODIFICACION, "
                + "u.USUARIO_ID, u.NOMBRE_COMP, u.NUMERO_DOC, "
                + "r.RESERVA_ID, r.LOCAL_ID, "
                + "l.NOMBRE AS NOMBRE_LOCAL "
                + "FROM RES_COMENTARIOS c "
                + "JOIN RES_USUARIOS u ON c.USUARIO_ID = u.USUARIO_ID "
                + "JOIN RES_RESERVAS r ON c.RESERVA_ID = r.RESERVA_ID "
                + "JOIN RES_LOCALES l ON r.LOCAL_ID = l.LOCAL_ID "
                + "WHERE (? IS NULL OR u.NUMERO_DOC = ?) "
                + "AND (? IS NULL OR l.LOCAL_ID = ?) "
                + "AND (? IS NULL OR c.ESTADO = ?) "
                + "AND (? IS NULL OR c.PUNTUACION = ?)";

        return sql;
    }
}
