/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

import java.time.LocalDateTime;
import pe.edu.pucp.softres.model.DiaSemana;

/**
 *
 * @author frank
 */
public class HorarioParametros {
    private DiaSemana diaSemana;
    private Boolean esFeriado;
    private Boolean estado;
    
    public HorarioParametros(){
        this.diaSemana = null;
        this.esFeriado = null;
        this.estado = null;
    }
    
    public Boolean getEstado(){
        return this.estado;
    }
    
    public void setEstado(Boolean estado){
        this.estado = estado;
    }
    
    public Boolean getEsFeriado(){
        return this.esFeriado;
    }
    
    public void setEsFeriado(Boolean esFeriado){
        this.esFeriado = esFeriado;
    }
    
    public DiaSemana getDiaSemana(){
        return this.diaSemana;
    }
    
    public void setDiaSemana(DiaSemana diaSemana){
        this.diaSemana = diaSemana;
    }
    
}
