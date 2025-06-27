package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import pe.edu.pucp.softres.dao.SedeDAO;
import pe.edu.pucp.softres.daoImp.SedeDAOImpl;
import pe.edu.pucp.softres.model.SedeDTO;

import java.util.List;
import pe.edu.pucp.softres.parametros.SedeParametros;

public class SedeBO {

    private SedeDAO sedeDAO;

    public SedeBO() {
        this.sedeDAO = new SedeDAOImpl();
    }

    //ignorar esto para el Restful
    public SedeDTO insertar(SedeDTO sede) {
        Integer id = this.sedeDAO.insertar(sede);
        sede.setIdSede(id);
        return sede;
    }

    public SedeDTO obtenerPorId(Integer sedeId) {
        return this.sedeDAO.obtenerPorId(sedeId);
    }

    public List<SedeDTO> listar(SedeParametros parametros) {
        return this.sedeDAO.listar(parametros);
    }

    public Integer modificar(SedeDTO sedeDTO) {
        return this.sedeDAO.modificar(sedeDTO);
    }


    public Integer eliminar(SedeDTO sede) {
        return this.sedeDAO.eliminar(sede);
    }
}
