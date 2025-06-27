/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 *
 * @author frank
 */
public class HorarioAtencionDTO {

    private Integer idHorario;
    private DiaSemana diaSemana;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaInicio;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime horaFin;
    private Boolean esFeriado;
    private Boolean estado;
    private Date fechaCreacion;
    private String usuarioCreacion;
    private Date fechaModificacion;
    private String usuarioModificacion;
    private String horaInicioStr;
    private String horaFinStr;

    // Constructor
    public HorarioAtencionDTO() {
        this.idHorario = null;
        this.diaSemana = null;
        this.horaInicio = null;
        this.horaFin = null;
        this.esFeriado = null;
        this.estado = null;
        this.fechaCreacion = null;
        this.usuarioCreacion = null;
        this.fechaModificacion = null;
        this.usuarioModificacion = null;
        this.horaFinStr=null;
        this.horaInicioStr=null;
    }

    public HorarioAtencionDTO(Integer idHorario, DiaSemana diaSemana, LocalTime horaInicio,
            LocalTime horaFin, Boolean esFeriado, Boolean estado) {
        this.idHorario = idHorario;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.esFeriado = esFeriado;
        this.estado = estado;
    }

    public HorarioAtencionDTO(HorarioAtencionDTO original) {
        this.idHorario = original.idHorario;
        this.diaSemana = original.diaSemana;
        this.horaInicio = original.horaInicio;
        this.horaFin = original.horaFin;
        this.esFeriado = original.esFeriado;
        this.estado = original.estado;
    }

    // Seteados manualmente desde la lógica
    public String getHoraInicioStr() {
        return horaInicioStr;
    }

    public void setHoraInicioStr(String horaInicioStr) {
        this.horaInicioStr = horaInicioStr;
    }

    public String getHoraFinStr() {
        return horaFinStr;
    }

    public void setHoraFinStr(String horaFinStr) {
        this.horaFinStr = horaFinStr;
    }

    public Integer getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Integer idHorario) {
        this.idHorario = idHorario;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Boolean getEsFeriado() {
        return esFeriado;
    }

    public void setEsFeriado(Boolean esFeriado) {
        this.esFeriado = esFeriado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
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
}
