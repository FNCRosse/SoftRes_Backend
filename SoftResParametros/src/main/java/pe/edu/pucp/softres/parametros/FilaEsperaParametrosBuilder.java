/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;
import pe.edu.pucp.softres.model.EstadoFilaEspera;

/**
 *
 * @author frank
 */
public class FilaEsperaParametrosBuilder {
    private Integer idFila;
    private Integer idUsuario;
    private EstadoFilaEspera estado;
    
    public FilaEsperaParametrosBuilder(){
        this.idFila = null;
        this.idUsuario = null;
        this.estado = null;
    }
    
    public FilaEsperaParametrosBuilder setIdFIla(Integer idFila){
        this.idFila = idFila;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setIdUsuario(Integer idUsuario){
        this.idUsuario = idUsuario;
        return this;
    }
    
    public FilaEsperaParametrosBuilder setEstado(EstadoFilaEspera estado){
        this.estado = estado;
        return this;
    }
    
    public FilaEsperaParametros buildFilaEsperaParametros(){
        FilaEsperaParametros parametros = new FilaEsperaParametros();
        parametros.setIdFila(this.idFila);
        parametros.setIdUsuario(this.idUsuario);
        parametros.setEstado(this.estado);
        return parametros;
    }
}
