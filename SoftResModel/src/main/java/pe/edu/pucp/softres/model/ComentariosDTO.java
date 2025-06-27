
package pe.edu.pucp.softres.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author frank
 */
public class ComentariosDTO {

    private Integer idComentario;
    private UsuariosDTO usuario;
    private ReservaDTO reserva;
    private String mensaje;
    private Integer puntuacion;
    private Boolean estado;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public ComentariosDTO() {
        this.idComentario = null;
        this.usuario = null;
        this.reserva = null;
        this.mensaje = null;
        this.puntuacion = null;
        this.estado = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
    }

    public ComentariosDTO(Integer idComentario, UsuariosDTO usuario, ReservaDTO reserva,
            String mensaje,
            Integer puntuacion, Boolean estado) {
        this.idComentario = idComentario;
        this.usuario = usuario;
        this.reserva = reserva;
        this.mensaje = mensaje;
        this.puntuacion = puntuacion;
        this.estado = estado;
    }

    public ComentariosDTO(ComentariosDTO comentario) {
        this.idComentario = comentario.idComentario;
        this.usuario = comentario.usuario;
        this.reserva = comentario.reserva;
        this.mensaje = comentario.mensaje;
        this.puntuacion = comentario.puntuacion;
        this.estado = comentario.estado;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public ReservaDTO getReserva() {
        return reserva;
    }

    public void setReserva(ReservaDTO reserva) {
        this.reserva = reserva;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
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
