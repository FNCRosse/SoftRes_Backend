/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

import pe.edu.pucp.softres.model.EstadoMesa;

/**
 *
 * @author frank
 */
public class MesaParametrosBuilder {

    private String nombre;
    private Integer idTipoMesa;
    private Integer idLocal;
    private EstadoMesa estado;

    public MesaParametrosBuilder() {
        this.nombre = null;
        this.idTipoMesa = null;
        this.idLocal = null;
        this.estado = null;
    }

    public MesaParametrosBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public MesaParametrosBuilder setIdTipoMesa(Integer idTipoMesa) {
        this.idTipoMesa = idTipoMesa;
        return this;
    }

    public MesaParametrosBuilder setIdLocal(Integer idLocal) {
        this.idLocal = idLocal;
        return this;
    }

    public MesaParametrosBuilder setEstado(EstadoMesa estado) {
        this.estado = estado;
        return this;
    }

    public MesaParametros buildMesaParametros() {
        MesaParametros parametros = new MesaParametros();
        parametros.setNombre(this.nombre);
        parametros.setIdTipoMesa(this.idTipoMesa);
        parametros.setIdLocal(this.idLocal);
        parametros.setEstado(this.estado);
        return parametros;
    }
}
