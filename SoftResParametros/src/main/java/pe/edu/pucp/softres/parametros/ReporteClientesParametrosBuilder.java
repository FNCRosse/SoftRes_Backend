/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author frank
 */

public class ReporteClientesParametrosBuilder {

    private Date fechaInicio;
    private Date fechaFin;
    private Integer idLocal;
    private Integer idRol;

    public ReporteClientesParametrosBuilder() {
        this.fechaInicio = null;
        this.fechaFin = null;
        this.idLocal = null;
        this.idRol = null;
    }

    public ReporteClientesParametrosBuilder conFechaInicio(Date fechaInicio) {
        this.setFechaInicio(fechaInicio);
        return this;
    }

    public ReporteClientesParametrosBuilder conFechaFin(Date fechaFin) {
        this.setFechaFin(fechaFin);
        return this;
    }

    public ReporteClientesParametrosBuilder conIdLocal(Integer idLocal) {
        this.setIdLocal(idLocal);
        return this;
    }

    public ReporteClientesParametrosBuilder conIdRol(Integer idRol) {
        this.setIdRol(idRol);
        return this;
    }

    public ReporteClientesParametros buildReporteClientesParametros() {
        ReporteClientesParametros parametros = new ReporteClientesParametros();
        parametros.setFechaInicio(this.getFechaInicio());
        parametros.setFechaFin(this.getFechaFin());
        parametros.setIdLocal(this.getIdLocal());
        parametros.setIdRol(this.getIdRol());
        return parametros;
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

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}

