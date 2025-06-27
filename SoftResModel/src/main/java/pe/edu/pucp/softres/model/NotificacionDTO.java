/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class NotificacionDTO {

    private Integer idNotificacion;
    private UsuariosDTO usuario;
    private String mensaje;
    private Boolean leida;
    private TipoNotificacion tipoNotificacion;
    private EstadoNotificacion estado;
    
    // Constructor
    public NotificacionDTO() {
        this.idNotificacion = null;
        this.usuario = null;
        this.mensaje = null;
        this.leida = null; 
        this.tipoNotificacion = null;
        this.estado = null;
    }

    public NotificacionDTO(Integer idNotificacion, UsuariosDTO idUsuario, String mensaje,Boolean leida,
            TipoNotificacion tipoNotificacion, EstadoNotificacion estado) {
        this.idNotificacion = idNotificacion;
        this.usuario = idUsuario;
        this.mensaje = mensaje;
        this.leida = leida;
        this.tipoNotificacion = tipoNotificacion;
        this.estado = estado;
    }

    public NotificacionDTO(NotificacionDTO original) {
        this.idNotificacion = original.idNotificacion;
        this.usuario = original.usuario;
        this.mensaje = original.mensaje;
        this.leida = original.leida;
        this.tipoNotificacion = original.tipoNotificacion;
        this.estado = original.estado;
    }

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public TipoNotificacion getTipoNotificacion() {
        return tipoNotificacion;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public void setTipoNotificacion(TipoNotificacion tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public EstadoNotificacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }
}
