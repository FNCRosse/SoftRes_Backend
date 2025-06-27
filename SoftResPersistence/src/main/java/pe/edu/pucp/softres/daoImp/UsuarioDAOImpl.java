package pe.edu.pucp.softres.daoImp;
//adiwdmoiwdma
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.UsuarioDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.RolDTO;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;
import pe.edu.pucp.softres.parametros.UsuariosParametrosBuilder;
import pe.edu.pucp.softres.db.util.Cifrado;
/**
 *
 * @author frank
 */
public class UsuarioDAOImpl extends DAOImplBase implements UsuarioDAO {

    private UsuariosDTO usuario;

    public UsuarioDAOImpl() {
        super("RES_USUARIOS");
        this.retornarLlavePrimaria = true;
        this.usuario = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("USUARIO_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("ROL_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("TIPO_DOC_ID", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("NOMBRE_COMP", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("NUMERO_DOC", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("CONTRASENA", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("EMAIL", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("TELEFONO", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("SUELDO", TipoDato.REAL, false, false));
        this.listaColumnas.add(new Columna("FECHA_CONTRATACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("CANT_RESERVAS", TipoDato.ENTERO, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.BOOLEANO, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.usuario.getRol().getIdRol());
        this.statement.setInt(2, this.usuario.getTipoDocumento().getIdTipoDocumento());
        this.statement.setString(3, this.usuario.getNombreComp());
        this.statement.setString(4, this.usuario.getNumeroDocumento());
        this.statement.setString(5, this.usuario.getContrasenha());
        this.statement.setString(6, this.usuario.getEmail());
        this.statement.setString(7, this.usuario.getTelefono());

        if (this.usuario.getSueldo() != null) {
            this.statement.setDouble(8, this.usuario.getSueldo());
        } else {
            this.statement.setNull(8, java.sql.Types.DOUBLE);
        }

        if (this.usuario.getFechaContratacion() != null) {
            this.statement.setObject(9, this.usuario.getFechaContratacion());
        } else {
            this.statement.setNull(9, java.sql.Types.TIMESTAMP);
        }

        this.statement.setInt(10, this.usuario.getCantidadReservacion());
        this.statement.setBoolean(11, this.usuario.getEstado());
        this.statement.setObject(12, (this.usuario.getFechaCreacion()));

        if (this.usuario.getUsuarioCreacion() != null) {
            this.statement.setString(13, this.usuario.getUsuarioCreacion());
        } else {
            this.statement.setNull(13, java.sql.Types.VARCHAR);
        }

        if (this.usuario.getFechaModificacion() != null) {
            this.statement.setObject(14, this.usuario.getFechaModificacion());
        } else {
            this.statement.setNull(14, java.sql.Types.TIMESTAMP);
        }

        if (this.usuario.getUsuarioModificacion() != null) {
            this.statement.setString(15, this.usuario.getUsuarioModificacion());
        } else {
            this.statement.setNull(15, java.sql.Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        incluirValorDeParametrosParaInsercion();
        this.statement.setInt(16, this.usuario.getIdUsuario());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setBoolean(1, this.usuario.getEstado());
        this.statement.setObject(2, this.usuario.getFechaModificacion());
        this.statement.setString(3, this.usuario.getUsuarioModificacion());
        this.statement.setInt(4, this.usuario.getIdUsuario());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.usuario.getIdUsuario());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.usuario = new UsuariosDTO();
        this.usuario.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));

        // Crear objetos relacionados y asignar
        RolDTO rol = new RolDTO();
        rol.setIdRol(this.resultSet.getInt("ROL_ID"));
        this.usuario.setRol(rol);

        TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
        tipoDoc.setIdTipoDocumento(this.resultSet.getInt("TIPO_DOC_ID"));
        this.usuario.setTipoDocumento(tipoDoc);

        this.usuario.setNombreComp(this.resultSet.getString("NOMBRE_COMP"));
        this.usuario.setNumeroDocumento(this.resultSet.getString("NUMERO_DOC"));
        this.usuario.setContrasenha(this.resultSet.getString("CONTRASENA"));
        this.usuario.setEmail(this.resultSet.getString("EMAIL"));
        this.usuario.setTelefono(this.resultSet.getString("TELEFONO"));
        this.usuario.setSueldo(this.resultSet.getDouble("SUELDO"));

        Timestamp tsContratacion = this.resultSet.getTimestamp("FECHA_CONTRATACION");
        this.usuario.setFechaContratacion(tsContratacion);

        this.usuario.setCantidadReservacion(this.resultSet.getInt("CANT_RESERVAS"));
        this.usuario.setEstado(this.resultSet.getBoolean("ESTADO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.usuario.setFechaCreacion(tsCreacion);

        this.usuario.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.usuario.setFechaModificacion(tsModificacion);

        this.usuario.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }
    
    protected void instanciarObjetoDelResultSetParaListado() throws SQLException {
        this.usuario = new UsuariosDTO();
        this.usuario.setIdUsuario(this.resultSet.getInt("USUARIO_ID"));

        // Crear objetos relacionados y asignar
        RolDTO rol = new RolDTO();
        rol.setIdRol(this.resultSet.getInt("ROL_ID"));
        rol.setNombre(this.resultSet.getString("ROL_NOMBRE"));
        this.usuario.setRol(rol);

        TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
        tipoDoc.setIdTipoDocumento(this.resultSet.getInt("TIPO_DOC_ID"));
        tipoDoc.setNombre(this.resultSet.getString("TIPO_DOC_NOMBRE"));
        this.usuario.setTipoDocumento(tipoDoc);

        this.usuario.setNombreComp(this.resultSet.getString("NOMBRE_COMP"));
        this.usuario.setNumeroDocumento(this.resultSet.getString("NUMERO_DOC"));
        this.usuario.setContrasenha(this.resultSet.getString("CONTRASENA"));
        this.usuario.setEmail(this.resultSet.getString("EMAIL"));
        this.usuario.setTelefono(this.resultSet.getString("TELEFONO"));
        this.usuario.setSueldo(this.resultSet.getDouble("SUELDO"));

        Timestamp tsContratacion = this.resultSet.getTimestamp("FECHA_CONTRATACION");
        this.usuario.setFechaContratacion(tsContratacion);

        this.usuario.setCantidadReservacion(this.resultSet.getInt("CANT_RESERVAS"));
        this.usuario.setEstado(this.resultSet.getBoolean("ESTADO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.usuario.setFechaCreacion(tsCreacion);

        this.usuario.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.usuario.setFechaModificacion(tsModificacion);

        this.usuario.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.usuario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSetParaListado();
        lista.add(this.usuario);
    }

    @Override
    public Integer insertar(UsuariosDTO usuario) {
        this.usuario = usuario;
        return super.insertar();
    }

    @Override
    public UsuariosDTO obtenerPorId(Integer id) {
        this.usuario = new UsuariosDTO();
        this.usuario.setIdUsuario(id);
        super.obtenerPorId();
        return this.usuario;
    }

    @Override
    public List<UsuariosDTO> listar(UsuariosParametros userParametro) {
        UsuariosParametros parametros;

        if (userParametro == null) {
            parametros = new UsuariosParametrosBuilder().buildUsuariosParametros(); // Parámetros vacíos
        } else {
            parametros = new UsuariosParametrosBuilder()
                .setNombreCompleto(userParametro.getNombreCompleto())
                .setIdTipoUsuario(userParametro.getIdTipoUsuario())
                .setIdTipoDocumento(userParametro.getIdTipoDocumento())
                .setEstado(userParametro.getEstado())
                .setNumDocumento(userParametro.getNumDocumento())
                .setEsCliente(userParametro.getEsCliente())
                .buildUsuariosParametros();
        }
        String sql = this.generarSQLParaListar();
        return (List<UsuariosDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametros);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT u.USUARIO_ID, ";
        sql += "u.ROL_ID, ";
        sql += "r.NOMBRE AS ROL_NOMBRE, ";
        sql += "r.ES_CLIENTE AS ES_CLIENTE, ";
        sql += "u.NOMBRE_COMP, ";
        sql += "u.TIPO_DOC_ID, ";
        sql += "t.NOMBRE AS TIPO_DOC_NOMBRE, ";
        sql += "u.NUMERO_DOC, ";
        sql += "u.EMAIL, ";
        sql += "u.CONTRASENA, ";
        sql += "u.TELEFONO, ";
        sql += "u.SUELDO, ";
        sql += "u.FECHA_CONTRATACION, ";
        sql += "u.CANT_RESERVAS, ";
        sql += "u.ESTADO, ";
        sql += "u.FECHA_CREACION, ";
        sql += "u.USUARIO_CREACION, ";
        sql += "u.FECHA_MODIFICACION, ";
        sql += "u.USUARIO_MODIFICACION ";
        sql += "FROM RES_USUARIOS u ";
        sql += "INNER JOIN RES_ROLES r ON u.ROL_ID = r.ROL_ID ";
        sql += "INNER JOIN RES_TIPO_DOCUMENTOS t ON u.TIPO_DOC_ID = t.TIPO_DOC_ID ";
        sql += "WHERE (? IS NULL OR u.ESTADO = ?) AND ";
        sql += "(u.NOMBRE_COMP LIKE CONCAT('%', ?, '%') OR ? IS NULL) AND ";
        sql += "(u.NUMERO_DOC LIKE CONCAT('%', ?, '%') OR ? IS NULL) AND ";
        sql += "(u.ROL_ID = ? OR ? IS NULL) AND ";
        sql += "(? IS NULL OR r.ES_CLIENTE  = ?) AND ";
        sql += "(u.TIPO_DOC_ID = ? OR ? IS NULL) ";
        sql += "ORDER BY u.ESTADO DESC, u.USUARIO_ID ASC, u.NOMBRE_COMP ASC";
        return sql;
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        UsuariosParametros parametros = (UsuariosParametros) objParametros;
        try {
            // Estado
            if (parametros.getEstado() != null) {
                this.statement.setBoolean(1, parametros.getEstado());
                this.statement.setBoolean(2, parametros.getEstado());
            } else {
                this.statement.setNull(1, java.sql.Types.BOOLEAN);
                this.statement.setNull(2, java.sql.Types.BOOLEAN);
            }

            // NombreCompleto para LIKE: poner %...%
            if (parametros.getNombreCompleto() != null && !parametros.getNombreCompleto().trim().isEmpty()) {
                String nombre = parametros.getNombreCompleto().trim();
                this.statement.setString(3, nombre);
                this.statement.setString(4, nombre);
            } else {
                this.statement.setNull(3, java.sql.Types.VARCHAR);
                this.statement.setNull(4, java.sql.Types.VARCHAR);
            }
            //numero de documento
            if (parametros.getNumDocumento()!= null && !parametros.getNumDocumento().trim().isEmpty()) {
                String ndoc = parametros.getNumDocumento().trim();
                this.statement.setString(5, ndoc);
                this.statement.setString(6, ndoc);
            } else {
                this.statement.setNull(5, java.sql.Types.VARCHAR);
                this.statement.setNull(6, java.sql.Types.VARCHAR);
            }
            // IdTipoUsuario
            if (parametros.getIdTipoUsuario() != null) {
                this.statement.setInt(7, parametros.getIdTipoUsuario());
                this.statement.setInt(8, parametros.getIdTipoUsuario());
            } else {
                this.statement.setNull(7, java.sql.Types.INTEGER);
                this.statement.setNull(8, java.sql.Types.INTEGER);
            }
            // es Cliente
            if (parametros.getEsCliente()!= null) {
                this.statement.setBoolean(9, parametros.getEsCliente());
                this.statement.setBoolean(10, parametros.getEsCliente());
            } else {
                this.statement.setNull(9, java.sql.Types.BOOLEAN);
                this.statement.setNull(10, java.sql.Types.BOOLEAN);
            }
            // IdTipoDocumento
            if (parametros.getIdTipoDocumento() != null) {
                this.statement.setInt(11, parametros.getIdTipoDocumento());
                this.statement.setInt(12, parametros.getIdTipoDocumento());
            } else {
                this.statement.setNull(11, java.sql.Types.INTEGER);
                this.statement.setNull(12, java.sql.Types.INTEGER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public Integer modificar(UsuariosDTO usuario) {
        this.usuario = usuario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(UsuariosDTO usuario) {
        this.usuario = usuario;
        return super.eliminar();
    }
    
    @Override
    protected String generarSQLParaEliminacion() {
        return super.generarSQLParaEliminacion();
    }
    @Override
    public UsuariosDTO obtenerPorEmailYContrasena(String email, String contrasenha) {
        UsuariosDTO usuario = null;

        try {
            String contrasenhaCifrada = Cifrado.cifrarMD5(contrasenha);
            this.abrirConexion();
            String sql = "SELECT * FROM RES_USUARIOS WHERE EMAIL = ? AND CONTRASENA = ? AND ESTADO = 1";
            this.colocarSQLenStatement(sql);
            this.statement.setString(1, email);
            this.statement.setString(2, contrasenhaCifrada);

            this.resultSet = this.statement.executeQuery();

            if (this.resultSet.next()) {
                this.instanciarObjetoDelResultSet();
                usuario = this.usuario;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.cerrarConexion();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return usuario;
    }
}

