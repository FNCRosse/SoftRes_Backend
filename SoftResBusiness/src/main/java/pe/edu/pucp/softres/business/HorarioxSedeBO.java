/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.business;

import java.util.List;
import pe.edu.pucp.softres.dao.HorarioxSedeDAO;
import pe.edu.pucp.softres.daoImp.HorarioxSedeDAOImpl;
import pe.edu.pucp.softres.model.HorariosxSedesDTO;

/**
 *
 * @author Rosse
 */
public class HorarioxSedeBO {
    
    private final HorarioxSedeDAO horarioxSedeDAO;

    public HorarioxSedeBO() {
        this.horarioxSedeDAO = new HorarioxSedeDAOImpl();
    }

    public Integer insertar(HorariosxSedesDTO hxs) {
        //Como es tabla intermedia solo va retornar si se inserto
        return  this.horarioxSedeDAO.insertar(hxs); 
    }

    public List<HorariosxSedesDTO> listar(Integer idSede) {
        return this.horarioxSedeDAO.listar(idSede);
    }

    public Integer eliminar(HorariosxSedesDTO hxs) {
        return this.horarioxSedeDAO.eliminar(hxs);
    }
    
}
