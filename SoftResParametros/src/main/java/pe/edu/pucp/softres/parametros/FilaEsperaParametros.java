
package pe.edu.pucp.softres.parametros;
import pe.edu.pucp.softres.model.EstadoFilaEspera;

/**
 *
 * @author frank
 */
public class FilaEsperaParametros {
    private Integer idFila;
    private Integer idUsuario;
    private EstadoFilaEspera estado;
    
    public FilaEsperaParametros(){
        this.idFila = null;
        this.idUsuario = null;
        this.estado = null;
    }
    
    public Integer getIdFila(){
        return this.idFila;
    }
    
    public void setIdFila(Integer idFila){
        this.idFila = idFila;
    }
    
    public Integer getIdUsuario(){
        return this.idUsuario;
    }
    
    public void setIdUsuario(Integer idUsuario){
        this.idUsuario = idUsuario;
    }
    
    public EstadoFilaEspera getEstado(){
        return this.estado;
    }
    
    public void setEstado(EstadoFilaEspera estado){
        this.estado = estado;
    }
}
