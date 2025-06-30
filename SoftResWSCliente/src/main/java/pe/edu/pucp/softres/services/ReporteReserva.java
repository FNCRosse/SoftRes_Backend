/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import pe.edu.pucp.softres.reports.ReporteUtil;

@WebService(serviceName = "reporteReserva")
public class ReporteReserva {
    
    public ReporteReserva(){
        
    }
    
    @WebMethod(operationName = "reporteReserva")
    public byte[] reporteReserva() {
        return ReporteUtil.reporte("ReporteReserva");
    }
}
