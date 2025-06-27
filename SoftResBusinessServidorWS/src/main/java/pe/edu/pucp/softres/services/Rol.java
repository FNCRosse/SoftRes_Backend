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
import java.util.List;
import pe.edu.pucp.softres.business.RolBO;
import pe.edu.pucp.softres.model.RolDTO;

/**
 *
 * @author Rosse
 */
@Path("Rol")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Rol {

    private final RolBO rolBO;

    public Rol() {
        this.rolBO = new RolBO();
    }

    @POST
    @Path("listar")
    public List<RolDTO> listar() {
        return this.rolBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer rolID) {
        RolDTO rol = this.rolBO.obtenerPorId(rolID);
        if (rol  == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(rol ).build();
    }

    @POST
    public Response insertar(RolDTO rol) {
        try {
            rol  = this.rolBO.insertar(rol );
            return Response.status(Response.Status.CREATED).entity(rol).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(RolDTO rol) {
        Integer respuesta = this.rolBO.modificar(rol);
        if (respuesta > 0) {
            return Response.ok(rol).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(RolDTO rol) {
        Integer respuesta = this.rolBO.eliminar(rol);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
