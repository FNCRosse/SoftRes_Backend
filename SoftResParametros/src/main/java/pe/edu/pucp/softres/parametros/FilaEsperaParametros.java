package pe.edu.pucp.softres.parametros;
import java.util.Date;

import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.TipoReserva;

/**
 *
 * @author frank
 */
public class FilaEsperaParametros {
    private Integer idFila;
    private Integer idUsuario;
    private Integer idLocal;
    private TipoReserva tipoReserva;
    private Integer cantPersonas;
    private Integer idTipoMesa;
    private Date fechaHoraDeseadaInicio;
    private Date fechaHoraDeseadaFin;
    private Date fechaRegistroInicio;
    private Date fechaRegistroFin;
    private Date fechaNotificacion;
    private EstadoFilaEspera estado;
    private String observaciones;
    
    public FilaEsperaParametros(){
        this.idFila = null;
        this.idUsuario = null;
        this.idLocal = null;
        this.tipoReserva = null;
        this.cantPersonas = null;
        this.idTipoMesa = null;
        this.fechaHoraDeseadaInicio = null;
        this.fechaHoraDeseadaFin = null;
        this.fechaRegistroInicio = null;
        this.fechaRegistroFin = null;
        this.fechaNotificacion = null;
        this.estado = null;
        this.observaciones = null;
    }
    
    public Integer getIdFila(){
        return this.idFila;
    }
    
    public void setIdFila(Integer idFila){
        this.idFila = idFila;
    }
    
    public Integer getIdUsuario(){
        return this.idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public Integer getIdLocal(){
        return this.idLocal;
    }
    
    public void setIdLocal(Integer idLocal){
        this.idLocal = idLocal;
    }
    
    public TipoReserva getTipoReserva(){
        return this.tipoReserva;
    }
    
    public void setTipoReserva(TipoReserva tipoReserva){
        this.tipoReserva = tipoReserva;
    }
    
    public Integer getCantPersonas(){
        return this.cantPersonas;
    }
    
    public void setCantPersonas(Integer cantPersonas){
        this.cantPersonas = cantPersonas;
    }
    
    public Integer getIdTipoMesa(){
        return this.idTipoMesa;
    }
    
    public void setIdTipoMesa(Integer idTipoMesa){
        this.idTipoMesa = idTipoMesa;
    }
    
    public Date getFechaHoraDeseadaInicio(){
        return this.fechaHoraDeseadaInicio;
    }
    
    public void setFechaHoraDeseadaInicio(Date fechaHoraDeseadaInicio){
        this.fechaHoraDeseadaInicio = fechaHoraDeseadaInicio;
    }
    
    public Date getFechaHoraDeseadaFin(){
        return this.fechaHoraDeseadaFin;
    }
    
    public void setFechaHoraDeseadaFin(Date fechaHoraDeseadaFin){
        this.fechaHoraDeseadaFin = fechaHoraDeseadaFin;
    }
    
    public Date getFechaRegistroInicio(){
        return this.fechaRegistroInicio;
    }
    
    public void setFechaRegistroInicio(Date fechaRegistroInicio){
        this.fechaRegistroInicio = fechaRegistroInicio;
    }
    
    public Date getFechaRegistroFin(){
        return this.fechaRegistroFin;
    }
    
    public void setFechaRegistroFin(Date fechaRegistroFin){
        this.fechaRegistroFin = fechaRegistroFin;
    }
    
    public Date getFechaNotificacion(){
        return this.fechaNotificacion;
    }
    
    public void setFechaNotificacion(Date fechaNotificacion){
        this.fechaNotificacion = fechaNotificacion;
    }
    
    public EstadoFilaEspera getEstado(){
        return this.estado;
    }
    
    public void setEstado(EstadoFilaEspera estado){
        this.estado = estado;
    }
    
    public String getObservaciones(){
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones){
        this.observaciones = observaciones;
    }
}
