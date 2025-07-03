
package pe.edu.pucp.softres.business;

import pe.edu.pucp.softres.dao.FilaEsperaDAO;
import pe.edu.pucp.softres.dao.NotificacionDAO;
import pe.edu.pucp.softres.dao.UsuarioDAO;
import pe.edu.pucp.softres.daoImp.FilaEsperaDAOImpl;
import pe.edu.pucp.softres.daoImp.NotificacionDAOImpl;
import pe.edu.pucp.softres.daoImp.UsuarioDAOImpl;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.model.TipoNotificacion;
import pe.edu.pucp.softres.model.EstadoNotificacion;
import pe.edu.pucp.softres.model.UsuariosDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

public class FilaEsperaBO {

    private FilaEsperaDAO filaEsperaDAO;
    private NotificacionDAO notificacionDAO;
    private UsuarioDAO usuarioDAO;

    public FilaEsperaBO() {
        this.filaEsperaDAO = new FilaEsperaDAOImpl();
        this.notificacionDAO = new NotificacionDAOImpl();
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    /**
     * Inserta una nueva solicitud en la fila de espera
     */
    public FilaEsperaDTO insertar(FilaEsperaDTO filaespera) {
        // Validaciones básicas
        if (filaespera == null) {
            throw new IllegalArgumentException("La fila de espera no puede ser null");
        }
        
        if (filaespera.getUsuario() == null || filaespera.getUsuario().getIdUsuario() == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }
        
        if (filaespera.getLocal() == null || filaespera.getLocal().getIdLocal() == null) {
            throw new IllegalArgumentException("El local es obligatorio");
        }
        
        if (filaespera.getFechaHoraDeseada() == null) {
            throw new IllegalArgumentException("La fecha y hora deseada son obligatorias");
        }
        
        if (filaespera.getCantidadPersonas() == null || filaespera.getCantidadPersonas() <= 0) {
            throw new IllegalArgumentException("La cantidad de personas debe ser mayor a 0");
        }
        
        // Establecer valores por defecto
        if (filaespera.getEstado() == null) {
            filaespera.setEstado(EstadoFilaEspera.PENDIENTE);
        }
        
        if (filaespera.getFechaRegistro() == null) {
            filaespera.setFechaRegistro(new Date());
        }
        
        // Insertar en base de datos
        Integer id = this.filaEsperaDAO.insertar(filaespera);
        filaespera.setIdFila(id);
        
        // Enviar notificación de confirmación
        enviarNotificacionRegistro(filaespera);
        
        return filaespera;
    }

    public FilaEsperaDTO obtenerPorId(Integer idFila, Integer idUsuario) {
        if (idFila == null || idFila <= 0) {
            throw new IllegalArgumentException("El ID de fila debe ser un número positivo");
        }
        if (idUsuario == null || idUsuario <= 0) {
            throw new IllegalArgumentException("El ID de usuario debe ser un número positivo");
        }
        
        return this.filaEsperaDAO.obtenerPorId(idFila, idUsuario);
    }

    public ArrayList<FilaEsperaDTO> listar(FilaEsperaParametros parametros) {
        return this.filaEsperaDAO.listar(parametros);
    }

    /**
     * Lista solicitudes pendientes ordenadas por prioridad
     */
    public List<FilaEsperaDTO> listarPendientesPorPrioridad(Integer localId) {
        FilaEsperaParametros parametros = new FilaEsperaParametros();
        // Aquí necesitarías expandir FilaEsperaParametros para incluir filtros específicos
        
        ArrayList<FilaEsperaDTO> todasLasSolicitudes = this.filaEsperaDAO.listar(parametros);
        
        return todasLasSolicitudes.stream()
            .filter(fila -> fila.getEstado() == EstadoFilaEspera.PENDIENTE)
            .filter(fila -> localId == null || fila.getLocal().getIdLocal().equals(localId))
            .sorted((f1, f2) -> {
                // Prioridad: 1. VIPs primero, 2. Por fecha de registro
                boolean esVip1 = esClienteVIP(f1.getUsuario().getIdUsuario());
                boolean esVip2 = esClienteVIP(f2.getUsuario().getIdUsuario());
                
                if (esVip1 && !esVip2) return -1;
                if (!esVip1 && esVip2) return 1;
                
                return f1.getFechaRegistro().compareTo(f2.getFechaRegistro());
            })
            .collect(Collectors.toList());
    }

    /**
     * Busca la siguiente solicitud compatible para notificar
     */
    public FilaEsperaDTO buscarSiguienteCompatible(Integer localId, Date fechaHora, 
                                                   TipoReserva tipoReserva, Integer tipoMesaId) {
        List<FilaEsperaDTO> pendientes = listarPendientesPorPrioridad(localId);
        
        return pendientes.stream()
            .filter(fila -> tipoReserva == null || fila.getTipoReserva() == tipoReserva)
            .filter(fila -> tipoMesaId == null || 
                    (fila.getTipoMesa() != null && fila.getTipoMesa().getIdTipoMesa().equals(tipoMesaId)))
            .filter(fila -> esFechaCompatible(fila.getFechaHoraDeseada(), fechaHora))
            .findFirst()
            .orElse(null);
    }

    /**
     * Notifica al siguiente en la fila de espera
     */
    public boolean notificarSiguiente(Integer localId, Date fechaHora, 
                                      TipoReserva tipoReserva, Integer tipoMesaId) {
        FilaEsperaDTO siguiente = buscarSiguienteCompatible(localId, fechaHora, tipoReserva, tipoMesaId);
        
        if (siguiente != null) {
            siguiente.setEstado(EstadoFilaEspera.NOTIFICADO);
            siguiente.setFechaNotificacion(new Date());
            
            Integer resultado = this.filaEsperaDAO.modificar(siguiente);
            
            if (resultado > 0) {
                enviarNotificacionDisponibilidad(siguiente);
                return true;
            }
        }
        
        return false;
    }

    public Integer modificar(FilaEsperaDTO filaespera) {
        if (filaespera == null) {
            throw new IllegalArgumentException("La fila de espera no puede ser null");
        }
        
        if (filaespera.getIdFila() == null || filaespera.getIdFila() <= 0) {
            throw new IllegalArgumentException("El ID de fila debe ser válido");
        }
        
        return this.filaEsperaDAO.modificar(filaespera);
    }

    public Integer eliminar(FilaEsperaDTO filaespera) {
        if (filaespera == null) {
            throw new IllegalArgumentException("La fila de espera no puede ser null");
        }
        
        // Eliminación lógica - cambiar estado a CANCELADO
        filaespera.setEstado(EstadoFilaEspera.CANCELADO);
        return this.filaEsperaDAO.modificar(filaespera);
    }

    /**
     * Confirma que un usuario aceptó la disponibilidad notificada
     */
    public Integer confirmarDisponibilidad(Integer idFila, Integer idUsuario) {
        FilaEsperaDTO fila = obtenerPorId(idFila, idUsuario);
        
        if (fila == null) {
            throw new RuntimeException("Solicitud de fila de espera no encontrada");
        }
        
        if (fila.getEstado() != EstadoFilaEspera.NOTIFICADO) {
            throw new RuntimeException("Solo se pueden confirmar solicitudes notificadas");
        }
        
        fila.setEstado(EstadoFilaEspera.CONFIRMADA);
        return this.filaEsperaDAO.modificar(fila);
    }

    /**
     * Limpia solicitudes expiradas (más de 24 horas sin respuesta)
     */
    public int limpiarSolicitudesExpiradas() {
        // Esta funcionalidad requeriría extender el DAO para buscar por criterios de fecha
        // Por ahora retornamos 0
        return 0;
    }

    // Métodos privados de utilidad
    private boolean esClienteVIP(Integer usuarioId) {
        try {
            UsuariosDTO usuario = usuarioDAO.obtenerPorId(usuarioId);
            return usuario != null && usuario.getRol() != null && usuario.getRol().getIdRol() == 4;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean esFechaCompatible(Date fechaDeseada, Date fechaDisponible) {
        if (fechaDeseada == null || fechaDisponible == null) {
            return false;
        }
        
        // Considerar compatible si está dentro de un rango de ±2 horas
        long diferencia = Math.abs(fechaDeseada.getTime() - fechaDisponible.getTime());
        long dosHoras = 2 * 60 * 60 * 1000; // 2 horas en milisegundos
        
        return diferencia <= dosHoras;
    }

    private void enviarNotificacionRegistro(FilaEsperaDTO fila) {
        try {
            NotificacionDTO notificacion = new NotificacionDTO();
            notificacion.setUsuario(fila.getUsuario());
            notificacion.setTipoNotificacion(TipoNotificacion.CONFIRMACION);
            notificacion.setMensaje("Su solicitud ha sido agregada a la lista de espera. " +
                    "Le notificaremos cuando haya disponibilidad.");
            notificacion.setLeida(false);
            notificacion.setEstado(EstadoNotificacion.PENDIENTE);
            
            notificacionDAO.insertar(notificacion);
        } catch (Exception e) {
            // Log error but don't fail the main operation
            System.err.println("Error enviando notificación de registro: " + e.getMessage());
        }
    }

    private void enviarNotificacionDisponibilidad(FilaEsperaDTO fila) {
        try {
            NotificacionDTO notificacion = new NotificacionDTO();
            notificacion.setUsuario(fila.getUsuario());
            notificacion.setTipoNotificacion(TipoNotificacion.CONFIRMACION);
            notificacion.setMensaje("¡Buenas noticias! Ahora hay disponibilidad para su reserva " +
                    "en " + (fila.getLocal() != null ? fila.getLocal().getNombre() : "el local solicitado") +
                    ". Contáctenos para confirmar dentro de las próximas 2 horas.");
            notificacion.setLeida(false);
            notificacion.setEstado(EstadoNotificacion.PENDIENTE);
            
            notificacionDAO.insertar(notificacion);
        } catch (Exception e) {
            // Log error but don't fail the main operation
            System.err.println("Error enviando notificación de disponibilidad: " + e.getMessage());
        }
    }
}
