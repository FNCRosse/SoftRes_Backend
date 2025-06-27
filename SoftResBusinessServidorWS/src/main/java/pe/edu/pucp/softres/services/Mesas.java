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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.business.MesaBO;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

@Path("Mesas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Mesas {

    private final MesaBO mesaBO;

    public Mesas() {
        this.mesaBO = new MesaBO();
    }

    @POST
    @Path("listar")
    public List<MesaDTO> listar(MesaParametros parametros) {
        return this.mesaBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer mesaID) {
        MesaDTO mesa = this.mesaBO.obtenerPorId(mesaID);
        if (mesa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(mesa).build();
    }

    @POST
    public Response insertar(MesaDTO mesa) {
        try {
            mesa.setFechaCreacion(new Date());
            mesa = this.mesaBO.insertar(mesa);
            return Response.status(Response.Status.CREATED).entity(mesa).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(MesaDTO mesa) {
        mesa.setFechaModificacion(new Date());
        Integer respuesta = this.mesaBO.modificar(mesa);
        if (respuesta > 0) {
            return Response.ok(mesa).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(MesaDTO mesa) {
        mesa.setFechaModificacion(new Date());
        mesa.setEstado(EstadoMesa.DESECHADA);
        Integer respuesta = this.mesaBO.eliminar(mesa);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
