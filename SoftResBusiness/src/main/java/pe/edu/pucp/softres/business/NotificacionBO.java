package pe.edu.pucp.softres.business;

import pe.edu.pucp.softres.dao.NotificacionDAO;
import pe.edu.pucp.softres.daoImp.NotificacionDAOImpl;
import pe.edu.pucp.softres.model.NotificacionDTO;

import java.util.List;
import pe.edu.pucp.softres.model.EstadoNotificacion;
import pe.edu.pucp.softres.parametros.NotificacionParametros;

public class NotificacionBO {

    private NotificacionDAO notificacionDAO;

    public NotificacionBO() {
        this.notificacionDAO = new NotificacionDAOImpl();
    }

    public NotificacionDTO insertar(NotificacionDTO notificacion) {
        Integer id = this.notificacionDAO.insertar(notificacion);
        notificacion.setIdNotificacion(id);
        return notificacion;
    }

    public NotificacionDTO obtenerPorId(Integer notificacionId, Integer idUsuario) {
        return this.notificacionDAO.obtenerPorId(notificacionId,idUsuario);
    }

    public List<NotificacionDTO> listar(NotificacionParametros parametros) {
        return this.notificacionDAO.listar(parametros);
    }

    public Integer modificar(NotificacionDTO notificacion) {
        return this.notificacionDAO.modificar(notificacion);
    }

    public Integer eliminar(NotificacionDTO notificacion) {
        return this.notificacionDAO.eliminar(notificacion);
    }
}