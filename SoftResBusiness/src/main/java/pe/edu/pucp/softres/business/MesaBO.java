package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.softres.dao.MesaDAO;
import pe.edu.pucp.softres.daoImp.MesaDAOImpl;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

public class MesaBO {

    private MesaDAO mesaDAO;

    public MesaBO() {
        this.mesaDAO = new MesaDAOImpl();
    }

    public MesaDTO insertar(MesaDTO mesa) {
        Integer id =  this.mesaDAO.insertar(mesa);
        mesa.setIdMesa(id);
        return mesa;
    }

    public MesaDTO obtenerPorId(Integer mesaId) {
        return this.mesaDAO.obtenerPorId(mesaId);
    }

    public List<MesaDTO> listar(MesaParametros parametros) {
        return this.mesaDAO.listar(parametros);
    }

    public Integer modificar(MesaDTO mesa) {
        return this.mesaDAO.modificar(mesa);
    }

    public Integer eliminar(MesaDTO mesa) {
        return this.mesaDAO.eliminar(mesa);
    }
}
