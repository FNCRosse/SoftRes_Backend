/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

/**
 *
 * @author Rosse
 */
public class SedeParametrosBuilder {
    private String nombre;
    private Boolean estado;
    
    public SedeParametrosBuilder() {
        this.estado = null;
        this.nombre =null;
    }
    
    public SedeParametros buildSedeParametros() {
        SedeParametros parametros = new SedeParametros();
        parametros.setEstado(this.estado);
        parametros.setNombre(this.nombre);
        return parametros;
    }

    public SedeParametrosBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public SedeParametrosBuilder setEstado(Boolean estado) {
        this.estado = estado;
        return this;
    }
}
