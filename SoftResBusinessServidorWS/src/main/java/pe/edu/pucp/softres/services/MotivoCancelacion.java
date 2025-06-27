/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

/**
 *
 * @author Rosse
 */
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import pe.edu.pucp.softres.business.MotivoCancelacionBO;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;

@Path("MotivoCancelacion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MotivoCancelacion {

    private final MotivoCancelacionBO motivoCancelacionBO;

    public MotivoCancelacion() {
        this.motivoCancelacionBO = new MotivoCancelacionBO();
    }

    @POST
    @Path("listar")
    public List<MotivosCancelacionDTO> listar() {
        return this.motivoCancelacionBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer mCancelacionID) {
        MotivosCancelacionDTO mCancelacion = this.motivoCancelacionBO.obtenerPorId(mCancelacionID);
        if (mCancelacion == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mCancelacion).build();
    }

    @POST
    public Response insertar(MotivosCancelacionDTO mCancelacion) {
        try {
            mCancelacion = this.motivoCancelacionBO.insertar(mCancelacion);
            return Response.status(Response.Status.CREATED).entity(mCancelacion).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(MotivosCancelacionDTO mCancelacion) {
        Integer respuesta = this.motivoCancelacionBO.modificar(mCancelacion);
        if (respuesta > 0) {
            return Response.ok(mCancelacion).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(MotivosCancelacionDTO mCancelacion) {
        Integer respuesta = this.motivoCancelacionBO.eliminar(mCancelacion);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
