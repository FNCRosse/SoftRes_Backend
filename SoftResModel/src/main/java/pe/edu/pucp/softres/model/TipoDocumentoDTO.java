/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class TipoDocumentoDTO {

    private Integer idTipoDocumento;
    private String nombre;

    // Constructor
    public TipoDocumentoDTO() {
        this.idTipoDocumento = null;
        this.nombre = null;
    }

    public TipoDocumentoDTO(Integer idTipoDocumento, String nombre) {
        this.idTipoDocumento = idTipoDocumento;
        this.nombre = nombre;
    }

    public TipoDocumentoDTO(TipoDocumentoDTO tipo) {
        this.idTipoDocumento = tipo.idTipoDocumento;
        this.nombre = tipo.nombre;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Integer getIdTipoDocumento() {
        return this.idTipoDocumento;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return this.nombre;
    }
}
