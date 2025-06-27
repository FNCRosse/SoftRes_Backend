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
import pe.edu.pucp.softres.business.HorarioxSedeBO;
import pe.edu.pucp.softres.model.HorariosxSedesDTO;

/**
 *
 * @author Rosse
 */
@Path("HorarioxSede")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HorarioxSede {

    private final HorarioxSedeBO horarioxSedeBO;

    public HorarioxSede() {
        this.horarioxSedeBO = new HorarioxSedeBO();
    }

    @POST
    @Path("listar")
    public List<HorariosxSedesDTO> listar(Integer idSede) {
        return this.horarioxSedeBO.listar(idSede);
    }

    @POST
    public Response insertar(HorariosxSedesDTO hxs) {
        try {
            int resultado = this.horarioxSedeBO.insertar(hxs);
            if (resultado > 0) {
                return Response.status(Response.Status.CREATED)
                        .entity("Horario asignado a sede correctamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se pudo asignar el horario a la sede").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(HorariosxSedesDTO hxs) {
        Integer respuesta = this.horarioxSedeBO.eliminar(hxs);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
