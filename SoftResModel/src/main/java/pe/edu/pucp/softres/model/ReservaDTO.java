
package pe.edu.pucp.softres.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author frank
 */
public class ReservaDTO {

    private Integer idReserva;
    private UsuariosDTO usuario;
    private LocalDTO local;
    private FilaEsperaDTO filaEspera;
    private TipoReserva tipoReserva;
    private Date fecha_Hora_Registro;
    private Integer cantidad_personas;
    private TipoMesaDTO tipoMesa;
    private Integer numeroMesas;
    private String observaciones;
    private MotivosCancelacionDTO motivoCancelacion;
    private EstadoReserva estado;
    private String nombreEvento;
    private String descripcionEvento;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;

    // Constructores
    public ReservaDTO() {
        this.idReserva = null;
        this.usuario = null;
        this.local = null;
        this.filaEspera = null;
        this.tipoReserva = null;
        this.fecha_Hora_Registro = null;
        this.cantidad_personas = null;
        this.tipoMesa = null;
        this.numeroMesas = null;
        this.observaciones = null;
        this.motivoCancelacion = null;
        this.estado = null;
        this.nombreEvento = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.descripcionEvento = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
    }

    public ReservaDTO(Integer idReserva, UsuariosDTO usuario, LocalDTO local, FilaEsperaDTO filaEspera,
                  TipoReserva tipoReserva, Date fecha_Hora, Integer cantidad_personas, MotivosCancelacionDTO motivoCancelacion,
                  int numeroMesas, String observaciones, EstadoReserva estado, String nombreEvento, String descripcionEvento) {
        this.idReserva = idReserva;
        this.usuario = usuario;             // cambia idUsuario por objeto UsuariosDTO
        this.local = local;                 // cambia idLocal por objeto LocalDTO
        this.filaEspera = filaEspera;      // cambia idFilaEspera por objeto FilaEsperaDTO
        this.tipoReserva = tipoReserva;
        this.fecha_Hora_Registro = fecha_Hora;
        this.cantidad_personas = cantidad_personas;
        this.motivoCancelacion = motivoCancelacion; // si quieres que sea objeto cambia también
        this.numeroMesas = numeroMesas;
        this.observaciones = observaciones;
        this.estado = estado;
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
    }
    //constructor copia

    public ReservaDTO(ReservaDTO original) {
        this.idReserva = original.idReserva;
        this.usuario = original.usuario;
        this.local = original.local;
        this.filaEspera = original.filaEspera;
        this.tipoReserva = original.tipoReserva;
        this.fecha_Hora_Registro = original.fecha_Hora_Registro;
        this.cantidad_personas = original.cantidad_personas;
        this.tipoMesa = original.tipoMesa;
        this.numeroMesas = original.numeroMesas;
        this.observaciones = original.observaciones;
        this.motivoCancelacion = original.motivoCancelacion;
        this.estado = original.estado;
        this.nombreEvento = original.nombreEvento;
        this.descripcionEvento = original.descripcionEvento;
    }

    // Getter & Setter
    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public LocalDTO getLocal() {
        return local;
    }

    public void setLocal(LocalDTO local) {
        this.local = local;
    }

    public Date getFecha_Hora() {
        return fecha_Hora_Registro;
    }

    public void setFecha_Hora(Date fecha_Hora) {
        this.fecha_Hora_Registro = fecha_Hora;
    }

    public Integer getCantidad_personas() {
        return cantidad_personas;
    }

    public void setCantidad_personas(Integer cantidad_personas) {
        this.cantidad_personas = cantidad_personas;
    }

    public TipoMesaDTO getTipoMesa() {
        return tipoMesa;
    }

    public void setTipoMesa(TipoMesaDTO tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    public Integer getNumeroMesas() {
        return numeroMesas;
    }

    public void setNumeroMesas(Integer numeroMesas) {
        this.numeroMesas = numeroMesas;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public MotivosCancelacionDTO getMotivoCancelacion() {
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(MotivosCancelacionDTO motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getDescripcionEvento() {
        return descripcionEvento;
    }

    public void setDescripcionEvento(String descripcionEvento) {
        this.descripcionEvento = descripcionEvento;
    }

    public FilaEsperaDTO getFilaEspera() {
        return filaEspera;
    }

    public void setFilaEspera(FilaEsperaDTO filaEspera) {
        this.filaEspera = filaEspera;
    }

    public TipoReserva getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(TipoReserva tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }
}
