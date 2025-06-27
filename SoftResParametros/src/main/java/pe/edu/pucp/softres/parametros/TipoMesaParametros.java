
package pe.edu.pucp.softres.parametros;

public class TipoMesaParametros {
    private Integer idTipoMesa;
    private String nombre;
    private Boolean estado;

    public TipoMesaParametros() {
        this.idTipoMesa = null;
        this.nombre = null;
        this.estado = null;
    }

    public Integer getIdTipoMesa() {
        return idTipoMesa;
    }

    public void setIdTipoMesa(Integer idTipoMesa) {
        this.idTipoMesa = idTipoMesa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}

