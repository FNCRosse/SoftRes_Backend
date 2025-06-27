/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.List;
import pe.edu.pucp.softres.bo.client.ReservaxMesaBO;
import pe.edu.pucp.softres.model.ReservaxMesasDTO;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "reservaxmesa")
@XmlSeeAlso({ReservaxMesasDTO.class})
public class ReservaxMesa {
    private final ReservaxMesaBO rxmBO;
    
    public ReservaxMesa() {
        this.rxmBO = new ReservaxMesaBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarRxM(ReservaxMesasDTO rxm) throws IOException, InterruptedException {
        return this.rxmBO.insertar(rxm);
    }

    @WebMethod(operationName = "listar")
    public List<ReservaxMesasDTO> listarRxM(Integer idReserva) throws IOException, InterruptedException {
        return this.rxmBO.listar(idReserva);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarRxM(ReservaxMesasDTO rxm) throws IOException, InterruptedException {
        return this.rxmBO.eliminar(rxm);
    }
}
