/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.dao;

import java.util.List;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 *
 * @author Rosse
 */
public interface ReservaxMesaDAO {
    Integer insertar(ReservaxMesasDTO reservaxmesas);
    List<ReservaxMesasDTO> listar(Integer idReserva);
    Integer eliminar(ReservaxMesasDTO reservaxmesas);
}
