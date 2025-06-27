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
public class ReporteReservasParametrosBuilder {

    
    private Integer idSede;
    private Date FechaInicio;
    private Date FechaFin;
    private Integer idRol;

    public ReporteReservasParametrosBuilder() {
        this.idSede = null;
        this.FechaInicio = null;
        this.FechaFin = null;
        this.idRol = null;
    }
    
    public ReporteReservasParametros BuilReporteReservasParametros() {
        ReporteReservasParametros parametros = new ReporteReservasParametros();
        parametros.setIdSede(this.idSede);
        parametros.setFechaInicio(this.FechaInicio);
        parametros.setFechaFin(this.FechaFin);
        parametros.setIdRol(this.idRol);
        return parametros;
    }
    /**
     * @param idSede the idSede to set
     */
    public ReporteReservasParametrosBuilder setIdSede(Integer idSede) {
        this.idSede = idSede;
        return this;
    }

    /**
     * @param FechaInicio the FechaInicio to set
     */
    public ReporteReservasParametrosBuilder setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
        return this;
    }

    /**
     * @param FechaFin the FechaFin to set
     */
    public ReporteReservasParametrosBuilder setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
        return this;
    }

    /**
     * @param idRol the idRol to set
     */
    public ReporteReservasParametrosBuilder setIdRol(Integer idRol) {
        this.idRol = idRol;
        return this;
    }
    
    
    
}
