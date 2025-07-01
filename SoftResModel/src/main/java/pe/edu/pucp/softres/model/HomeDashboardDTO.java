/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author BryanGnr
 */
public class HomeDashboardDTO {
    // Indicadores Superiores
    private Integer cantidad_reservas_diario;
    private Integer cantidad_reservas_semanal;
    private Double ocupacion_mesas;
    private Integer cancelaciones_recientes;
    
    // Datos para gráfico de evolución mensual
    private ArrayList<Integer> reservasMensuales; // 12 valores (ene-dic)
    
    // Datos para gráfico de pastel: porcentaje por local
    private Map<String, Double> porcentajeReservasPorLocal;
    
    // Estado Actual de reservas
    private Double estado_actual_canceladas;
    private Double estado_actual_pendientes;
    private Double estado_actual_confirmadas;
    private Double estado_actual_finalizadas;
    
    public HomeDashboardDTO(){
        
    }

    /**
     * @return the cantidad_reservas_diario
     */
    public Integer getCantidad_reservas_diario() {
        return cantidad_reservas_diario;
    }

    /**
     * @param cantidad_reservas_diario the cantidad_reservas_diario to set
     */
    public void setCantidad_reservas_diario(Integer cantidad_reservas_diario) {
        this.cantidad_reservas_diario = cantidad_reservas_diario;
    }

    /**
     * @return the cantidad_reservas_semanal
     */
    public Integer getCantidad_reservas_semanal() {
        return cantidad_reservas_semanal;
    }

    /**
     * @param cantidad_reservas_semanal the cantidad_reservas_semanal to set
     */
    public void setCantidad_reservas_semanal(Integer cantidad_reservas_semanal) {
        this.cantidad_reservas_semanal = cantidad_reservas_semanal;
    }

    /**
     * @return the ocupacion_mesas
     */
    public Double getOcupacion_mesas() {
        return ocupacion_mesas;
    }

    /**
     * @param ocupacion_mesas the ocupacion_mesas to set
     */
    public void setOcupacion_mesas(Double ocupacion_mesas) {
        this.ocupacion_mesas = ocupacion_mesas;
    }

    /**
     * @return the cancelaciones_recientes
     */
    public Integer getCancelaciones_recientes() {
        return cancelaciones_recientes;
    }

    /**
     * @param cancelaciones_recientes the cancelaciones_recientes to set
     */
    public void setCancelaciones_recientes(Integer cancelaciones_recientes) {
        this.cancelaciones_recientes = cancelaciones_recientes;
    }

    /**
     * @return the reservasMensuales
     */
    public ArrayList<Integer> getReservasMensuales() {
        return reservasMensuales;
    }

    /**
     * @param reservasMensuales the reservasMensuales to set
     */
    public void setReservasMensuales(ArrayList<Integer> reservasMensuales) {
        this.reservasMensuales = reservasMensuales;
    }

    /**
     * @return the porcentajeReservasPorLocal
     */
    public Map<String, Double> getPorcentajeReservasPorLocal() {
        return porcentajeReservasPorLocal;
    }

    /**
     * @param porcentajeReservasPorLocal the porcentajeReservasPorLocal to set
     */
    public void setPorcentajeReservasPorLocal(Map<String, Double> porcentajeReservasPorLocal) {
        this.porcentajeReservasPorLocal = porcentajeReservasPorLocal;
    }

    /**
     * @return the estado_actual_canceladas
     */
    public Double getEstado_actual_canceladas() {
        return estado_actual_canceladas;
    }

    /**
     * @param estado_actual_canceladas the estado_actual_canceladas to set
     */
    public void setEstado_actual_canceladas(Double estado_actual_canceladas) {
        this.estado_actual_canceladas = estado_actual_canceladas;
    }

    /**
     * @return the estado_actual_pendientes
     */
    public Double getEstado_actual_pendientes() {
        return estado_actual_pendientes;
    }

    /**
     * @param estado_actual_pendientes the estado_actual_pendientes to set
     */
    public void setEstado_actual_pendientes(Double estado_actual_pendientes) {
        this.estado_actual_pendientes = estado_actual_pendientes;
    }

    /**
     * @return the estado_actual_confirmadas
     */
    public Double getEstado_actual_confirmadas() {
        return estado_actual_confirmadas;
    }

    /**
     * @param estado_actual_confirmadas the estado_actual_confirmadas to set
     */
    public void setEstado_actual_confirmadas(Double estado_actual_confirmadas) {
        this.estado_actual_confirmadas = estado_actual_confirmadas;
    }

    /**
     * @return the estado_actual_finalizadas
     */
    public Double getEstado_actual_finalizadas() {
        return estado_actual_finalizadas;
    }

    /**
     * @param estado_actual_finalizadas the estado_actual_finalizadas to set
     */
    public void setEstado_actual_finalizadas(Double estado_actual_finalizadas) {
        this.estado_actual_finalizadas = estado_actual_finalizadas;
    }
}
