/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softres.dao;

import pe.edu.pucp.softres.model.HomeDashboardDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author BryanGnr
 */
public interface HomeDashboardDAO {
    Integer obtenerCantidadReservasDiarias();
    Integer obtenerCantidadReservasSemanales();
    Double obtenerPorcentajeOcupacionMesas();
    Integer obtenerCantidadCancelacionesRecientes();
    List<Integer> obtenerReservasPorMes(); // 12 elementos
    Map<String, Double> obtenerPorcentajeReservasPorLocal(); // clave = nombre local
    Map<String, Integer> obtenerEstadoActualReservas(); // "Pendiente" -> 40, "Canceladas" -> 20
}
