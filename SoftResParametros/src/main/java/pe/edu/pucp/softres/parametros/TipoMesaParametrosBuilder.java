
package pe.edu.pucp.softres.parametros;


public class TipoMesaParametrosBuilder {
    private String nombre;
    private Boolean estado;

    public TipoMesaParametrosBuilder() {
        this.nombre = null;
        this.estado = null;
    }
    
    public TipoMesaParametros BuilTipoMesaParametros() {
        TipoMesaParametros parametros = new TipoMesaParametros();
        parametros.setNombre(this.nombre);
        parametros.setEstado(this.estado);
        return parametros;
    }
    
    public TipoMesaParametrosBuilder setNombre(String nombre){
        this.nombre = nombre;
        return this;
    }
    
    public TipoMesaParametrosBuilder setEstado(Boolean estado){
        this.estado = estado;
        return this;
    }
}
