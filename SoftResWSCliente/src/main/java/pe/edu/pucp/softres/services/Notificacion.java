/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.List;
import pe.edu.pucp.softres.bo.client.NotificacionBO;
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.parametros.NotificacionParametros;

/**
 *
 * @author BryanGnr
 */

@WebService(serviceName = "notificaciones")
@XmlSeeAlso({NotificacionDTO.class, NotificacionParametros.class})
public class Notificacion {
    private final NotificacionBO notificacionBO;

    public Notificacion() {
        this.notificacionBO = new NotificacionBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarNotificacion(NotificacionDTO notificacion) throws IOException, InterruptedException {
        return this.notificacionBO.insertar(notificacion);
    }

    @WebMethod(operationName = "obtenerPorId")
    public NotificacionDTO obtenerNotificacionPorId( Integer notificacionId) throws IOException, InterruptedException {
        return this.notificacionBO.obtenerPorId(notificacionId);
    }

    @WebMethod(operationName = "listar")
    public List<NotificacionDTO> listarNotificaciones(NotificacionParametros parametros) throws IOException, InterruptedException {
        return this.notificacionBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarNotificacion(NotificacionDTO notificacion) throws IOException, InterruptedException {
        return this.notificacionBO.modificar(notificacion);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarNotificacion( NotificacionDTO notificacion) throws IOException, InterruptedException {
        return this.notificacionBO.eliminar(notificacion);
    }
}
