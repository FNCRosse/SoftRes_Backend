package pe.edu.pucp.softres.business;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
        
        // Generar valores únicos que respeten las constraints de BD
        long timestamp = System.currentTimeMillis();
        
        // NUMERO_DOC: Solo 8 dígitos numéricos (como los existentes en BD)
        String uniqueDoc = String.valueOf(timestamp % 100000000L); // Últimos 8 dígitos del timestamp
        if (uniqueDoc.length() < 8) {
            uniqueDoc = String.format("%08d", Long.parseLong(uniqueDoc)); // Rellenar con 0s si es menor a 8
        }
        
        // EMAIL: Generar email único pero más corto
        String uniqueEmail = "test" + (timestamp % 1000000) + "@example.com"; // Solo 6 dígitos para el email
        
        usuario.setNumeroDocumento(uniqueDoc);
        usuario.setContrasenha("password123");
        usuario.setEmail(uniqueEmail);
        usuario.setTelefono("999999999");
        usuario.setSueldo(130.00);
        usuario.setFechaContratacion(new Date());
        usuario.setContrasenha(Cifrado.cifrarMD5(usuario.getContrasenha()));
        usuario.setCantidadReservacion(0);
        usuario.setEstado(true);
        usuario.setFechaCreacion(new Date());
        usuario.setUsuarioCreacion("test_insertar");
        
        System.out.println("Intentando insertar usuario con documento: " + uniqueDoc + " y email: " + uniqueEmail);
        
        UsuariosDTO insertado = this.usuarioBO.insertar(usuario);
        userIdCreado = insertado.getIdUsuario();
        assertNotNull(userIdCreado, "El ID del usuario insertado no debe ser null");
        assertTrue(userIdCreado > 0, "El ID del usuario debe ser positivo");
        
        System.out.println("Usuario insertado exitosamente con ID: " + userIdCreado);
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
        System.out.println("=== Test: Listar Usuarios ===");
        System.out.println("Buscando usuario con ID: " + userIdCreado);
        
        UsuariosParametros parametros = new UsuariosParametros();
        List<UsuariosDTO> lista = this.usuarioBO.listar(parametros);
        
        System.out.println("Total usuarios encontrados: " + (lista != null ? lista.size() : "null"));
        
        assertNotNull(lista, "La lista de usuarios no debe ser null");
        
        if (lista != null && !lista.isEmpty()) {
            System.out.println("Usuarios en la lista:");
            for (UsuariosDTO u : lista) {
                System.out.println("- ID: " + u.getIdUsuario() + ", Nombre: " + u.getNombreComp() + 
                    ", Estado: " + u.getEstado() + ", Email: " + u.getEmail());
            }
            
            // Verificar si nuestro usuario está en la lista
            boolean encontrado = lista.stream().anyMatch(r -> r.getIdUsuario().equals(userIdCreado));
            if (!encontrado) {
                System.err.println("ERROR: Usuario con ID " + userIdCreado + " NO encontrado en la lista");
                
                // Intentar buscar por otros criterios para debugging
                boolean encontradoPorNombre = lista.stream().anyMatch(r -> "Usuario Test".equals(r.getNombreComp()));
                System.out.println("¿Encontrado por nombre 'Usuario Test'? " + encontradoPorNombre);
                
                // Verificar que el usuario aún existe individual
                UsuariosDTO usuarioIndividual = this.usuarioBO.obtenerPorId(userIdCreado);
                if (usuarioIndividual != null) {
                    System.out.println("Usuario individual SÍ existe: ID=" + usuarioIndividual.getIdUsuario() + 
                        ", Estado=" + usuarioIndividual.getEstado());
                } else {
                    System.err.println("Usuario individual NO existe - posible problema en inserción");
                }
            } else {
                System.out.println("Usuario encontrado exitosamente en la lista");
            }
        } else {
            System.err.println("La lista está vacía o es null");
        }
        
        assertTrue(lista.stream().anyMatch(r -> r.getIdUsuario().equals(userIdCreado)), 
            "El usuario insertado debe estar presente en la lista");
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

