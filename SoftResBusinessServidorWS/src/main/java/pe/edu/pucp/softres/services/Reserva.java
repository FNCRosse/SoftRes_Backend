/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import java.util.Date;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.business.ReservaBO;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;

/**
 * Servicio REST para gestión de reservas
 * Sigue el patrón de abstracción sobre la capa de negocio
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
    public Response listar(ReservaParametros parametros) {
        try {
            List<ReservaDTO> reservas = this.reservaBO.listar(parametros);
            return Response.ok(reservas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar reservas: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer reservaID) {
        try {
            if (reservaID == null || reservaID <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de reserva inválido").build();
            }
            
            ReservaDTO reserva = this.reservaBO.obtenerPorId(reservaID);
            if (reserva == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Reserva no encontrada").build();
            }
            return Response.ok(reserva).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    public Response insertar(ReservaDTO reserva) {
        try {
            if (reserva == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la reserva son requeridos").build();
            }
            
            reserva.setFechaCreacion(new Date());
            Integer reservaId = this.reservaBO.insertar(reserva);
            
            if (reservaId != null && reservaId > 0) {
                reserva.setIdReserva(reservaId);
                return Response.status(Response.Status.CREATED).entity(reserva).build();
            } else if (reservaId != null && reservaId == -1) {
                return Response.status(Response.Status.ACCEPTED)
                        .entity("Reserva agregada a lista de espera").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Error al crear la reserva").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(ReservaDTO reserva) {
        try {
            if (reserva == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la reserva son requeridos").build();
            }
            
            reserva.setFechaModificacion(new Date());
            Integer respuesta = this.reservaBO.modificar(reserva);
            if (respuesta > 0) {
                return Response.ok(reserva).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada o no se pudo modificar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(ReservaDTO reserva) {
        try {
            if (reserva == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la reserva son requeridos").build();
            }
            
            reserva.setFechaModificacion(new Date());
            reserva.setEstado(EstadoReserva.CANCELADA);
            Integer respuesta = this.reservaBO.eliminar(reserva);
            if (respuesta > 0) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada o no se pudo eliminar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/cancelar")
    public Response cancelar(ReservaDTO reserva) {
        try {
            if (reserva == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la reserva son requeridos").build();
            }
            
            reserva.setFechaModificacion(new Date());
            Integer respuesta = this.reservaBO.cancelar(reserva);
            if (respuesta > 0) {
                return Response.ok()
                        .entity("Reserva cancelada exitosamente").build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada o no se pudo cancelar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/confirmar/{reservaId}/{usuarioConfirmadorId}")
    public Response confirmarReserva(@PathParam("reservaId") Integer reservaId, 
                                   @PathParam("usuarioConfirmadorId") Integer usuarioConfirmadorId) {
        try {
            if (reservaId == null || reservaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de reserva inválido").build();
            }
            
            if (usuarioConfirmadorId == null || usuarioConfirmadorId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de usuario confirmador inválido").build();
            }
            
            Integer respuesta = this.reservaBO.confirmarReserva(reservaId, usuarioConfirmadorId);
            if (respuesta > 0) {
                return Response.ok()
                        .entity("Reserva confirmada exitosamente").build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada o no se pudo confirmar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/confirmar")
    public Response confirmarReservaPost(ReservaConfirmacionDTO confirmacion) {
        try {
            if (confirmacion == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de confirmación son requeridos").build();
            }
            
            return confirmarReserva(confirmacion.getReservaId(), confirmacion.getUsuarioConfirmadorId());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    // Clase interna para el DTO de confirmación
    public static class ReservaConfirmacionDTO {
        private Integer reservaId;
        private Integer usuarioConfirmadorId;

        public ReservaConfirmacionDTO() {}

        public Integer getReservaId() {
            return reservaId;
        }

        public void setReservaId(Integer reservaId) {
            this.reservaId = reservaId;
        }

        public Integer getUsuarioConfirmadorId() {
            return usuarioConfirmadorId;
        }

        public void setUsuarioConfirmadorId(Integer usuarioConfirmadorId) {
            this.usuarioConfirmadorId = usuarioConfirmadorId;
        }
    }
}
