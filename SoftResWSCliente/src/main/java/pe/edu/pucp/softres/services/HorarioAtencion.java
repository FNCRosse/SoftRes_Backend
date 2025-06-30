/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.HorarioAtencionBO;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.parametros.HorarioParametros;

/**
 *
 * @author Rosse
 */
@WebService(serviceName = "horarioAtencion")
@XmlSeeAlso({HorarioAtencionDTO.class, HorarioParametros.class})
public class HorarioAtencion {

    private final HorarioAtencionBO horarioBO;

    public HorarioAtencion() {
        this.horarioBO = new HorarioAtencionBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarHorario(HorarioAtencionDTO horario) throws IOException, InterruptedException {
        horario.setFechaCreacion(new Date());
        horario.setEstado(true);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        // Convertir horaInicioStr a horaInicio (LocalTime)
        if (horario.getHoraInicioStr() != null && !horario.getHoraInicioStr().isEmpty()) {
            LocalTime horaInicio = LocalTime.parse(horario.getHoraInicioStr(), formatter);
            horario.setHoraInicio(horaInicio);
        }

        // Convertir horaFinStr a horaFin (LocalTime)
        if (horario.getHoraFinStr() != null && !horario.getHoraFinStr().isEmpty()) {
            LocalTime horaFin = LocalTime.parse(horario.getHoraFinStr(), formatter);
            horario.setHoraFin(horaFin);
        }
        return this.horarioBO.insertar(horario);
    }

    @WebMethod(operationName = "obtenerPorId")
    public HorarioAtencionDTO obtenerHorarioPorId(Integer horarioId) throws IOException, InterruptedException {
        HorarioAtencionDTO h = this.horarioBO.obtenerPorId(horarioId);
        if (h.getHoraInicio() != null) {
            h.setHoraInicioStr(h.getHoraInicio().toString());
        }
        if (h.getHoraFin() != null) {
            h.setHoraFinStr(h.getHoraFin().toString());
        }
        return h;
    }

    @WebMethod(operationName = "listar")
    public List<HorarioAtencionDTO> listarHorario(HorarioParametros parametros) throws IOException, InterruptedException {
        List<HorarioAtencionDTO> lista = this.horarioBO.listar(parametros);
        for (HorarioAtencionDTO h : lista) {
            if (h.getHoraInicio() != null) {
                h.setHoraInicioStr(h.getHoraInicio().toString());
            }
            if (h.getHoraFin() != null) {
                h.setHoraFinStr(h.getHoraFin().toString());
            }
        }

        return lista;
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarHorario(HorarioAtencionDTO horario) throws IOException, InterruptedException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            // Convertir horaInicioStr a horaInicio (LocalTime)
            if (horario.getHoraInicioStr() != null && !horario.getHoraInicioStr().isEmpty()) {
                LocalTime horaInicio = LocalTime.parse(horario.getHoraInicioStr(), formatter);
                horario.setHoraInicio(horaInicio);
            }
            // Convertir horaFinStr a horaFin (LocalTime)
            if (horario.getHoraFinStr() != null && !horario.getHoraFinStr().isEmpty()) {
                LocalTime horaFin = LocalTime.parse(horario.getHoraFinStr(), formatter);
                horario.setHoraFin(horaFin);
            }
            return this.horarioBO.modificar(horario);
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error en modificarHorario: " + e.getMessage());
        }
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarHorario(HorarioAtencionDTO horario) throws IOException, InterruptedException {
        horario.setFechaModificacion(new Date());
        horario.setEstado(false);// lógica de eliminación lógica como en REST
        return this.horarioBO.eliminar(horario);
    }
}
