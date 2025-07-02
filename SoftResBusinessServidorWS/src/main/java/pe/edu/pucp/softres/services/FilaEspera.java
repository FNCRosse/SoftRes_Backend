/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.business.FilaEsperaBO;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.model.TipoReserva;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 * Servicio REST para gestión de fila de espera
 * Maneja las solicitudes de reserva cuando no hay disponibilidad inmediata
 * 
 * @author Rosse
 */
@Path("FilaEspera")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FilaEspera {

    private final FilaEsperaBO filaEsperaBO;

    public FilaEspera() {
        this.filaEsperaBO = new FilaEsperaBO();
    }

    @POST
    public Response insertar(FilaEsperaDTO filaEspera) {
        try {
            if (filaEspera == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la fila de espera son requeridos").build();
            }
            
            filaEspera.setFechaRegistro(new Date());
            FilaEsperaDTO resultado = this.filaEsperaBO.insertar(filaEspera);
            
            return Response.status(Response.Status.CREATED).entity(resultado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("{idFila}/{idUsuario}")
    public Response obtenerPorId(@PathParam("idFila") Integer idFila, 
                                @PathParam("idUsuario") Integer idUsuario) {
        try {
            if (idFila == null || idFila <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de fila inválido").build();
            }
            
            if (idUsuario == null || idUsuario <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de usuario inválido").build();
            }
            
            FilaEsperaDTO filaEspera = this.filaEsperaBO.obtenerPorId(idFila, idUsuario);
            if (filaEspera == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Solicitud de fila de espera no encontrada").build();
            }
            return Response.ok(filaEspera).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("listar")
    public Response listar(FilaEsperaParametros parametros) {
        try {
            ArrayList<FilaEsperaDTO> filaEspera = this.filaEsperaBO.listar(parametros);
            return Response.ok(filaEspera).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar fila de espera: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("pendientes")
    public Response listarPendientesPorPrioridad(@QueryParam("localId") Integer localId) {
        try {
            List<FilaEsperaDTO> pendientes = this.filaEsperaBO.listarPendientesPorPrioridad(localId);
            return Response.ok(pendientes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar pendientes: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("siguiente")
    public Response buscarSiguienteCompatible(@QueryParam("localId") Integer localId,
                                            @QueryParam("fechaHora") String fechaHora,
                                            @QueryParam("tipoReserva") String tipoReserva,
                                            @QueryParam("tipoMesaId") Integer tipoMesaId) {
        try {
            if (localId == null || localId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de local es requerido").build();
            }
            
            // Convertir parámetros
            Date fecha = fechaHora != null ? new Date(Long.parseLong(fechaHora)) : new Date();
            TipoReserva tipo = tipoReserva != null ? TipoReserva.valueOf(tipoReserva) : null;
            
            FilaEsperaDTO siguiente = this.filaEsperaBO.buscarSiguienteCompatible(localId, fecha, tipo, tipoMesaId);
            
            if (siguiente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No hay solicitudes compatibles en la fila de espera").build();
            }
            
            return Response.ok(siguiente).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("notificar")
    public Response notificarSiguiente(NotificacionFilaRequest request) {
        try {
            if (request == null || request.getLocalId() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Datos de notificación son requeridos").build();
            }
            
            Date fecha = request.getFechaHora() != null ? request.getFechaHora() : new Date();
            TipoReserva tipo = request.getTipoReserva() != null ? 
                TipoReserva.valueOf(request.getTipoReserva()) : null;
            
            boolean notificado = this.filaEsperaBO.notificarSiguiente(
                request.getLocalId(), fecha, tipo, request.getTipoMesaId());
            
            if (notificado) {
                return Response.ok()
                        .entity("Cliente notificado exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron solicitudes compatibles para notificar").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(FilaEsperaDTO filaEspera) {
        try {
            if (filaEspera == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la fila de espera son requeridos").build();
            }
            
            Integer respuesta = this.filaEsperaBO.modificar(filaEspera);
            if (respuesta > 0) {
                return Response.ok(filaEspera).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Solicitud de fila de espera no encontrada o no se pudo modificar").build();
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
    public Response eliminar(FilaEsperaDTO filaEspera) {
        try {
            if (filaEspera == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la fila de espera son requeridos").build();
            }
            
            Integer respuesta = this.filaEsperaBO.eliminar(filaEspera);
            if (respuesta > 0) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Solicitud de fila de espera no encontrada o no se pudo eliminar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/confirmar/{idFila}/{idUsuario}")
    public Response confirmarDisponibilidad(@PathParam("idFila") Integer idFila, 
                                          @PathParam("idUsuario") Integer idUsuario) {
        try {
            if (idFila == null || idFila <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de fila inválido").build();
            }
            
            if (idUsuario == null || idUsuario <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de usuario inválido").build();
            }
            
            Integer respuesta = this.filaEsperaBO.confirmarDisponibilidad(idFila, idUsuario);
            if (respuesta > 0) {
                return Response.ok()
                        .entity("Disponibilidad confirmada exitosamente").build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Solicitud no encontrada o no se pudo confirmar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    // Clase interna para el DTO de notificación
    public static class NotificacionFilaRequest {
        private Integer localId;
        private Date fechaHora;
        private String tipoReserva;
        private Integer tipoMesaId;

        public NotificacionFilaRequest() {}

        public Integer getLocalId() {
            return localId;
        }

        public void setLocalId(Integer localId) {
            this.localId = localId;
        }

        public Date getFechaHora() {
            return fechaHora;
        }

        public void setFechaHora(Date fechaHora) {
            this.fechaHora = fechaHora;
        }

        public String getTipoReserva() {
            return tipoReserva;
        }

        public void setTipoReserva(String tipoReserva) {
            this.tipoReserva = tipoReserva;
        }

        public Integer getTipoMesaId() {
            return tipoMesaId;
        }

        public void setTipoMesaId(Integer tipoMesaId) {
            this.tipoMesaId = tipoMesaId;
        }
    }
} 