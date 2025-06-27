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
import pe.edu.pucp.softres.business.ComentarioBO;
import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;

/**
 *
 * @author Rosse
 */
@Path("Comentarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Comentarios {

    private final ComentarioBO comentarioBO;

    public Comentarios() {
        this.comentarioBO = new ComentarioBO();
    }

    @POST
    @Path("listar")
    public List<ComentariosDTO> listar(ComentarioParametros parametros) {
        return this.comentarioBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer comentarioId) {
        ComentariosDTO comentario = this.comentarioBO.obtenerPorId(comentarioId);
        if (comentario == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(comentario).build();
    }

    @POST
    public Response insertar(ComentariosDTO comentario) {
        try {
            comentario.setFechaCreacion(new Date());
            comentario.setEstado(true);
            comentario = this.comentarioBO.insertar(comentario);
            return Response.status(Response.Status.CREATED).entity(comentario).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(ComentariosDTO comentario) {
        comentario.setFechaModificacion(new Date());
        Integer respuesta = this.comentarioBO.modificar(comentario);
        if (respuesta > 0) {
            return Response.ok(comentario).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(ComentariosDTO comentario) {
        comentario.setFechaModificacion(new Date());
        comentario.setEstado(false);
        Integer respuesta = this.comentarioBO.eliminar(comentario);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
