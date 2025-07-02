/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

import java.util.Date;

import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.TipoReserva;

public class ReservaParametrosBuilder {

    private TipoReserva tipoReserva;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer idLocal;
    private String dniCliente;
    private Integer usuarioId;
    private EstadoReserva estado;

    public ReservaParametrosBuilder() {
        this.tipoReserva = null;
        this.fechaInicio = null;
        this.fechaFin = null;
        this.idLocal = null;
        this.dniCliente = null;
        this.usuarioId = null;
        this.estado = null;
    }

    public ReservaParametrosBuilder setTipoReserva(TipoReserva tipoReserva) {
        this.tipoReserva = tipoReserva;
        return this;
    }

    public ReservaParametrosBuilder setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public ReservaParametrosBuilder setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public ReservaParametrosBuilder setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
        return this;
    }

    public ReservaParametrosBuilder setdniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
        return this;
    }

    public ReservaParametrosBuilder setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public ReservaParametrosBuilder setEstado(EstadoReserva estado) {
        this.estado = estado;
        return this;
    }

    public ReservaParametros buildReservaParametros() {
        ReservaParametros parametros = new ReservaParametros();
        parametros.setTipoReserva(this.tipoReserva);
        parametros.setFechaInicio(this.fechaInicio);
        parametros.setFechaFin(this.fechaFin);
        parametros.setIdLocal(this.idLocal);
        parametros.setdniCliente(dniCliente);
        parametros.setUsuarioId(this.usuarioId);
        parametros.setEstado(this.estado);
        return parametros;
    }
}

