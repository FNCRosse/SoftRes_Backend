/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author frank
 */
public class FilaEsperaDTO {

    private Integer idFila;
    private UsuariosDTO usuario;
    private EstadoFilaEspera estado;

    // Constructor
    public FilaEsperaDTO() {
        this.idFila = null;
        this.usuario = null;
        this.estado = null;
    }

    public FilaEsperaDTO(Integer idFila, UsuariosDTO usuario, Integer idReserva,
            EstadoFilaEspera estado) {
        this.idFila = idFila;
        this.usuario = usuario;
        this.estado = estado;
    }

    public FilaEsperaDTO(FilaEsperaDTO original) {
        this.idFila = original.idFila;
        this.usuario = original.usuario;
        this.estado = original.estado;
    }

    public Integer getIdFila() {
        return idFila;
    }

    public void setIdFila(Integer idFila) {
        this.idFila = idFila;
    }

    public UsuariosDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosDTO usuario) {
        this.usuario = usuario;
    }

    public EstadoFilaEspera getEstado() {
        return estado;
    }

    public void setEstado(EstadoFilaEspera estado) {
        this.estado = estado;
    }
}
