/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

import java.util.Date;

/**
 * DTO para la tabla RES_MESAS
 * Representa una mesa en un local específico
 * 
 * @author frank
 */
public class MesaDTO {

    private Integer idMesa;
    private LocalDTO local;
    private TipoMesaDTO tipoMesa;
    private String numeroMesa;
    private Integer capacidad;
    private EstadoMesa estado;
    private Integer x;
    private Integer y;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;

    public MesaDTO() {
        this.idMesa = null;
        this.local = null;
        this.tipoMesa = null;
        this.numeroMesa = null;
        this.capacidad = null;
        this.estado = null;
        this.x = null;
        this.y = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
    }

    public MesaDTO(Integer idMesa, LocalDTO local, TipoMesaDTO tipoMesa,
            String numeroMesa, Integer capacidad,
            EstadoMesa estado, Integer x, Integer y) {
        this.idMesa = idMesa;
        this.local = local;
        this.tipoMesa = tipoMesa;
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;
        this.x = x;
        this.y = y;
    }

    // Constructor copia
    public MesaDTO(MesaDTO original) {
        this.idMesa = original.idMesa;
        this.local = original.local != null ? new LocalDTO(original.local) : null;
        this.tipoMesa = original.tipoMesa != null ? new TipoMesaDTO(original.tipoMesa) : null;
        this.numeroMesa = original.numeroMesa;
        this.capacidad = original.capacidad;
        this.estado = original.estado;
        this.x = original.x;
        this.y = original.y;
        this.fechaCreacion = original.fechaCreacion != null ? new Date(original.fechaCreacion.getTime()) : null;
        this.usuarioCreacion = original.usuarioCreacion;
        this.fechaModificacion = original.fechaModificacion != null ? new Date(original.fechaModificacion.getTime()) : null;
        this.usuarioModificacion = original.usuarioModificacion;
    }

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public LocalDTO getLocal() {
        return local;
    }

    public void setLocal(LocalDTO local) {
        this.local = local;
    }

    public TipoMesaDTO getTipoMesa() {
        return tipoMesa;
    }

    public void setTipoMesa(TipoMesaDTO tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    public String getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(String numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public EstadoMesa getEstado() {
        return estado;
    }

    public void setEstado(EstadoMesa estado) {
        this.estado = estado;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * Método de utilidad para verificar si la mesa está disponible
     * @return true si el estado es DISPONIBLE
     */
    public boolean estaDisponible() {
        return this.estado == EstadoMesa.DISPONIBLE;
    }

    /**
     * Método de utilidad para verificar si la mesa está reservada
     * @return true si el estado es RESERVADA
     */
    public boolean estaReservada() {
        return this.estado == EstadoMesa.RESERVADA;
    }

    /**
     * Método de utilidad para verificar si la mesa está en uso
     * @return true si el estado es EN_USO
     */
    public boolean estaEnUso() {
        return this.estado == EstadoMesa.EN_USO;
    }

    /**
     * Método de utilidad para verificar si la mesa está en mantenimiento
     * @return true si el estado es EN_MANTENIMIENTO
     */
    public boolean estaEnMantenimiento() {
        return this.estado == EstadoMesa.EN_MANTENIMIENTO;
    }

    /**
     * Método de utilidad para verificar si la mesa está desechada
     * @return true si el estado es DESECHADA
     */
    public boolean estaDesechada() {
        return this.estado == EstadoMesa.DESECHADA;
    }

    /**
     * Método de utilidad para verificar si la mesa puede ser asignada
     * @return true si la mesa está disponible y no desechada
     */
    public boolean puedeSerAsignada() {
        return this.estado == EstadoMesa.DISPONIBLE;
    }

    /**
     * Método de utilidad para calcular la distancia entre esta mesa y otra
     * @param otraMesa Mesa con la que comparar
     * @return distancia euclidiana entre las dos mesas
     */
    public double calcularDistancia(MesaDTO otraMesa) {
        if (otraMesa == null || this.x == null || this.y == null || 
            otraMesa.x == null || otraMesa.y == null) {
            return Double.MAX_VALUE;
        }
        
        int deltaX = this.x - otraMesa.x;
        int deltaY = this.y - otraMesa.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Método de utilidad para verificar si la mesa tiene capacidad suficiente
     * @param personasRequeridas número de personas que necesitan mesa
     * @return true si la capacidad es mayor o igual al número requerido
     */
    public boolean tieneCapacidadPara(int personasRequeridas) {
        return this.capacidad != null && this.capacidad >= personasRequeridas;
    }

    @Override
    public String toString() {
        return "MesaDTO{" +
                "idMesa=" + idMesa +
                ", local=" + (local != null ? local.getIdLocal() : "null") +
                ", tipoMesa=" + (tipoMesa != null ? tipoMesa.getIdTipoMesa() : "null") +
                ", numeroMesa='" + numeroMesa + '\'' +
                ", capacidad=" + capacidad +
                ", estado=" + estado +
                ", posicion=(" + x + "," + y + ")" +
                '}';
    }
}
