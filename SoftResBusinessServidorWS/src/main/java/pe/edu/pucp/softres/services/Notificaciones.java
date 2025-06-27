/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.softres.business.NotificacionBO;
import pe.edu.pucp.softres.model.EstadoNotificacion;
import pe.edu.pucp.softres.model.NotificacionDTO;
import pe.edu.pucp.softres.parametros.NotificacionParametros;

/**
 *
 * @author Rosse
 */
@Path("Notificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Notificaciones {

    private final NotificacionBO notificacionBO;

    public Notificaciones() {
        this.notificacionBO = new NotificacionBO();
    }

    @POST
    @Path("listar")
    public List<NotificacionDTO> listar(NotificacionParametros parametros) {
        return this.notificacionBO.listar(parametros);
    }

    @GET
    @Path("{idNotificacion}/{idUser}")
    public Response obtenerPorId(@PathParam("idNotificacion") Integer notificacionid,
                                                        @PathParam("idUser") Integer idUser) {
        NotificacionDTO notificacion = this.notificacionBO.obtenerPorId(notificacionid,idUser);
        if (notificacion == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(notificacion).build();
    }

    @POST
    public Response insertar(NotificacionDTO notificacion) {
        try {
            notificacion = this.notificacionBO.insertar(notificacion);
            return Response.status(Response.Status.CREATED).entity(notificacion).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(NotificacionDTO notificacion) {
        Integer respuesta = this.notificacionBO.modificar(notificacion);
        if (respuesta > 0) {
            return Response.ok(notificacion).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(NotificacionDTO notificacion) {
        notificacion.setEstado(EstadoNotificacion.FALLIDO);
        Integer respuesta = this.notificacionBO.eliminar(notificacion);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
