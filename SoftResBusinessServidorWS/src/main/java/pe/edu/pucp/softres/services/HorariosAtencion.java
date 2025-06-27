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
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.business.HorarioAtencionBO;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.parametros.HorarioParametros;

/**
 *
 * @author Rosse
 */
@Path("HorarioAtencion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HorariosAtencion {

    private final HorarioAtencionBO horarioAtencionBO;

    public HorariosAtencion() {
        this.horarioAtencionBO = new HorarioAtencionBO();
    }

    @POST
    @Path("listar")
    public List<HorarioAtencionDTO> listar(HorarioParametros parametros) {
        return this.horarioAtencionBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer horarioId) {
        HorarioAtencionDTO comentario = this.horarioAtencionBO.obtenerPorId(horarioId);
        if (comentario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(comentario).build();
    }

    @POST
    public Response insertar(HorarioAtencionDTO horario) {
        try {
            horario.setFechaCreacion(new Date());
            horario.setEstado(true);
            horario = this.horarioAtencionBO.insertar(horario);
            return Response.status(Response.Status.CREATED).entity(horario).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(HorarioAtencionDTO horario) {
        horario.setFechaModificacion(new Date());
        Integer respuesta = this.horarioAtencionBO.modificar(horario);
        if (respuesta > 0) {
            return Response.ok(horario).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(HorarioAtencionDTO horario) {
        horario.setFechaModificacion(new Date());
        horario.setEstado(false);
        Integer respuesta = this.horarioAtencionBO.eliminar(horario);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
