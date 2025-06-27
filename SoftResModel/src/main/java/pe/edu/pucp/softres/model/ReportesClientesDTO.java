/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author frank
 */
public class ReportesClientesDTO {

    private UsuariosDTO cliente;
    private List<HorarioAtencionDTO> horariosPreferidos;
    private List<LocalDTO> localesMasVisitadas;
    private double valoracionPromedio;

    public ReportesClientesDTO() {
        this.cliente = null;
        this.horariosPreferidos = new ArrayList<>();
        this.localesMasVisitadas = new ArrayList<>();
        this.valoracionPromedio = 0.0;
    }

    public ReportesClientesDTO(UsuariosDTO cliente, List<HorarioAtencionDTO> horariosPreferidos, List<LocalDTO> localesMasVisitadas, double valoracionPromedio) {
        this.cliente = cliente;
        this.horariosPreferidos = horariosPreferidos;
        this.localesMasVisitadas = localesMasVisitadas;
        this.valoracionPromedio = valoracionPromedio;
    }

    public UsuariosDTO getCliente() {
        return cliente;
    }

    public void setCliente(UsuariosDTO cliente) {
        this.cliente = cliente;
    }

    public List<HorarioAtencionDTO> getHorariosPreferidos() {
        return horariosPreferidos;
    }

    public void setHorariosPreferidos(List<HorarioAtencionDTO> horariosPreferidos) {
        this.horariosPreferidos = horariosPreferidos;
    }

    public List<LocalDTO> getLocalesMasVisitadas() {
        return localesMasVisitadas;
    }

    public void setLocalesMasVisitadas(List<LocalDTO> localesMasVisitadas) {
        this.localesMasVisitadas = localesMasVisitadas;
    }

    public double getValoracionPromedio() {
        return valoracionPromedio;
    }

    public void setValoracionPromedio(double valoracionPromedio) {
        this.valoracionPromedio = valoracionPromedio;
    }
}

