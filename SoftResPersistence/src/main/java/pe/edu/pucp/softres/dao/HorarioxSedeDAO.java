/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.dao;

import java.util.List;
import pe.edu.pucp.softres.model.HorariosxSedesDTO;

/**
 *
 * @author Rosse
 */
public interface HorarioxSedeDAO {
    Integer insertar(HorariosxSedesDTO hxs);
    List<HorariosxSedesDTO> listar(Integer idsede);
    Integer eliminar(HorariosxSedesDTO hxs);
}
