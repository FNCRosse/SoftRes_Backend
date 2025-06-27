/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

public class LocalParametrosBuilder {

    private String nombre;
    private Integer idSede;
    private Boolean estado;

    public LocalParametrosBuilder() {
        this.idSede = null;
        this.estado = null;
        this.nombre =null;
    }

    public LocalParametrosBuilder setIdSede(Integer idSede) {
        this.idSede = idSede;
        return this;
    }

    public LocalParametrosBuilder setEstado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public LocalParametros buildLocalParametros() {
        LocalParametros parametros = new LocalParametros();
        parametros.setIdSede(this.idSede);
        parametros.setEstado(this.estado);
        parametros.setNombre(this.nombre);
        return parametros;
    }

    public LocalParametrosBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }
}
