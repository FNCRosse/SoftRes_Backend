package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.UsuarioBO;
import pe.edu.pucp.softres.db.util.Cifrado;
import pe.edu.pucp.softres.model.CredencialesDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;

@WebService(serviceName = "usuarios")
@XmlSeeAlso({UsuariosDTO.class, UsuariosParametros.class})
public class Usuario {

    private final UsuarioBO usuarioBO;

    public Usuario() {
        this.usuarioBO = new UsuarioBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarUsuario(UsuariosDTO usuario) throws IOException, InterruptedException {
        usuario.setFechaCreacion(new Date());
        usuario.setEstado(true);
        usuario.setContrasenha(Cifrado.cifrarMD5(usuario.getContrasenha()));
        return this.usuarioBO.insertar(usuario);
    }

    @WebMethod(operationName = "obtenerPorId")
    public UsuariosDTO obtenerUsuarioPorId(Integer usuarioId) throws IOException, InterruptedException {
        return this.usuarioBO.obtenerPorId(usuarioId);
    }

    @WebMethod(operationName = "listar")
    public List<UsuariosDTO> listarUsuario(UsuariosParametros parametros) throws IOException, InterruptedException {
        return this.usuarioBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarUsuario(UsuariosDTO usuario) throws IOException, InterruptedException {
        usuario.setFechaModificacion(new Date());
        return this.usuarioBO.modificar(usuario);
    }
    @WebMethod(operationName = "cambiarcontrasena")
    public Integer cambiarcontrasena(UsuariosDTO usuario) throws IOException, InterruptedException {
        usuario.setFechaModificacion(new Date());
        usuario.setContrasenha(Cifrado.cifrarMD5(usuario.getContrasenha()));
        return this.usuarioBO.modificar(usuario);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarUsuario(UsuariosDTO usuario) throws IOException, InterruptedException {
        usuario.setFechaModificacion(new Date());
        usuario.setEstado(false);// lógica de eliminación lógica como en REST
        return this.usuarioBO.eliminar(usuario);
    }

    @WebMethod(operationName = "login")
    public UsuariosDTO loginUsuario(CredencialesDTO credenciales) throws IOException, InterruptedException {
        return this.usuarioBO.login(credenciales);
    }
    
    @WebMethod(operationName = "existeDoc")
    public Boolean existeDoc(String numDocumento) throws IOException, InterruptedException {
        return this.usuarioBO.ValidarDocumentoUnico(numDocumento);
    }
}
