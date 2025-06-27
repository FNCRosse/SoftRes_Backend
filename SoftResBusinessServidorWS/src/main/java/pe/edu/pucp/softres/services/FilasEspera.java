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
import pe.edu.pucp.softres.business.FilaEsperaBO;
import pe.edu.pucp.softres.model.EstadoFilaEspera;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 *
 * @author Rosse
 */
@Path("FilaEspera")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilasEspera {

    private final FilaEsperaBO FilaEsperaBO;

    public FilasEspera() {
        this.FilaEsperaBO = new FilaEsperaBO();
    }

    @POST
    @Path("listar")
    public List<FilaEsperaDTO> listar(FilaEsperaParametros parametros) {
        return this.FilaEsperaBO.listar(parametros);
    }

    @GET
    @Path("{idFila}/{idUser}")
    public Response obtenerPorId(@PathParam("idFila") Integer filaid,@PathParam("idUser") Integer idUser) {
        FilaEsperaDTO fila = this.FilaEsperaBO.obtenerPorId(filaid,idUser);
        if (fila == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(fila).build();
    }

    @POST
    public Response insertar(FilaEsperaDTO fila) {
        try {
            fila = this.FilaEsperaBO.insertar(fila);
            return Response.status(Response.Status.CREATED).entity(fila).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(FilaEsperaDTO fila) {
        Integer respuesta = this.FilaEsperaBO.modificar(fila);
        if (respuesta > 0) {
            return Response.ok(fila).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(FilaEsperaDTO fila) {
        fila.setEstado(EstadoFilaEspera.CANCELADO);
        Integer respuesta = this.FilaEsperaBO.eliminar(fila);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
