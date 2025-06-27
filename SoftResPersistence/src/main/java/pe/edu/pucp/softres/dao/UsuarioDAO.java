
package pe.edu.pucp.softres.dao;


import java.util.List;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;


/**
 *
 * @author frank
 */
public interface UsuarioDAO {
    Integer insertar(UsuariosDTO usuario);
    UsuariosDTO obtenerPorId(Integer idUsuario);
    List<UsuariosDTO> listar(UsuariosParametros parametros);
    Integer modificar(UsuariosDTO usuario);
    Integer eliminar(UsuariosDTO usuarioId);
     UsuariosDTO obtenerPorEmailYContrasena(String email, String contrasenha);
}
