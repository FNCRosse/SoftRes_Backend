
package pe.edu.pucp.softres.parametros;

import pe.edu.pucp.softres.model.TipoNotificacion;
import pe.edu.pucp.softres.model.EstadoNotificacion;

/**
 *
 * @author frank
 */
public class NotificacionParametrosBuilder {
    
    private Integer idUsuario;
    private TipoNotificacion tipoNotificacion;
    private EstadoNotificacion estado;
    private Boolean esLeida;
    
    public NotificacionParametrosBuilder(){
        idUsuario = null;
        tipoNotificacion = null;
        estado = null;
        esLeida = null;
    }
    
    public NotificacionParametrosBuilder setIdUsuario(Integer idUsuario){
        this.idUsuario = idUsuario;
        return this;
    }
    
    public NotificacionParametrosBuilder setEsLeida(Boolean esLeida){
        this.esLeida = esLeida;
        return this;
    }
    public NotificacionParametrosBuilder setTipoNotificacion(TipoNotificacion tipoNotificacion){
        this.tipoNotificacion = tipoNotificacion;
        return this;
    }
    
    public NotificacionParametrosBuilder setEstado(EstadoNotificacion estado){
        this.estado = estado;
        return this;
    }
    
    public NotificacionParametros buildNotificacionParametros(){
        NotificacionParametros parametros = new NotificacionParametros();
        parametros.setIdUsuario(this.idUsuario);
        parametros.setTipoNotificacion(this.tipoNotificacion);
        parametros.setEstado(this.estado);
        parametros.setEsLeida(esLeida);
        return parametros;
    }
}
