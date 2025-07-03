/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

import java.util.Date;

/**
 * DTO para la tabla RES_FILASESPERA
 * Representa las solicitudes de reserva que están en lista de espera
 * 
 * @author frank
 */
public class FilaEsperaDTO {

    private Integer idFila;
    private UsuariosDTO usuario;
    private LocalDTO local;
    private TipoReserva tipoReserva;
    private Integer cantidadPersonas;
    private TipoMesaDTO tipoMesa;
    private Date fechaHoraDeseada;
    private Date fechaRegistro;
    private Date fechaNotificacion;
    private EstadoFilaEspera estado;
    private String observaciones;

    // Constructor por defecto
    public FilaEsperaDTO() {
        this.idFila = null;
        this.usuario = null;
        this.local = null;
        this.tipoReserva = null;
        this.cantidadPersonas = null;
        this.tipoMesa = null;
        this.fechaHoraDeseada = null;
        this.fechaRegistro = null;
        this.fechaNotificacion = null;
        this.estado = null;
        this.observaciones = null;
    }

    // Constructor completo
    public FilaEsperaDTO(Integer idFila, UsuariosDTO usuario, LocalDTO local,
            TipoReserva tipoReserva, Integer cantidadPersonas, TipoMesaDTO tipoMesa,
            Date fechaHoraDeseada, Date fechaRegistro, Date fechaNotificacion,
            EstadoFilaEspera estado, String observaciones) {
        this.idFila = idFila;
        this.usuario = usuario;
        this.local = local;
        this.tipoReserva = tipoReserva;
        this.cantidadPersonas = cantidadPersonas;
        this.tipoMesa = tipoMesa;
        this.fechaHoraDeseada = fechaHoraDeseada;
        this.fechaRegistro = fechaRegistro;
        this.fechaNotificacion = fechaNotificacion;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    // Constructor copia
    public FilaEsperaDTO(FilaEsperaDTO original) {
        this.idFila = original.idFila;
        this.usuario = original.usuario != null ? new UsuariosDTO(original.usuario) : null;
        this.local = original.local != null ? new LocalDTO(original.local) : null;
        this.tipoReserva = original.tipoReserva;
        this.cantidadPersonas = original.cantidadPersonas;
        this.tipoMesa = original.tipoMesa != null ? new TipoMesaDTO(original.tipoMesa) : null;
        this.fechaHoraDeseada = original.fechaHoraDeseada != null ? new Date(original.fechaHoraDeseada.getTime()) : null;
        this.fechaRegistro = original.fechaRegistro != null ? new Date(original.fechaRegistro.getTime()) : null;
        this.fechaNotificacion = original.fechaNotificacion != null ? new Date(original.fechaNotificacion.getTime()) : null;
        this.estado = original.estado;
        this.observaciones = original.observaciones;
    }

    // Getters y Setters
    public Integer getIdFila() {
        return idFila;
    }

    public void setIdFila(Integer idFila) {
        this.idFila = idFila;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public LocalDTO getLocal() {
        return local;
    }

    public void setLocal(LocalDTO local) {
        this.local = local;
    }

    public TipoReserva getTipoReserva() {
        return tipoReserva;
    }

    public void setTipoReserva(TipoReserva tipoReserva) {
        this.tipoReserva = tipoReserva;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public TipoMesaDTO getTipoMesa() {
        return tipoMesa;
    }

    public void setTipoMesa(TipoMesaDTO tipoMesa) {
        this.tipoMesa = tipoMesa;
    }

    public Date getFechaHoraDeseada() {
        return fechaHoraDeseada;
    }

    public void setFechaHoraDeseada(Date fechaHoraDeseada) {
        this.fechaHoraDeseada = fechaHoraDeseada;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaNotificacion() {
        return fechaNotificacion;
    }

    public void setFechaNotificacion(Date fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }

    public EstadoFilaEspera getEstado() {
        return estado;
    }

    public void setEstado(EstadoFilaEspera estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Método de utilidad para verificar si la solicitud está pendiente
     * @return true si el estado es PENDIENTE
     */
    public boolean esPendiente() {
        return this.estado == EstadoFilaEspera.PENDIENTE;
    }

    /**
     * Método de utilidad para verificar si la solicitud fue notificada
     * @return true si el estado es NOTIFICADO
     */
    public boolean fueNotificada() {
        return this.estado == EstadoFilaEspera.NOTIFICADO;
    }

    /**
     * Método de utilidad para verificar si la solicitud fue confirmada
     * @return true si el estado es CONFIRMADA
     */
    public boolean fueConfirmada() {
        return this.estado == EstadoFilaEspera.CONFIRMADA;
    }

    @Override
    public String toString() {
        return "FilaEsperaDTO{" +
                "idFila=" + idFila +
                ", usuario=" + (usuario != null ? usuario.getIdUsuario() : "null") +
                ", local=" + (local != null ? local.getIdLocal() : "null") +
                ", tipoReserva=" + tipoReserva +
                ", cantidadPersonas=" + cantidadPersonas +
                ", tipoMesa=" + (tipoMesa != null ? tipoMesa.getIdTipoMesa() : "null") +
                ", fechaHoraDeseada=" + fechaHoraDeseada +
                ", estado=" + estado +
                '}';
    }
}