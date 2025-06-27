/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class RolDTO {

    private Integer idRol;
    private String nombre;
    private Boolean esCliente;

    // constructor
    public RolDTO() {
        this.idRol = null;
        this.nombre = null;
        this.esCliente = null;
    }

    public RolDTO(Integer idRol, String nombre, Boolean esCliente) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.esCliente = esCliente;
    }

    public RolDTO(RolDTO rol) {
        this.idRol = rol.idRol;
        this.nombre = rol.nombre;
        this.esCliente = rol.esCliente;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public Integer getIdRol() {
        return this.idRol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Boolean getEsCliente() {
        return esCliente;
    }

    public void setEsCliente(Boolean esCliente) {
        this.esCliente = esCliente;
    }
}
