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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softres.business.MesaBO;
import pe.edu.pucp.softres.model.EstadoMesa;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

/**
 * Servicio REST para gestión de mesas
 * Maneja las operaciones CRUD básicas para las mesas del restaurante
 * 
 * @author Rosse
 */
@Path("Mesa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Mesa {

    private final MesaBO mesaBO;

    public Mesa() {
        this.mesaBO = new MesaBO();
    }

    @POST
    public Response insertar(MesaDTO mesa) {
        try {
            if (mesa == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la mesa son requeridos").build();
            }
            
            // Validaciones básicas
            if (mesa.getNumeroMesa() == null || mesa.getNumeroMesa().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El número de mesa es requerido").build();
            }
            
            if (mesa.getCapacidad() == null || mesa.getCapacidad() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("La capacidad debe ser mayor a 0").build();
            }
            
            if (mesa.getLocal() == null || mesa.getLocal().getIdLocal() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El local es requerido").build();
            }
            
            if (mesa.getTipoMesa() == null || mesa.getTipoMesa().getIdTipoMesa() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El tipo de mesa es requerido").build();
            }
            
            // Establecer valores por defecto
            if (mesa.getEstado() == null) {
                mesa.setEstado(EstadoMesa.DISPONIBLE);
            }
            
            if (mesa.getFechaCreacion() == null) {
                mesa.setFechaCreacion(new Date());
            }
            
            mesa.setUsuarioCreacion("sistema");
            
            MesaDTO resultado = this.mesaBO.insertar(mesa);
            
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
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer mesaId) {
        try {
            if (mesaId == null || mesaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de mesa inválido").build();
            }
            
            MesaDTO mesa = this.mesaBO.obtenerPorId(mesaId);
            if (mesa == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Mesa no encontrada").build();
            }
            return Response.ok(mesa).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("listar")
    public Response listar(MesaParametros parametros) {
        try {
            List<MesaDTO> mesas = this.mesaBO.listar(parametros);
            return Response.ok(mesas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar mesas: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("listar/disponibles")
    public Response listarDisponibles(@QueryParam("localId") Integer localId,
                                    @QueryParam("tipoMesaId") Integer tipoMesaId) {
        try {
            MesaParametros parametros = new MesaParametros();
            parametros.setEstado(EstadoMesa.DISPONIBLE);
            
            if (localId != null && localId > 0) {
                parametros.setIdLocal(localId);
            }
            
            if (tipoMesaId != null && tipoMesaId > 0) {
                parametros.setIdTipoMesa(tipoMesaId);
            }
            
            List<MesaDTO> mesas = this.mesaBO.listar(parametros);
            return Response.ok(mesas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar mesas disponibles: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("listar/ocupadas")
    public Response listarOcupadas(@QueryParam("localId") Integer localId) {
        try {
            MesaParametros parametros = new MesaParametros();
            parametros.setEstado(EstadoMesa.EN_USO);
            
            if (localId != null && localId > 0) {
                parametros.setIdLocal(localId);
            }
            
            List<MesaDTO> mesas = this.mesaBO.listar(parametros);
            return Response.ok(mesas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar mesas ocupadas: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("listar/reservadas")
    public Response listarReservadas(@QueryParam("localId") Integer localId) {
        try {
            MesaParametros parametros = new MesaParametros();
            parametros.setEstado(EstadoMesa.RESERVADA);
            
            if (localId != null && localId > 0) {
                parametros.setIdLocal(localId);
            }
            
            List<MesaDTO> mesas = this.mesaBO.listar(parametros);
            return Response.ok(mesas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al listar mesas reservadas: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(MesaDTO mesa) {
        try {
            if (mesa == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la mesa son requeridos").build();
            }
            
            if (mesa.getIdMesa() == null || mesa.getIdMesa() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de mesa inválido").build();
            }
            
            mesa.setFechaModificacion(new Date());
            mesa.setUsuarioModificacion("sistema");
            
            Integer respuesta = this.mesaBO.modificar(mesa);
            if (respuesta > 0) {
                return Response.ok(mesa).build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Mesa no encontrada o no se pudo modificar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/cambiarEstado/{id}/{estado}")
    public Response cambiarEstado(@PathParam("id") Integer mesaId, 
                                @PathParam("estado") String estado) {
        try {
            if (mesaId == null || mesaId <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de mesa inválido").build();
            }
            
            if (estado == null || estado.trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Estado es requerido").build();
            }
            
            // Obtener la mesa actual
            MesaDTO mesa = this.mesaBO.obtenerPorId(mesaId);
            if (mesa == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Mesa no encontrada").build();
            }
            
            // Cambiar el estado
            try {
                EstadoMesa nuevoEstado = EstadoMesa.valueOf(estado.toUpperCase());
                mesa.setEstado(nuevoEstado);
                mesa.setFechaModificacion(new Date());
                mesa.setUsuarioModificacion("sistema");
                
                Integer respuesta = this.mesaBO.modificar(mesa);
                if (respuesta > 0) {
                    return Response.ok(mesa).build();
                }
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("No se pudo cambiar el estado de la mesa").build();
                         } catch (IllegalArgumentException e) {
                 return Response.status(Response.Status.BAD_REQUEST)
                         .entity("Estado inválido. Estados válidos: DISPONIBLE, RESERVADA, EN_USO, EN_MANTENIMIENTO, DESECHADA").build();
             }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(MesaDTO mesa) {
        try {
            if (mesa == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Los datos de la mesa son requeridos").build();
            }
            
            if (mesa.getIdMesa() == null || mesa.getIdMesa() <= 0) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("ID de mesa inválido").build();
            }
            
            Integer respuesta = this.mesaBO.eliminar(mesa);
            if (respuesta > 0) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Mesa no encontrada o no se pudo eliminar").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error de validación: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }
} 