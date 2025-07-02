/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

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
import pe.edu.pucp.softres.business.ReservaxMesaBO;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.model.ReservaDTO;

/**
 * Servicio REST para gestión de la relación Reserva x Mesa
 * Maneja la asignación y liberación de mesas para reservas específicas
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
    @Path("/verificarDisponibilidad")
    public Response verificarDisponibilidad(ReservaDTO reserva) {
        try {
            if (reserva == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la reserva son requeridos").build();
            }
            
            boolean disponible = this.reservaxMesaBO.verificarDisponibilidadMesas(reserva);
            
            return Response.ok(new DisponibilidadResponse(disponible)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al verificar disponibilidad: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/asignar/{reservaId}")
    public Response asignarMesas(@PathParam("reservaId") Integer reservaId, ReservaDTO reserva) {
        try {
            if (reservaId == null || reservaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de reserva inválido").build();
            }
            
            if (reserva == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la reserva son requeridos").build();
            }
            
            boolean asignado = this.reservaxMesaBO.asignarMesasAReserva(reservaId, reserva);
            
            if (asignado) {
                return Response.ok()
                        .entity("Mesas asignadas exitosamente a la reserva").build();
            } else {
                return Response.status(Response.Status.CONFLICT)
                        .entity("No se pudieron asignar las mesas. Verifique disponibilidad").build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/obtenerMesas/{reservaId}")
    public Response obtenerMesasDeReserva(@PathParam("reservaId") Integer reservaId) {
        try {
            if (reservaId == null || reservaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de reserva inválido").build();
            }
            
            List<MesaDTO> mesas = this.reservaxMesaBO.obtenerMesasDeReserva(reservaId);
            
            return Response.ok(mesas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/cambiarEstado/{reservaId}")
    public Response cambiarEstadoMesas(@PathParam("reservaId") Integer reservaId, 
                                     CambioEstadoRequest request) {
        try {
            if (reservaId == null || reservaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de reserva inválido").build();
            }
            
            if (request == null || request.getNuevoEstado() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El nuevo estado es requerido").build();
            }
            
            EstadoMesa nuevoEstado;
            try {
                nuevoEstado = EstadoMesa.valueOf(request.getNuevoEstado().toUpperCase());
            } catch (IllegalArgumentException e) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Estado inválido. Estados válidos: DISPONIBLE, RESERVADA, EN_USO, EN_MANTENIMIENTO, DESECHADA").build();
            }
            
            boolean cambiado = this.reservaxMesaBO.cambiarEstadoMesasReserva(reservaId, nuevoEstado);
            
            if (cambiado) {
                return Response.ok()
                        .entity("Estado de las mesas cambiado exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron mesas asociadas a la reserva o no se pudieron cambiar").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/liberar/{reservaId}")
    public Response liberarMesas(@PathParam("reservaId") Integer reservaId) {
        try {
            if (reservaId == null || reservaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de reserva inválido").build();
            }
            
            boolean liberado = this.reservaxMesaBO.liberarMesasDeReserva(reservaId);
            
            if (liberado) {
                return Response.ok()
                        .entity("Mesas liberadas exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron mesas asociadas a la reserva o no se pudieron liberar").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/estadisticas/{localId}")
    public Response obtenerEstadisticasOcupacion(@PathParam("localId") Integer localId) {
        try {
            if (localId == null || localId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de local inválido").build();
            }
            
            int[] estadisticas = this.reservaxMesaBO.obtenerEstadisticasOcupacion(localId);
            
            EstadisticasResponse response = new EstadisticasResponse();
            if (estadisticas != null && estadisticas.length >= 4) {
                response.setTotalMesas(estadisticas[0]);
                response.setMesasDisponibles(estadisticas[1]);
                response.setMesasReservadas(estadisticas[2]);
                response.setMesasEnUso(estadisticas[3]);
                response.setPorcentajeOcupacion(estadisticas[0] > 0 ? 
                    (estadisticas[2] + estadisticas[3]) * 100.0 / estadisticas[0] : 0.0);
            }
            
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    // Clases internas para DTOs específicos del servicio
    public static class DisponibilidadResponse {
        private boolean disponible;
        private String mensaje;

        public DisponibilidadResponse() {}

        public DisponibilidadResponse(boolean disponible) {
            this.disponible = disponible;
            this.mensaje = disponible ? "Hay mesas disponibles" : "No hay mesas disponibles";
        }

        public boolean isDisponible() {
            return disponible;
        }

        public void setDisponible(boolean disponible) {
            this.disponible = disponible;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

    public static class CambioEstadoRequest {
        private String nuevoEstado;

        public CambioEstadoRequest() {}

        public String getNuevoEstado() {
            return nuevoEstado;
        }

        public void setNuevoEstado(String nuevoEstado) {
            this.nuevoEstado = nuevoEstado;
        }
    }

    public static class EstadisticasResponse {
        private int totalMesas;
        private int mesasDisponibles;
        private int mesasReservadas;
        private int mesasEnUso;
        private double porcentajeOcupacion;

        public EstadisticasResponse() {}

        public int getTotalMesas() {
            return totalMesas;
        }

        public void setTotalMesas(int totalMesas) {
            this.totalMesas = totalMesas;
        }

        public int getMesasDisponibles() {
            return mesasDisponibles;
        }

        public void setMesasDisponibles(int mesasDisponibles) {
            this.mesasDisponibles = mesasDisponibles;
        }

        public int getMesasReservadas() {
            return mesasReservadas;
        }

        public void setMesasReservadas(int mesasReservadas) {
            this.mesasReservadas = mesasReservadas;
        }

        public int getMesasEnUso() {
            return mesasEnUso;
        }

        public void setMesasEnUso(int mesasEnUso) {
            this.mesasEnUso = mesasEnUso;
        }

        public double getPorcentajeOcupacion() {
            return porcentajeOcupacion;
        }

        public void setPorcentajeOcupacion(double porcentajeOcupacion) {
            this.porcentajeOcupacion = porcentajeOcupacion;
        }
    }
}
