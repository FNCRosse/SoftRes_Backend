/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;
import java.util.Date;

import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.TipoReserva;

/**
 *
 * @author frank
 */
public class FilaEsperaParametrosBuilder {
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
    
    public FilaEsperaParametrosBuilder(){
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
    
    public FilaEsperaParametrosBuilder setIdFila(Integer idFila){
        this.idFila = idFila;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setIdUsuario(Integer idUsuario){
        this.idUsuario = idUsuario;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setIdLocal(Integer idLocal){
        this.idLocal = idLocal;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setTipoReserva(TipoReserva tipoReserva){
        this.tipoReserva = tipoReserva;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setCantPersonas(Integer cantPersonas){
        this.cantPersonas = cantPersonas;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setIdTipoMesa(Integer idTipoMesa){
        this.idTipoMesa = idTipoMesa;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setFechaHoraDeseadaInicio(Date fechaHoraDeseadaInicio){
        this.fechaHoraDeseadaInicio = fechaHoraDeseadaInicio;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setFechaHoraDeseadaFin(Date fechaHoraDeseadaFin){
        this.fechaHoraDeseadaFin = fechaHoraDeseadaFin;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setFechaRegistroInicio(Date fechaRegistroInicio){
        this.fechaRegistroInicio = fechaRegistroInicio;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setFechaRegistroFin(Date fechaRegistroFin){
        this.fechaRegistroFin = fechaRegistroFin;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setFechaNotificacion(Date fechaNotificacion){
        this.fechaNotificacion = fechaNotificacion;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setEstado(EstadoFilaEspera estado){
        this.estado = estado;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setObservaciones(String observaciones){
        this.observaciones = observaciones;
        return this;
    }
    
    public FilaEsperaParametros buildFilaEsperaParametros(){
        FilaEsperaParametros parametros = new FilaEsperaParametros();
        parametros.setIdFila(this.idFila);
        parametros.setIdUsuario(this.idUsuario);
        parametros.setIdLocal(this.idLocal);
        parametros.setTipoReserva(this.tipoReserva);
        parametros.setCantPersonas(this.cantPersonas);
        parametros.setIdTipoMesa(this.idTipoMesa);
        parametros.setFechaHoraDeseadaInicio(this.fechaHoraDeseadaInicio);
        parametros.setFechaHoraDeseadaFin(this.fechaHoraDeseadaFin);
        parametros.setFechaRegistroInicio(this.fechaRegistroInicio);
        parametros.setFechaRegistroFin(this.fechaRegistroFin);
        parametros.setFechaNotificacion(this.fechaNotificacion);
        parametros.setEstado(this.estado);
        parametros.setObservaciones(this.observaciones);
        return parametros;
    }
}
