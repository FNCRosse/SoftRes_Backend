
package pe.edu.pucp.softres.parametros;

import java.time.LocalDateTime;
import pe.edu.pucp.softres.model.DiaSemana;

/**
 *
 * @author frank
 */
public class HorarioParametrosBuilder {
    private DiaSemana diaSemana;
    private Boolean esFeriado;
    private Boolean estado;
    
    public HorarioParametrosBuilder(){
        diaSemana = null;
        esFeriado = null;
        estado = null;
    }
    
    public HorarioParametrosBuilder setDiaSemana(DiaSemana diaSemana){
        this.diaSemana = diaSemana;
        return this;
    }
    
    public HorarioParametrosBuilder setEsFeriado(Boolean esFeriado){
        this.esFeriado = esFeriado;
        return this;
    }
    
    public HorarioParametrosBuilder setEstado(Boolean estado){
        this.estado = estado;
        return this;
    }
    
    public HorarioParametros buildHorarioParametros(){
        HorarioParametros parametros = new HorarioParametros();
        parametros.setDiaSemana(this.diaSemana);
        parametros.setEsFeriado(this.esFeriado);
        parametros.setEstado(this.estado);
        return parametros;
    }
}
