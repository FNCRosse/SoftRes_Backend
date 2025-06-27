/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.business;

import java.util.List;
import pe.edu.pucp.softres.dao.ReservaxMesaDAO;
import pe.edu.pucp.softres.daoImp.ReservaxMesaDAOImpl;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 *
 * @author Rosse
 */
public class ReservaxMesaBO {
    private final ReservaxMesaDAO reservaxMesaDAO;

    public ReservaxMesaBO() {
        this.reservaxMesaDAO = new ReservaxMesaDAOImpl();
    }

    public Integer insertar(ReservaxMesasDTO hxs) {
        //Como es tabla intermedia solo va retornar si se inserto
        return  this.reservaxMesaDAO.insertar(hxs); 
    }

    public List<ReservaxMesasDTO> listar(Integer idReserva) {
        return this.reservaxMesaDAO.listar(idReserva);
    }

    public Integer eliminar(ReservaxMesasDTO hxs) {
        return this.reservaxMesaDAO.eliminar(hxs);
    }
}
