/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class ReportesReservasDTO {

    private LocalDTO local;
    private SedeDTO sede;
    private TipoReserva tipoReserva;
    private RolDTO tipoCliente;
    private Integer totalReservas;
    private Integer cantidadCancelaciones;
    private Double porcentajeCanceladas;
    private Double porcentajeOcupacion;
    private TipoMesaDTO ubicacionMesaPreferida;
    
    public ReportesReservasDTO() {
        this.local = null;
        this.sede = null;
        this.tipoReserva = null;
        this.tipoCliente = null;
        this.totalReservas = null;
        this.cantidadCancelaciones = null;
        this.porcentajeCanceladas = null;
        this.porcentajeOcupacion = null;
        this.ubicacionMesaPreferida = null;
    }

    public ReportesReservasDTO(LocalDTO local,
     SedeDTO sede,
     TipoReserva tipoReserva,
     RolDTO tipoCliente,
     Integer totalReservas,
     Integer cantidadCancelaciones,
     Double porcentajeCanceladas,
     Double porcentajeOcupacion,
     TipoMesaDTO ubicacionMesaPreferida) {
        this.local = local;
        this.sede = sede;
        this.tipoReserva = tipoReserva;
        this.tipoCliente = tipoCliente;
        this.totalReservas = totalReservas;
        this.cantidadCancelaciones = cantidadCancelaciones;
        this.porcentajeCanceladas = porcentajeCanceladas;
        this.porcentajeOcupacion = porcentajeOcupacion;
        this.ubicacionMesaPreferida = ubicacionMesaPreferida;
    }

    public ReportesReservasDTO(ReportesReservasDTO original) {
        this.local = original.local;
        this.sede = original.sede;
        this.tipoReserva = original.tipoReserva;
        this.tipoCliente = original.tipoCliente;
        this.totalReservas = original.totalReservas;
        this.cantidadCancelaciones = original.cantidadCancelaciones;
        this.porcentajeCanceladas = original.porcentajeCanceladas;
        this.porcentajeOcupacion = original.porcentajeOcupacion;
        this.ubicacionMesaPreferida = original.ubicacionMesaPreferida;
    }
    
    /**
     * @return the local
     */
    public LocalDTO getLocal() {
        return local;
    }

    /**
     * @param local the local to set
     */
    public void setLocal(LocalDTO local) {
        this.local = local;
    }

    /**
     * @return the sede
     */
    public SedeDTO getSede() {
        return sede;
    }

    /**
     * @param sede the sede to set
     */
    public void setSede(SedeDTO sede) {
        this.sede = sede;
    }

    /**
     * @return the tipoReserva
     */
    public TipoReserva getTipoReserva() {
        return tipoReserva;
    }

    /**
     * @param tipoReserva the tipoReserva to set
     */
    public void setTipoReserva(TipoReserva tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    /**
     * @return the tipoCliente
     */
    public RolDTO getTipoCliente() {
        return tipoCliente;
    }

    /**
     * @param tipoCliente the tipoCliente to set
     */
    public void setTipoCliente(RolDTO tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    /**
     * @return the totalReservas
     */
    public Integer getTotalReservas() {
        return totalReservas;
    }

    /**
     * @param totalReservas the totalReservas to set
     */
    public void setTotalReservas(Integer totalReservas) {
        this.totalReservas = totalReservas;
    }

    /**
     * @return the cantidadCancelaciones
     */
    public Integer getCantidadCancelaciones() {
        return cantidadCancelaciones;
    }

    /**
     * @param cantidadCancelaciones the cantidadCancelaciones to set
     */
    public void setCantidadCancelaciones(Integer cantidadCancelaciones) {
        this.cantidadCancelaciones = cantidadCancelaciones;
    }

    /**
     * @return the porcentajeCanceladas
     */
    public Double getPorcentajeCanceladas() {
        return porcentajeCanceladas;
    }

    /**
     * @param porcentajeCanceladas the porcentajeCanceladas to set
     */
    public void setPorcentajeCanceladas(Double porcentajeCanceladas) {
        this.porcentajeCanceladas = porcentajeCanceladas;
    }

    /**
     * @return the porcentajeOcupacion
     */
    public Double getPorcentajeOcupacion() {
        return porcentajeOcupacion;
    }

    /**
     * @param porcentajeOcupacion the porcentajeOcupacion to set
     */
    public void setPorcentajeOcupacion(Double porcentajeOcupacion) {
        this.porcentajeOcupacion = porcentajeOcupacion;
    }

    /**
     * @return the ubicacionMesaPreferida
     */
    public TipoMesaDTO getUbicacionMesaPreferida() {
        return ubicacionMesaPreferida;
    }

    /**
     * @param ubicacionMesaPreferida the ubicacionMesaPreferida to set
     */
    public void setUbicacionMesaPreferida(TipoMesaDTO ubicacionMesaPreferida) {
        this.ubicacionMesaPreferida = ubicacionMesaPreferida;
    }
    
}
