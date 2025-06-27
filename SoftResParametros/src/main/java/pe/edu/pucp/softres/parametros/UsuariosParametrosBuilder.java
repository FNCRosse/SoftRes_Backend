/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.parametros;

/**
 *
 * @author frank
 */
public class UsuariosParametrosBuilder {

    private String nombreCompleto;
    private String numDocumento;
    private Integer idTipoUsuario; // idrol
    private Integer idTipoDocumento;
    private Boolean estado;
    private Boolean esCliente;

    public UsuariosParametrosBuilder() {
        this.nombreCompleto = null;
        this.idTipoUsuario = null;
        this.idTipoDocumento = null;
        this.estado = null;
        this.esCliente = null;
    }
    
    public UsuariosParametros buildUsuariosParametros() {
        UsuariosParametros parametros = new UsuariosParametros();
        parametros.setNombreCompleto(this.nombreCompleto);
        parametros.setIdTipoUsuario(this.idTipoUsuario);
        parametros.setIdTipoDocumento(this.idTipoDocumento);
        parametros.setEstado(this.estado);
        parametros.setNumDocumento(this.numDocumento);
        parametros.setEsCliente(this.esCliente);
        return parametros;
    }
    
    public UsuariosParametrosBuilder setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
        return this;
    }

    public UsuariosParametrosBuilder setEsCliente(Boolean esCliente) {
        this.esCliente = esCliente;
        return this;
    }
    
    public UsuariosParametrosBuilder setNombreCompleto(String nombreCompleto){
        this.nombreCompleto = nombreCompleto;
        return this;
    }
    public UsuariosParametrosBuilder setIdTipoUsuario(Integer idTipoUsuario){
        this.idTipoUsuario = idTipoUsuario;
        return this;
    }
    public UsuariosParametrosBuilder setIdTipoDocumento(Integer idTipoDocumento){
        this.idTipoDocumento = idTipoDocumento;
        return this;
    }
    public UsuariosParametrosBuilder setEstado(Boolean estado){
        this.estado = estado;
        return this;
    }
}

