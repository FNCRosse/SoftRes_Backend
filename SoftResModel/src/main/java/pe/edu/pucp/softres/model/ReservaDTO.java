

package pe.edu.pucp.softres.model;

import java.util.Date;
import java.util.List;

/**
 * DTO para la tabla RES_RESERVAS
 * Representa una reserva en el sistema
 * 
 * @author frank
 */
public class ReservaDTO {

    private Integer idReserva;
    private UsuariosDTO usuario;
    private LocalDTO local;
    private FilaEsperaDTO filaEspera;
    private TipoReserva tipoReserva;
    private Date fechaHoraRegistro;
    private Integer cantidadPersonas;
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
    
    // Lista de mesas asignadas a la reserva (relación many-to-many)
    private List<MesaDTO> mesasAsignadas;

    // Constructor por defecto
    public ReservaDTO() {
        this.idReserva = null;
        this.usuario = null;
        this.local = null;
        this.filaEspera = null;
        this.tipoReserva = null;
        this.fechaHoraRegistro = null;
        this.cantidadPersonas = null;
        this.tipoMesa = null;
        this.numeroMesas = null;
        this.observaciones = null;
        this.motivoCancelacion = null;
        this.estado = null;
        this.nombreEvento = null;
        this.descripcionEvento = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
        this.mesasAsignadas = null;
    }

    // Constructor completo
    public ReservaDTO(Integer idReserva, UsuariosDTO usuario, LocalDTO local, FilaEsperaDTO filaEspera,
                  TipoReserva tipoReserva, Date fechaHora, Integer cantidadPersonas, TipoMesaDTO tipoMesa,
                  Integer numeroMesas, String observaciones, MotivosCancelacionDTO motivoCancelacion,
                  EstadoReserva estado, String nombreEvento, String descripcionEvento) {
        this.idReserva = idReserva;
        this.usuario = usuario;
        this.local = local;
        this.filaEspera = filaEspera;
        this.tipoReserva = tipoReserva;
        this.fechaHoraRegistro = fechaHora;
        this.cantidadPersonas = cantidadPersonas;
        this.tipoMesa = tipoMesa;
        this.numeroMesas = numeroMesas;
        this.observaciones = observaciones;
        this.motivoCancelacion = motivoCancelacion;
        this.estado = estado;
        this.nombreEvento = nombreEvento;
        this.descripcionEvento = descripcionEvento;
    }

    // Constructor copia
    public ReservaDTO(ReservaDTO original) {
        this.idReserva = original.idReserva;
        this.usuario = original.usuario != null ? new UsuariosDTO(original.usuario) : null;
        this.local = original.local != null ? new LocalDTO(original.local) : null;
        this.filaEspera = original.filaEspera != null ? new FilaEsperaDTO(original.filaEspera) : null;
        this.tipoReserva = original.tipoReserva;
        this.fechaHoraRegistro = original.fechaHoraRegistro != null ? new Date(original.fechaHoraRegistro.getTime()) : null;
        this.cantidadPersonas = original.cantidadPersonas;
        this.tipoMesa = original.tipoMesa != null ? new TipoMesaDTO(original.tipoMesa) : null;
        this.numeroMesas = original.numeroMesas;
        this.observaciones = original.observaciones;
        this.motivoCancelacion = original.motivoCancelacion;
        this.estado = original.estado;
        this.nombreEvento = original.nombreEvento;
        this.descripcionEvento = original.descripcionEvento;
        this.fechaCreacion = original.fechaCreacion != null ? new Date(original.fechaCreacion.getTime()) : null;
        this.usuarioCreacion = original.usuarioCreacion;
        this.fechaModificacion = original.fechaModificacion != null ? new Date(original.fechaModificacion.getTime()) : null;
        this.usuarioModificacion = original.usuarioModificacion;
        this.mesasAsignadas = original.mesasAsignadas;
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

    public Date getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(Date fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
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

    public List<MesaDTO> getMesasAsignadas() {
        return mesasAsignadas;
    }

    public void setMesasAsignadas(List<MesaDTO> mesasAsignadas) {
        this.mesasAsignadas = mesasAsignadas;
    }

    /**
     * Método de utilidad para verificar si la reserva está confirmada
     * @return true si el estado es CONFIRMADA
     */
    public boolean estaConfirmada() {
        return this.estado == EstadoReserva.CONFIRMADA;
    }

    /**
     * Método de utilidad para verificar si la reserva está cancelada
     * @return true si el estado es CANCELADA
     */
    public boolean estaCancelada() {
        return this.estado == EstadoReserva.CANCELADA;
    }

    /**
     * Método de utilidad para verificar si la reserva está pendiente
     * @return true si el estado es PENDIENTE
     */
    public boolean estaPendiente() {
        return this.estado == EstadoReserva.PENDIENTE;
    }

    /**
     * Método de utilidad para verificar si es una reserva de evento
     * @return true si el tipo de reserva es EVENTO
     */
    public boolean esReservaEvento() {
        return this.tipoReserva == TipoReserva.EVENTO;
    }

    /**
     * Método de utilidad para verificar si es una reserva común
     * @return true si el tipo de reserva es COMUN
     */
    public boolean esReservaComun() {
        return this.tipoReserva == TipoReserva.COMUN;
    }

    /**
     * Método de utilidad para verificar si la reserva proviene de una fila de espera
     * @return true si hay una fila de espera asociada
     */
    public boolean provieneDeFilaEspera() {
        return this.filaEspera != null;
    }

    /**
     * Método de utilidad para obtener el número total de mesas asignadas
     * @return número de mesas asignadas o 0 si no hay ninguna
     */
    public int getCantidadMesasAsignadas() {
        return this.mesasAsignadas != null ? this.mesasAsignadas.size() : 0;
    }

    @Override
    public String toString() {
        return "ReservaDTO{" +
                "idReserva=" + idReserva +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : "null") +
                ", local=" + (local != null ? local.getIdLocal() : "null") +
                ", tipoReserva=" + tipoReserva +
                ", fechaHoraRegistro=" + fechaHoraRegistro +
                ", cantidadPersonas=" + cantidadPersonas +
                ", numeroMesas=" + numeroMesas +
                ", estado=" + estado +
                ", nombreEvento='" + nombreEvento + '\'' +
                '}';
    }
}
