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
import pe.edu.pucp.softres.business.TipoMesaBO;
import pe.edu.pucp.softres.model.TipoMesaDTO;

/**
 *
 * @author Rosse
 */
@Path("TipoMesa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoMesa {

    private final TipoMesaBO tipoMesaBO;

    public TipoMesa() {
        this.tipoMesaBO = new TipoMesaBO();
    }

    @POST
    @Path("listar")
    public List<TipoMesaDTO> listar() {
        return this.tipoMesaBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer tMesaID) {
        TipoMesaDTO tMesa = this.tipoMesaBO.obtenerPorId(tMesaID);
        if (tMesa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(tMesa).build();
    }

    @POST
    public Response insertar(TipoMesaDTO tMesa) {
        try {
            tMesa = this.tipoMesaBO.insertar(tMesa);
            return Response.status(Response.Status.CREATED).entity(tMesa).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(TipoMesaDTO tMesa) {
        Integer respuesta = this.tipoMesaBO.modificar(tMesa);
        if (respuesta > 0) {
            return Response.ok(tMesa).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(TipoMesaDTO tMesa) {
        Integer respuesta = this.tipoMesaBO.eliminar(tMesa);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
