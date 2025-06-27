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
import pe.edu.pucp.softres.business.ReservaBO;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 *
 * @author Rosse
 */
@Path("Reserva")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Reserva {

    private final ReservaBO reservaBO;

    public Reserva() {
        this.reservaBO = new ReservaBO();
    }

    @POST
    @Path("listar")
    public List<ReservaDTO> listar(ReservaParametros parametros) {
        return this.reservaBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer reservaID) {
        ReservaDTO reserva = this.reservaBO.obtenerPorId(reservaID);
        if (reserva == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reserva).build();
    }

    @POST
    public Response insertar(ReservaDTO reserva) {
        try {
            reserva.setFechaCreacion(new Date());
            reserva = this.reservaBO.insertar(reserva);
            return Response.status(Response.Status.CREATED).entity(reserva).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(ReservaDTO reserva) {
        reserva.setFechaModificacion(new Date());
        Integer respuesta = this.reservaBO.modificar(reserva);
        if (respuesta > 0) {
            return Response.ok(reserva).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(ReservaDTO reserva) {
        reserva.setFechaModificacion(new Date());
        reserva.setEstado(EstadoReserva.CANCELADA);
        Integer respuesta = this.reservaBO.eliminar(reserva);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
