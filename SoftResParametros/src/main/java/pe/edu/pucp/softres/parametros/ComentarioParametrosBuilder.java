
package pe.edu.pucp.softres.parametros;

/**
 *
 * @author frank
 */
public class ComentarioParametrosBuilder {
    private Integer puntuacion;
    private Integer idLocal;
    private Integer idReserva;
    private String numDocCliente;
    private Boolean estado;
    
    public ComentarioParametrosBuilder(){
        this.puntuacion = null;
        this.idLocal = null;
        this.numDocCliente = null;
        this.estado = null;
        this.idReserva= null;
    }
    
    public ComentarioParametros BuilComentarioParametros() {
        ComentarioParametros parametros = new ComentarioParametros();
        parametros.setPuntuacion(this.puntuacion);
        parametros.setIdLocal(this.idLocal);
        parametros.setnumDocCliente(this.numDocCliente);
        parametros.setEstado(this.estado);
        parametros.setIdReserva(this.idReserva);
        return parametros;
    }
    
    public ComentarioParametrosBuilder setPuntuacion(Integer puntuacion){
        this.puntuacion = puntuacion;
        return this;
    }
    public ComentarioParametrosBuilder setIdLocal(Integer idLocal){
        this.idLocal = idLocal;
        return this;
    }
    public ComentarioParametrosBuilder setnumDocCliente(String numDocCliente){
        this.numDocCliente = numDocCliente;
        return this;
    }
    public ComentarioParametrosBuilder setEstado(Boolean estado){
        this.estado = estado;
        return this;
    }

    public ComentarioParametrosBuilder setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
        return this;
    }
    
}
