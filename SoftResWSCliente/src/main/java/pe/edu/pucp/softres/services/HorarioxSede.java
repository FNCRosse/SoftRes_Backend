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
import pe.edu.pucp.softres.bo.client.HorarioxSedeBO;
import pe.edu.pucp.softres.model.HorariosxSedesDTO;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "horarioxsede")
@XmlSeeAlso({HorariosxSedesDTO.class})
public class HorarioxSede {
    private final HorarioxSedeBO hxsBO;
    
    public HorarioxSede() {
        this.hxsBO = new HorarioxSedeBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarHxS(HorariosxSedesDTO hxs) throws IOException, InterruptedException {
        return this.hxsBO.insertar(hxs);
    }

    @WebMethod(operationName = "listar")
    public List<HorariosxSedesDTO> listarHxS(Integer idSede) throws IOException, InterruptedException {
        return this.hxsBO.listar(idSede);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarHxS(HorariosxSedesDTO hxs) throws IOException, InterruptedException {
        return this.hxsBO.eliminar(hxs);
    }
}
