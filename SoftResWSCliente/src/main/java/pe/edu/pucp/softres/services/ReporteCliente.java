/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebMethod;
import pe.edu.pucp.softres.reports.ReporteUtil;

/**
 *
 * @author Fernando
 */
public class ReporteCliente {
    @WebMethod(operationName = "reporteCliente")
    public byte[] reporteClientes() {
        return ReporteUtil.reporte("Reporte_Cliente");
    }
}
