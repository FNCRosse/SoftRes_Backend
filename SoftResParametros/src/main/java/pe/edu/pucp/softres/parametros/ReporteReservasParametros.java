
package pe.edu.pucp.softres.parametros;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author frank
 */
public class ReporteReservasParametros {

    private Integer idSede;
    private Date FechaInicio;
    private Date FechaFin;
    private Integer idRol;
    
    public ReporteReservasParametros(){
        this.idSede = null;
        this.FechaInicio = null;
        this.FechaFin = null;
        this.idRol = null;
    }
    /**
     * @return the idSede
     */
    public Integer getIdSede() {
        return idSede;
    }

    /**
     * @param idSede the idSede to set
     */
    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    /**
     * @return the FechaInicio
     */
    public Date getFechaInicio() {
        return FechaInicio;
    }

    /**
     * @param FechaInicio the FechaInicio to set
     */
    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    /**
     * @return the FechaFin
     */
    public Date getFechaFin() {
        return FechaFin;
    }

    /**
     * @param FechaFin the FechaFin to set
     */
    public void setFechaFin(Date FechaFin) {
        this.FechaFin = FechaFin;
    }

    /**
     * @return the idRol
     */
    public Integer getIdRol() {
        return idRol;
    }

    /**
     * @param idRol the idRol to set
     */
    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
    
}
