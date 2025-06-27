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
import pe.edu.pucp.softres.business.LocalBO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;

/**
 *
 * @author Rosse
 */
@Path("Local")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Local {

    private final LocalBO localBO;

    public Local() {
        this.localBO = new LocalBO();
    }

    @POST
    @Path("listar")
    public List<LocalDTO> listar(LocalParametros parametros) {
        return this.localBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer localId) {
        LocalDTO local = this.localBO.obtenerPorId(localId);
        if (local == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(local).build();
    }

    @POST
    public Response insertar(LocalDTO local) {
        local = this.localBO.insertar(local);
        return Response.status(Response.Status.CREATED).entity(local).build();
    }

    @PUT
    public Response modificar(LocalDTO local) {
        Integer respuesta = this.localBO.modificar(local);
        if (respuesta > 0) {
            return Response.ok(local).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(LocalDTO local) {
        Integer respuesta = this.localBO.eliminar(local);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
