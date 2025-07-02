/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import pe.edu.pucp.softres.bo.client.HomeDashboardBO;

/**
 *
 * @author BryanGnr
 */
@WebService(serviceName = "dashboard")
public class DashBoard {
    private final HomeDashboardBO dashboardBO;

    public DashBoard() {
        this.dashboardBO = new HomeDashboardBO();
    }

    @WebMethod(operationName = "obtenerCantidadReservasDiarias")
    public Integer obtenerCantidadReservasDiarias() throws IOException, InterruptedException {
        return this.dashboardBO.obtenerCantidadReservasDiarias();
    }

    @WebMethod(operationName = "obtenerCantidadReservasSemanales")
    public Integer obtenerCantidadReservasSemanales() throws IOException, InterruptedException {
        return this.dashboardBO.obtenerCantidadReservasSemanales();
    }

    @WebMethod(operationName = "obtenerPorcentajeOcupacionMesas")
    public Double obtenerPorcentajeOcupacionMesas() throws IOException, InterruptedException {
        return this.dashboardBO.obtenerPorcentajeOcupacionMesas();
    }

    @WebMethod(operationName = "obtenerCantidadCancelacionesRecientes")
    public Integer obtenerCantidadCancelacionesRecientes() throws IOException, InterruptedException {
        return this.dashboardBO.obtenerCantidadCancelacionesRecientes();
    }
}
