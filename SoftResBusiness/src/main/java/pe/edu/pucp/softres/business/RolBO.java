package pe.edu.pucp.softres.business;

import java.util.List;
import pe.edu.pucp.softres.dao.RolDAO;
import pe.edu.pucp.softres.daoImp.RolDAOImpl;
import pe.edu.pucp.softres.model.RolDTO;

public class RolBO {

    private RolDAO rolDAO;

    public RolBO() {
        this.rolDAO = new RolDAOImpl();
    }

    public RolDTO insertar(RolDTO rol) {
        Integer id = this.rolDAO.insertar(rol);
        rol.setIdRol(id);
        return rol;
    }

    public RolDTO obtenerPorId(Integer rolId) {
        return this.rolDAO.obtenerPorId(rolId);
    }

    public List<RolDTO> listar() {
        return this.rolDAO.listar();
    }

    public Integer modificar(RolDTO rol) {
        return this.rolDAO.modificar(rol);
    }

    public Integer eliminar(RolDTO rol) {
        return this.rolDAO.eliminar(rol);
    }
}