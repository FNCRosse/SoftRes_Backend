/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import pe.edu.pucp.softres.business.ReservaxMesaBO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;

/**
 *
 * @author Rosse
 */
@Path("ReservaxMesa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaxMesa {

    private final ReservaxMesaBO reservaxMesaBO;

    public ReservaxMesa() {
        this.reservaxMesaBO = new ReservaxMesaBO();
    }

    @POST
    @Path("listar")
    public List<ReservaxMesasDTO> listar(Integer idReserva) {
        return this.reservaxMesaBO.listar(idReserva);
    }

    @POST
    public Response insertar(ReservaxMesasDTO rxm) {
        try {
            int resultado = this.reservaxMesaBO.insertar(rxm);
            if (resultado > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("Mesa asignado a Reserva correctamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se pudo asignar el Mesa a la Reserva").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(ReservaxMesasDTO rxm) {
        Integer respuesta = this.reservaxMesaBO.eliminar(rxm);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
