
package pe.edu.pucp.softres.parametros;

/**
 *
 * @author frank
 */
public class ComentarioParametros {
    private Integer puntuacion;
    private Integer idLocal;
    private Integer idReserva;
    private String numDocCliente;
    private Boolean estado;
    
    public ComentarioParametros(){
        this.puntuacion = null;
        this.idLocal = null;
        this.numDocCliente = null;
        this.idReserva = null;
        this.estado = null;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Integer getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
    }

    public String getnumDocCliente() {
        return numDocCliente;
    }

    public void setnumDocCliente(String numDocCliente) {
        this.numDocCliente = numDocCliente;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
