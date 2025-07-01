/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softres.dao.HomeDashboardDAO;
import pe.edu.pucp.softres.daoImp.HomeDashboardDAOImpl;

/**
 *
 * @author BryanGnr
 */
public class HomeDashboardBO {
    private final HomeDashboardDAO homeDashboardDAO;
    
    public HomeDashboardBO(){
        this.homeDashboardDAO = new HomeDashboardDAOImpl();
    }
    
    public Integer obtenerCantidadReservasDiarias(){
        Integer cantidadReservasDiarias = this.homeDashboardDAO.obtenerCantidadReservasDiarias();
        return cantidadReservasDiarias;
    }
    
    public Integer obtenerCantidadReservasSemanales(){
        Integer cantidadReservasSemanales = this.homeDashboardDAO.obtenerCantidadReservasSemanales();
        return cantidadReservasSemanales;
    }
    
    public Double obtenerPorcentajeOcupacionMesas(){
        Double porcentajeOcupacionMesas = this.homeDashboardDAO.obtenerPorcentajeOcupacionMesas();
        return porcentajeOcupacionMesas;
    }
    
    public Integer obtenerCantidadCancelacionesRecientes(){
        Integer cantidadCancelacionesRecientes = this.homeDashboardDAO.obtenerCantidadCancelacionesRecientes();
        return cantidadCancelacionesRecientes;
    }
    
    public List<Integer> obtenerReservasPorMes(){
        return this.homeDashboardDAO.obtenerReservasPorMes();
    }
    
    public Map<String, Double> obtenerPorcentajeReservasPorLocal(){
        return this.homeDashboardDAO.obtenerPorcentajeReservasPorLocal();
    }
    
    public Map<String, Integer> obtenerEstadoActualReservas(){
        return this.homeDashboardDAO.obtenerEstadoActualReservas();
    }
}
