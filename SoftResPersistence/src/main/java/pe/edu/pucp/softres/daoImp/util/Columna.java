
package pe.edu.pucp.softres.daoImp.util;

/**
 *
 * @author frank
 */
public class Columna {
    
    private String nombre;
    private TipoDato tipoDato;
    private Boolean esllavePrimaria;
    private Boolean esAutoGenerado;
    
    public Columna(String nombre, TipoDato tipoDato, Boolean esllavePrimaria,
            Boolean esAutoGenerado) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.esllavePrimaria = esllavePrimaria;
        this.esAutoGenerado = esAutoGenerado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoDato getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(TipoDato tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Boolean getEsllavePrimaria() {
        return esllavePrimaria;
    }

    public void setEsllavePrimaria(Boolean esllavePrimaria) {
        this.esllavePrimaria = esllavePrimaria;
    }

    public Boolean getEsAutoGenerado() {
        return esAutoGenerado;
    }

    public void setEsAutoGenerado(Boolean esAutoGenerado) {
        this.esAutoGenerado = esAutoGenerado;
    }
    
}
