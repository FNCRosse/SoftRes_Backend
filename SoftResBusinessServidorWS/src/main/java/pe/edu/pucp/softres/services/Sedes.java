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
import java.util.List;
import pe.edu.pucp.softres.business.SedeBO;
import pe.edu.pucp.softres.model.SedeDTO;
import pe.edu.pucp.softres.parametros.SedeParametros;

/**
 *
 * @author frank
 */
@Path("Sedes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Sedes {

    private final SedeBO sedeBO;

    public Sedes() {
        this.sedeBO = new SedeBO();
    }

    @POST
    @Path("listar")
    public List<SedeDTO> listar(SedeParametros parametros) {
        return this.sedeBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer sedeId) {
        SedeDTO sede = this.sedeBO.obtenerPorId(sedeId);
        if (sede == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(sede).build();
    }

    @POST
    public Response insertar(SedeDTO sedeDTO) {
        sedeDTO = this.sedeBO.insertar(sedeDTO);
        return Response.status(Response.Status.CREATED).entity(sedeDTO).build();
    }

    @PUT
    public Response modificar(SedeDTO sedeDTO) {
        Integer respuesta = this.sedeBO.modificar(sedeDTO);
        if (respuesta > 0) {
            return Response.ok(sedeDTO).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(SedeDTO sede) {
        Integer respuesta = this.sedeBO.eliminar(sede);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
