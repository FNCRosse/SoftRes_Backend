/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class TipoMesaDTO {

    private Integer idTipoMesa;
    private String nombre;

    // Constructor
    public TipoMesaDTO() {
        this.idTipoMesa = null;
        this.nombre = null;
    }

    public TipoMesaDTO(Integer idTipoMesa, String nombre) {
        this.idTipoMesa = idTipoMesa;
        this.nombre = nombre;
    }

    public TipoMesaDTO(TipoMesaDTO mesa) {
        this.idTipoMesa = mesa.idTipoMesa;
        this.nombre = mesa.nombre;
    }

    public void setIdTipoMesa(Integer idTipoMesa) {
        this.idTipoMesa = idTipoMesa;
    }

    public Integer getIdTipoMesa() {
        return this.idTipoMesa;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
