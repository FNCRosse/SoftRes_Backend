
package pe.edu.pucp.softres.parametros;

/**
 *
 * @author frank
 */
public class LocalParametros {
    private String nombre;
    private Integer idSede;
    private Boolean estado;
    
    public LocalParametros(){
        this.idSede = null;
        this.estado = null;
        this.nombre=null;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
