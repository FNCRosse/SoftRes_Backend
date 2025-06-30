/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

import pe.edu.pucp.softres.model.TipoNotificacion;
import pe.edu.pucp.softres.model.EstadoNotificacion;

/**
 *
 * @author frank
 */
public class NotificacionParametros {
    
    private Integer idUsuario;
    private TipoNotificacion tipoNotificacion;
    private EstadoNotificacion estado;
    private Boolean esLeida;
    
    public NotificacionParametros(){
        this.idUsuario = null;
        this.tipoNotificacion = null;
        this.estado = null;
        this.esLeida = null;
    }

    public Boolean getEsLeida() {
        return esLeida;
    }

    public void setEsLeida(Boolean esLeida) {
        this.esLeida = esLeida;
    }
    
    public Integer getIdUsuario(){
        return this.idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public TipoNotificacion getTipoNotificacion(){
        return this.tipoNotificacion;
    }
    
    public void setTipoNotificacion(TipoNotificacion tipoNotificacion){
        this.tipoNotificacion = tipoNotificacion;
    }
    
    public EstadoNotificacion getEstado(){
        return this.estado;
    }
    
    public void setEstado(EstadoNotificacion estado){
        this.estado = estado;
    }
}
