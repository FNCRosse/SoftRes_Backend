package pe.edu.pucp.softres.business;

import pe.edu.pucp.softres.dao.UsuarioDAO;
import pe.edu.pucp.softres.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;
import java.util.List;


public class UsuarioBO {

    private UsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public UsuariosDTO insertar(UsuariosDTO usuario) {
        Integer id = this.usuarioDAO.insertar(usuario);
        usuario.setIdUsuario(id);
        return usuario;
    }

    public UsuariosDTO obtenerPorId(Integer usuarioId) {
        return this.usuarioDAO.obtenerPorId(usuarioId);
    }

    public List<UsuariosDTO> listar(UsuariosParametros parametros) {
        return this.usuarioDAO.listar(parametros);
    }

    public Integer modificar(UsuariosDTO usuario) {
        return this.usuarioDAO.modificar(usuario);
    }

    public Integer eliminar(UsuariosDTO usuario) {
        return this.usuarioDAO.eliminar(usuario);
    }
    public UsuariosDTO login(String email, String contrasenha) {
        return usuarioDAO.obtenerPorEmailYContrasena(email, contrasenha);
    }
}

