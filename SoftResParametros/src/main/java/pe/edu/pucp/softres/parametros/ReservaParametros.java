/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;
import java.util.Date;

import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.TipoReserva;
/**
 *
 * @author frank
 */
public class ReservaParametros {
    private TipoReserva tipoReserva;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idLocal;
    private String dniCliente;
    private Integer usuarioId;
    private EstadoReserva estado;
    
    public ReservaParametros(){
        this.tipoReserva = null;
        this.fechaInicio = null;
        this.fechaFin = null;
        this.idLocal = null;
        this.dniCliente = null;
        this.usuarioId = null;
        this.estado = null;
    }

    public TipoReserva getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(TipoReserva tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    // Método alias para compatibilidad con el código existente
    public void setLocalId(Integer localId) {
        this.idLocal = localId;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    // Método obsoleto mantenido por compatibilidad
    public String getdniCliente() {
        return dniCliente;
    }

    // Método obsoleto mantenido por compatibilidad
    public void setdniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
