/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softres.business.HomeDashboardBO;

/**
 *
 * @author BryanGnr
 */
@Path("Dashboard")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DashBoard {

    private final HomeDashboardBO dashboardBO;

    public DashBoard() {
        this.dashboardBO = new HomeDashboardBO();
    }

    @GET
    @Path("cantidadReservasDiarias")
    public Response obtenerCantidadReservasDiarias() {
        try {
            Integer cantidad = this.dashboardBO.obtenerCantidadReservasDiarias();
            return Response.ok(cantidad).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("cantidadReservasSemanales")
    public Response obtenerCantidadReservasSemanales() {
        try {
            Integer cantidad = this.dashboardBO.obtenerCantidadReservasSemanales();
            return Response.ok(cantidad).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("porcentajeOcupacionMesas")
    public Response obtenerPorcentajeOcupacionMesas() {
        try {
            Double porcentaje = this.dashboardBO.obtenerPorcentajeOcupacionMesas();
            return Response.ok(porcentaje).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("cantidadCancelacionesRecientes")
    public Response obtenerCantidadCancelacionesRecientes() {
        try {
            Integer cantidad = this.dashboardBO.obtenerCantidadCancelacionesRecientes();
            return Response.ok(cantidad).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("reservasPorMes")
    public Response obtenerReservasPorMes() {
        try {
            List<Integer> reservasMensuales = this.dashboardBO.obtenerReservasPorMes();
            return Response.ok(reservasMensuales).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("porcentajeReservasPorLocal")
    public Response obtenerPorcentajeReservasPorLocal() {
        try {
            Map<String, Double> porcentajeReservas = this.dashboardBO.obtenerPorcentajeReservasPorLocal();
            return Response.ok(porcentajeReservas).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("estadoActualReservas")
    public Response obtenerEstadoActualReservas() {
        try {
            Map<String, Integer> estadoReservas = this.dashboardBO.obtenerEstadoActualReservas();
            return Response.ok(estadoReservas).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }
}
