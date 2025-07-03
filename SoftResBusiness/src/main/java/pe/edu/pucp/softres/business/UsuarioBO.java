package pe.edu.pucp.softres.business;

import java.util.Date;
import java.util.List;

import pe.edu.pucp.softres.dao.UsuarioDAO;
import pe.edu.pucp.softres.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;

public class UsuarioBO {

    private UsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    public UsuariosDTO insertar(UsuariosDTO usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser null");
            }
            
            // Validaciones básicas
            if (usuario.getNombreComp() == null || usuario.getNombreComp().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre completo es obligatorio");
            }
            
            if (usuario.getNumeroDocumento() == null || usuario.getNumeroDocumento().trim().isEmpty()) {
                throw new IllegalArgumentException("El número de documento es obligatorio");
            }
            
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("El email es obligatorio");
            }
            
            if (usuario.getRol() == null || usuario.getRol().getIdRol() == null) {
                throw new IllegalArgumentException("El rol es obligatorio");
            }
            
            if (usuario.getTipoDocumento() == null || usuario.getTipoDocumento().getIdTipoDocumento() == null) {
                throw new IllegalArgumentException("El tipo de documento es obligatorio");
            }
            
            // Validar duplicados
            if (validarDocumentoUnico(usuario.getNumeroDocumento())) {
                throw new RuntimeException("Ya existe un usuario con este número de documento");
            }
            
            if (validarEmailUnico(usuario.getEmail())) {
                throw new RuntimeException("Ya existe un usuario con este email");
            }
            
            // Establecer valores por defecto si no están presentes
            if (usuario.getFechaCreacion() == null) {
                usuario.setFechaCreacion(new Date());
            }
            
            if (usuario.getUsuarioCreacion() == null) {
                usuario.setUsuarioCreacion("sistema");
            }
            
            if (usuario.getCantidadReservacion() == null) {
                usuario.setCantidadReservacion(0);
            }
            
            // Insertar usuario
            Integer idGenerado = usuarioDAO.insertar(usuario);
            if (idGenerado != null && idGenerado > 0) {
                usuario.setIdUsuario(idGenerado);
                return usuario;
            } else {
                throw new RuntimeException("Error al insertar usuario: No se pudo insertar el usuario");
            }
            
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzar excepciones de validación
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    public UsuariosDTO obtenerPorId(Integer id) {
        try {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("El ID del usuario no puede ser null");
            }
            
            UsuariosDTO usuario = usuarioDAO.obtenerPorId(id);
            return usuario; // Puede ser null si no se encuentra
            
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzar excepciones de validación
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener usuario por ID: " + e.getMessage(), e);
        }
    }

    public List<UsuariosDTO> listar(UsuariosParametros parametros) {
        try {
            // Si parametros es null, crear objeto vacío para listar todos
            if (parametros == null) {
                parametros = new UsuariosParametros();
            }
            
            List<UsuariosDTO> usuarios = usuarioDAO.listar(parametros);
            return usuarios != null ? usuarios : new java.util.ArrayList<>();
            
        } catch (Exception e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }

    public Integer modificar(UsuariosDTO usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser null");
            }
            
            if (usuario.getIdUsuario() == null || usuario.getIdUsuario() <= 0) {
                throw new IllegalArgumentException("El ID del usuario no puede ser null");
            }
            
            // Validaciones básicas
            if (usuario.getNombreComp() == null || usuario.getNombreComp().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre completo es obligatorio");
            }
            
            // Verificar que el usuario existe
            UsuariosDTO usuarioExistente = usuarioDAO.obtenerPorId(usuario.getIdUsuario());
            if (usuarioExistente == null) {
                throw new RuntimeException("El usuario no existe");
            }
            
            // Establecer fecha de modificación si no está presente
            if (usuario.getFechaModificacion() == null) {
                usuario.setFechaModificacion(new Date());
            }
            
            if (usuario.getUsuarioModificacion() == null) {
                usuario.setUsuarioModificacion("sistema");
            }
            
            Integer resultado = usuarioDAO.modificar(usuario);
            return resultado != null ? resultado : 0;
            
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzar excepciones de validación
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar usuario: " + e.getMessage(), e);
        }
    }

    public Integer eliminar(UsuariosDTO usuario) {
        try {
            if (usuario == null) {
                throw new IllegalArgumentException("El usuario no puede ser null");
            }
            
            if (usuario.getIdUsuario() == null || usuario.getIdUsuario() <= 0) {
                throw new IllegalArgumentException("El ID del usuario no puede ser null");
            }
            
            // Verificar que el usuario existe
            UsuariosDTO usuarioExistente = usuarioDAO.obtenerPorId(usuario.getIdUsuario());
            if (usuarioExistente == null) {
                throw new RuntimeException("El usuario no existe");
            }
            
            // Configurar para eliminación lógica
            usuario.setEstado(false);
            if (usuario.getFechaModificacion() == null) {
                usuario.setFechaModificacion(new Date());
            }
            
            if (usuario.getUsuarioModificacion() == null) {
                usuario.setUsuarioModificacion("sistema");
            }
            
            Integer resultado = usuarioDAO.eliminar(usuario);
            return resultado != null ? resultado : 0;
            
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzar excepciones de validación
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    public Boolean validarDocumentoUnico(String numeroDocumento) {
        try {
            if (numeroDocumento == null || numeroDocumento.trim().isEmpty()) {
                return false;
            }
            
            return usuarioDAO.validarDocumentoUnico(numeroDocumento.trim());
            
        } catch (Exception e) {
            System.err.println("Error validando documento único: " + e.getMessage());
            return false;
        }
    }

    public Boolean validarEmailUnico(String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                return false;
            }
            
            return usuarioDAO.validarEmailUnico(email.trim());
            
        } catch (Exception e) {
            System.err.println("Error validando email único: " + e.getMessage());
            return false;
        }
    }

    public UsuariosDTO login(String email, String contrasenha) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("El email no puede estar vacío");
            }
            
            if (contrasenha == null || contrasenha.trim().isEmpty()) {
                throw new IllegalArgumentException("La contraseña no puede estar vacía");
            }
            
            return usuarioDAO.obtenerPorEmailYContrasena(email.trim(), contrasenha);
            
        } catch (IllegalArgumentException e) {
            throw e; // Re-lanzar excepciones de validación
        } catch (Exception e) {
            throw new RuntimeException("Error en login: " + e.getMessage(), e);
        }
    }
}