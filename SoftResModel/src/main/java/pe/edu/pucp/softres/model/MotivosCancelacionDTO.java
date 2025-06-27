/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class MotivosCancelacionDTO {
    private Integer IdMotivo;
    private String descripcion;
    
    public MotivosCancelacionDTO(){
        this.IdMotivo = null;
        this.descripcion = null;
    }
    public MotivosCancelacionDTO(Integer id, String descripcion){
        this.IdMotivo = id;
        this.descripcion = descripcion;
    }

    public Integer getIdMotivo() {
        return IdMotivo;
    }

    public void setIdMotivo(Integer IdMotivo) {
        this.IdMotivo = IdMotivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
