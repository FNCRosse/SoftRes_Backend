/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;
import pe.edu.pucp.softres.model.EstadoReservaState;
import java.time.LocalDateTime;
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
    private EstadoReserva estado;
    
    public ReservaParametros(){
        this.tipoReserva = null;
        this.fechaInicio = null;
        this.fechaFin = null;
        this.idLocal = null;
        this.dniCliente = null;
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

    public String getdniCliente() {
        return dniCliente;
    }

    public void setdniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public EstadoReserva getEstado() {
        return estado;
    }

    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}
