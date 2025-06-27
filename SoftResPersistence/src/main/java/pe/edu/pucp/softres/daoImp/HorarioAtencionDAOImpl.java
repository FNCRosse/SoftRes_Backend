package pe.edu.pucp.softres.daoImp;

import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;
import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;
import pe.edu.pucp.softres.dao.HorarioAtencionDAO;
import pe.edu.pucp.softres.daoImp.util.Columna;
import pe.edu.pucp.softres.daoImp.util.TipoDato;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.model.DiaSemana;
import pe.edu.pucp.softres.parametros.HorarioParametros;
import pe.edu.pucp.softres.parametros.HorarioParametrosBuilder;

/**
 *
 * @author frank
 */
public class HorarioAtencionDAOImpl extends DAOImplBase implements HorarioAtencionDAO {

    private HorarioAtencionDTO horario;

    public HorarioAtencionDAOImpl() {
        super("RES_HORARIOS_ATENCION");
        this.retornarLlavePrimaria = true;;
        this.horario = null;
    }

    @Override
    protected void configurarListaDeColumnas() {
        // nombre,TipoDato,esllavePrimaria,esAutoGenerado;
        this.listaColumnas.add(new Columna("HORARIO_ID", TipoDato.ENTERO, true, true));
        this.listaColumnas.add(new Columna("DIA_SEMANA", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("HORA_INI", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("HORA_FIN", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("ES_FERIADO", TipoDato.BOOLEANO, false, false));
        this.listaColumnas.add(new Columna("ESTADO", TipoDato.BOOLEANO, false, false));
        this.listaColumnas.add(new Columna("FECHA_CREACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_CREACION", TipoDato.CADENA, false, false));
        this.listaColumnas.add(new Columna("FECHA_MODIFICACION", TipoDato.DATE, false, false));
        this.listaColumnas.add(new Columna("USUARIO_MODIFICACION", TipoDato.CADENA, false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        if (this.horario.getDiaSemana() != null) {
            this.statement.setString(1, this.horario.getDiaSemana().name());
        } else {
            this.statement.setNull(1, java.sql.Types.VARCHAR);
        }

        if (this.horario.getHoraInicio() != null) {
            this.statement.setTime(2, Time.valueOf(this.horario.getHoraInicio()));
        } else {
            this.statement.setNull(2, java.sql.Types.TIME);
        }

        if (this.horario.getHoraFin() != null) {
            this.statement.setTime(3, Time.valueOf(this.horario.getHoraFin()));
        } else {
            this.statement.setNull(3, java.sql.Types.TIME);
        }

        if (this.horario.getEsFeriado() != null) {
            this.statement.setBoolean(4, this.horario.getEsFeriado());
        } else {
            this.statement.setNull(4, java.sql.Types.BOOLEAN);
        }

        if (this.horario.getEstado() != null) {
            this.statement.setBoolean(5, this.horario.getEstado());
        } else {
            this.statement.setNull(5, java.sql.Types.BOOLEAN);
        }

        if (this.horario.getFechaCreacion() != null) {
            this.statement.setObject(6, this.horario.getFechaCreacion());
        } else {
            this.statement.setNull(6, java.sql.Types.DATE);
        }

        if (this.horario.getUsuarioCreacion() != null) {
            this.statement.setString(7, this.horario.getUsuarioCreacion());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }

        if (this.horario.getFechaModificacion() != null) {
            this.statement.setObject(8, this.horario.getFechaModificacion());
        } else {
            this.statement.setNull(8, java.sql.Types.DATE);
        }

        if (this.horario.getUsuarioModificacion() != null) {
            this.statement.setString(9, this.horario.getUsuarioModificacion());
        } else {
            this.statement.setNull(9, java.sql.Types.VARCHAR);
        }
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        if (this.horario.getDiaSemana() != null) {
            this.statement.setString(1, this.horario.getDiaSemana().name());
        } else {
            this.statement.setNull(1, java.sql.Types.VARCHAR);
        }

        if (this.horario.getHoraInicio() != null) {
            this.statement.setTime(2, Time.valueOf(this.horario.getHoraInicio()));
        } else {
            this.statement.setNull(2, java.sql.Types.TIME);
        }

        if (this.horario.getHoraFin() != null) {
            this.statement.setTime(3, Time.valueOf(this.horario.getHoraFin()));
        } else {
            this.statement.setNull(3, java.sql.Types.TIME);
        }

        if (this.horario.getEsFeriado() != null) {
            this.statement.setBoolean(4, this.horario.getEsFeriado());
        } else {
            this.statement.setNull(4, java.sql.Types.BOOLEAN);
        }

        if (this.horario.getEstado() != null) {
            this.statement.setBoolean(5, this.horario.getEstado());
        } else {
            this.statement.setNull(5, java.sql.Types.BOOLEAN);
        }

        if (this.horario.getFechaCreacion() != null) {
            this.statement.setObject(6, this.horario.getFechaCreacion());
        } else {
            this.statement.setNull(6, java.sql.Types.DATE);
        }

        if (this.horario.getUsuarioCreacion() != null) {
            this.statement.setString(7, this.horario.getUsuarioCreacion());
        } else {
            this.statement.setNull(7, java.sql.Types.VARCHAR);
        }

        if (this.horario.getFechaModificacion() != null) {
            this.statement.setObject(8,this.horario.getFechaModificacion());
        } else {
            this.statement.setNull(8, java.sql.Types.DATE);
        }

        if (this.horario.getUsuarioModificacion() != null) {
            this.statement.setString(9, this.horario.getUsuarioModificacion());
        } else {
            this.statement.setNull(9, java.sql.Types.VARCHAR);
        }

        this.statement.setInt(10, this.horario.getIdHorario());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setBoolean(1, horario.getEstado());
        this.statement.setObject(2, horario.getFechaModificacion());
        this.statement.setString(3, this.horario.getUsuarioCreacion());
        this.statement.setInt(4, this.horario.getIdHorario());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.horario.getIdHorario());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.horario = new HorarioAtencionDTO();

        this.horario.setIdHorario(this.resultSet.getInt("HORARIO_ID"));

        String diaSemanaStr = this.resultSet.getString("DIA_SEMANA");
        this.horario.setDiaSemana(diaSemanaStr != null ? DiaSemana.valueOf(diaSemanaStr) : null);

        Time timeHora_Ini = this.resultSet.getTime("HORA_INI");
        this.horario.setHoraInicio(timeHora_Ini != null ? timeHora_Ini.toLocalTime() : null);

        Time timeHora_Fin = this.resultSet.getTime("HORA_FIN");
        this.horario.setHoraFin(timeHora_Fin != null ? timeHora_Fin.toLocalTime() : null);

        this.horario.setEsFeriado(this.resultSet.getBoolean("HORARIO_ID"));

        this.horario.setEstado(this.resultSet.getBoolean("ESTADO"));

        Timestamp tsCreacion = this.resultSet.getTimestamp("FECHA_CREACION");
        this.horario.setFechaCreacion(tsCreacion);

        this.horario.setUsuarioCreacion(this.resultSet.getString("USUARIO_CREACION"));

        Timestamp tsModificacion = this.resultSet.getTimestamp("FECHA_MODIFICACION");
        this.horario.setFechaModificacion(tsModificacion);

        this.horario.setUsuarioModificacion(this.resultSet.getString("USUARIO_MODIFICACION"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.horario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.horario);
    }

    @Override
    public Integer insertar(HorarioAtencionDTO horario) {
        this.horario = horario;
        return super.insertar();
    }

    @Override
    public HorarioAtencionDTO obtenerPorId(Integer idHorario) {
        this.horario = new HorarioAtencionDTO();
        this.horario.setIdHorario(idHorario);
        super.obtenerPorId();
        return this.horario;
    }

    @Override
    public List<HorarioAtencionDTO> listar(HorarioParametros horarioParametros) {
        HorarioParametros parametro;

        if (horarioParametros == null) {
            parametro = new HorarioParametrosBuilder().buildHorarioParametros(); //Para listar todos
        } else {
            parametro = new HorarioParametrosBuilder()
                    .setDiaSemana(horarioParametros.getDiaSemana())
                    .setEsFeriado(horarioParametros.getEsFeriado())
                    .setEstado(horarioParametros.getEstado())
                    .buildHorarioParametros();
        }
        String sql = this.generarSQLParaListar();
        return (List<HorarioAtencionDTO>) super.listarTodos(sql, this::incluirValorDeParametrosParaListar, parametro);
    }

    private String generarSQLParaListar() {
        String sql = "SELECT h.HORARIO_ID, "
                + "h.DIA_SEMANA, "
                + "h.HORA_INI, "
                + "h.HORA_FIN, "
                + "h.ES_FERIADO, "
                + "h.ESTADO, "
                + "h.FECHA_CREACION, "
                + "h.USUARIO_CREACION, "
                + "h.FECHA_MODIFICACION, "
                + "h.USUARIO_MODIFICACION "
                + "FROM RES_HORARIOS_ATENCION h "
                + "WHERE (? IS NULL OR h.ES_FERIADO = ?) "
                + "AND (? IS NULL OR h.DIA_SEMANA = ?) "
                + "AND (? IS NULL OR h.ESTADO = ?) "
                + "ORDER BY h.DIA_SEMANA ASC, h.HORA_INI ASC";
        return sql;
    }

    @Override
    public Integer modificar(HorarioAtencionDTO horario) {
        this.horario = horario;
        return super.modificar();
    }

    @Override
    public Integer eliminar(HorarioAtencionDTO horario) {
        this.horario = horario;
        return super.eliminar();
    }

    @Override
    protected String generarSQLParaEliminacion() {
        return super.generarSQLParaEliminacion();
    }

    private void incluirValorDeParametrosParaListar(Object objParametros) {
        HorarioParametros parametros = (HorarioParametros) objParametros;
        try {
            if (parametros.getEsFeriado() != null) {
                this.statement.setBoolean(1, parametros.getEsFeriado());
                this.statement.setBoolean(2, parametros.getEsFeriado());
            } else {
                this.statement.setNull(1, java.sql.Types.BOOLEAN);
                this.statement.setNull(2, java.sql.Types.BOOLEAN);
            }

            if (parametros.getDiaSemana() != null) {
                this.statement.setString(3, parametros.getDiaSemana().toString());
                this.statement.setString(4, parametros.getDiaSemana().toString());
            } else {
                this.statement.setNull(3, java.sql.Types.VARCHAR);
                this.statement.setNull(4, java.sql.Types.VARCHAR);
            }

            // USUARIO_CREACION (7 y 8)
            if (parametros.getEstado() != null) {
                this.statement.setBoolean(5, parametros.getEstado());
                this.statement.setBoolean(6, parametros.getEstado());
            } else {
                this.statement.setNull(5, java.sql.Types.BOOLEAN);
                this.statement.setNull(6, java.sql.Types.BOOLEAN);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LocalDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
