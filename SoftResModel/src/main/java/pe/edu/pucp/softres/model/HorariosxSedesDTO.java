/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

/**
 *
 * @author Rosse
 */
public class HorariosxSedesDTO {
    
    private Integer idhorario;
    private Integer idsede;
    
    public HorariosxSedesDTO(){
        this.idhorario = null;
        this.idsede = null;
    }

    public Integer getIdHorario() {
        return idhorario;
    }

    public void setIdHorario(Integer horario) {
        this.idhorario = horario;
    }

    public Integer getIdSede() {
        return idsede;
    }

    public void setIdSede(Integer sede) {
        this.idsede = sede;
    }
}
