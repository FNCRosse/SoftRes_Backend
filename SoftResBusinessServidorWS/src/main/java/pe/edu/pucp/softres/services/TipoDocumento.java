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
import pe.edu.pucp.softres.business.TipoDocumentoBO;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;

/**
 *
 * @author Rosse
 */
@Path("TipoDocumento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoDocumento {

    private final TipoDocumentoBO tipoDocumentoBO;

    public TipoDocumento() {
        this.tipoDocumentoBO = new TipoDocumentoBO();
    }

    @POST
    @Path("listar")
    public List<TipoDocumentoDTO> listar() {
        return this.tipoDocumentoBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer tDocId) {
        TipoDocumentoDTO tDoc = this.tipoDocumentoBO.obtenerPorId(tDocId);
        if (tDoc == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(tDoc).build();
    }

    @POST
    public Response insertar(TipoDocumentoDTO tDoc) {
        try {
            tDoc = this.tipoDocumentoBO.insertar(tDoc);
            return Response.status(Response.Status.CREATED).entity(tDoc).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(TipoDocumentoDTO tDoc) {
        Integer respuesta = this.tipoDocumentoBO.modificar(tDoc);
        if (respuesta > 0) {
            return Response.ok(tDoc).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(TipoDocumentoDTO tDoc) {
        Integer respuesta = this.tipoDocumentoBO.eliminar(tDoc);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
