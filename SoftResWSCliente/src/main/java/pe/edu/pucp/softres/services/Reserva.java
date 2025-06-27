/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.ReservaBO;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "reservas")
@XmlSeeAlso({ReservaDTO.class, ReservaParametros.class})
public class Reserva {
    private final ReservaBO comentarioBO;

    public Reserva() {
        this.comentarioBO = new ReservaBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        reserva.setFechaCreacion(new Date());
        return this.comentarioBO.insertar(reserva);
    }

    @WebMethod(operationName = "obtenerPorId")
    public ReservaDTO obtenerReservaPorId( Integer reservaId) throws IOException, InterruptedException {
        return this.comentarioBO.obtenerPorId(reservaId);
    }

    @WebMethod(operationName = "listar")
    public List<ReservaDTO> listarReserva(ReservaParametros parametros) throws IOException, InterruptedException {
        return this.comentarioBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        reserva.setFechaModificacion(new Date());
        return this.comentarioBO.modificar(reserva);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarReserva(ReservaDTO reserva) throws IOException, InterruptedException {
        reserva.setFechaModificacion(new Date());
        reserva.setEstado(EstadoReserva.CANCELADA);// lógica de eliminación lógica como en REST
        return this.comentarioBO.eliminar(reserva);
    }
}
