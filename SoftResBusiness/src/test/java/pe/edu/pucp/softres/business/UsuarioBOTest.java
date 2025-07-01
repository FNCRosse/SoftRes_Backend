
package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.softres.db.util.Cifrado;
import pe.edu.pucp.softres.model.RolDTO;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;

/**
 *
 * @author frank
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioBOTest {

    private UsuarioBO usuarioBO;
    private static Integer userIdCreado;
    
    @BeforeEach
    public void setup() {
        this.usuarioBO = new UsuarioBO();
    }

    @Test
    @Order(1)
    public void testInsertar() {
        UsuariosDTO usuario = new UsuariosDTO();
        RolDTO rol = new RolDTO();
        rol.setIdRol(3);
        TipoDocumentoDTO tipoDoc = new TipoDocumentoDTO();
        tipoDoc.setIdTipoDocumento(3);
   
        usuario.setRol(rol);
        usuario.setTipoDocumento(tipoDoc);
        usuario.setNombreComp("Usuario Test");
        usuario.setNumeroDocumento("13395077");
        usuario.setContrasenha("password123");
        usuario.setEmail("test6@example.com");
        usuario.setTelefono("999999999");
        usuario.setSueldo(130.00);
        usuario.setFechaContratacion(new Date());
        usuario.setContrasenha(Cifrado.cifrarMD5(usuario.getContrasenha()));
        usuario.setCantidadReservacion(0);
        usuario.setEstado(true);
        usuario.setFechaCreacion(new Date());
        usuario.setUsuarioCreacion("test_insertar");
        UsuariosDTO insertado = this.usuarioBO.insertar(usuario);
        userIdCreado = insertado.getIdUsuario();
        assertNotNull(userIdCreado);
    }

    @Test
    @Order(2)
    public void testObtenerPorId() {
        UsuariosDTO usuario = this.usuarioBO.obtenerPorId(userIdCreado);
        assertNotNull(usuario);
        assertEquals(userIdCreado, usuario.getIdUsuario());
        assertEquals("Usuario Test", usuario.getNombreComp());
    }

    @Test
    @Order(4)
    public void testListar() {
        UsuariosParametros parametros = new UsuariosParametros();
        List<UsuariosDTO> lista = this.usuarioBO.listar(parametros);
        assertNotNull(lista);
        assertTrue(lista.stream().anyMatch(r -> r.getIdUsuario().equals(userIdCreado)));
    }

    @Test
    @Order(3)
    public void testModificar() {
        UsuariosDTO usuario = this.usuarioBO.obtenerPorId(userIdCreado);
        assertNotNull(usuario);
        usuario.setNombreComp("Usuario Modificado");
        usuario.setFechaModificacion(new Date());
        usuario.setUsuarioModificacion("test_modificacion"); 
        Integer result = this.usuarioBO.modificar(usuario);
        assertNotEquals(0, result);

        UsuariosDTO modificado = usuarioBO.obtenerPorId(userIdCreado);
        assertEquals("Usuario Modificado", modificado.getNombreComp());
    }

    @Test
    @Order(5)
    public void testEliminar() {
        UsuariosDTO usuario = this.usuarioBO.obtenerPorId(userIdCreado);
        usuario.setFechaModificacion(new Date());
        usuario.setUsuarioModificacion("test_eliminar"); 
        usuario.setEstado(false);
        Integer deleted = this.usuarioBO.eliminar(usuario);
        assertNotEquals(0, deleted);

        UsuariosDTO eliminado = usuarioBO.obtenerPorId(userIdCreado);
        assertFalse(eliminado.getEstado()); // Verificamos que fue eliminado lógicamente
    }
    @Test
    @Order(6)
    public void testExisteDocumento(){
        String numDocumento = "93727901";// ya esta en mi BD
        Boolean encontrado = this.usuarioBO.validarDocumentoUnico(numDocumento);
        assertTrue(encontrado);
    }
    @Test
    @Order(7)
    public void testLogin(){
        String email = "juan.pérez@mail.com";// ya esta en mi BD
        String contrasenha = "clave123";
        UsuariosDTO logeado = this.usuarioBO.login(email,contrasenha);
        assertNotNull(logeado);
    }
    @Test
    @Order(8)
    public void testExisteEmail(){
        String email = "juan.pérez@mail.com";// ya esta en mi BD
        Boolean encontrado = this.usuarioBO.validarEmailUnico(email);
        assertTrue(encontrado);
    }
}

