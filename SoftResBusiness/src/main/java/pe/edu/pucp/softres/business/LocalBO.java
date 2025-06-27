package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.softres.dao.LocalDAO;
import pe.edu.pucp.softres.daoImp.LocalDAOImpl;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;

public class LocalBO {

    private final LocalDAO localDAO;

    public LocalBO() {
        this.localDAO = new LocalDAOImpl();
    }

    public LocalDTO insertar(LocalDTO local) {
        Integer id = this.localDAO.insertar(local);
        local.setIdLocal(id);
        return local;
    }

    public LocalDTO obtenerPorId(Integer localId) {
        return this.localDAO.obtenerPorId(localId);
    }

    public List<LocalDTO> listar(LocalParametros parametros) {
        return this.localDAO.listar(parametros);
    }

    public Integer modificar(LocalDTO local) {
        return this.localDAO.modificar(local);
    }

    public Integer eliminar(LocalDTO local) {
        return this.localDAO.eliminar(local);
    }
}
