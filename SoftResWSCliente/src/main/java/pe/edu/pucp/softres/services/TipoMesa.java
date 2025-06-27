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
import pe.edu.pucp.softres.bo.client.TipoMesaBO;
import pe.edu.pucp.softres.model.TipoMesaDTO;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "tipoMesa")
@XmlSeeAlso({TipoMesaDTO.class})
public class TipoMesa {
    private final TipoMesaBO tMesaBO;

    public TipoMesa() {
        this.tMesaBO = new TipoMesaBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarTipoMesa(TipoMesaDTO tMesa) throws IOException, InterruptedException {
        return this.tMesaBO.insertar(tMesa);
    }

    @WebMethod(operationName = "obtenerPorId")
    public TipoMesaDTO obtenerTipoMesaPorId( Integer rtMesaId) throws IOException, InterruptedException {
        return this.tMesaBO.obtenerPorId(rtMesaId);
    }

    @WebMethod(operationName = "listar")
    public List<TipoMesaDTO> listarTipoMesa() throws IOException, InterruptedException {
        return this.tMesaBO.listar();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarTipoMesa(TipoMesaDTO tMesa) throws IOException, InterruptedException {
        return this.tMesaBO.modificar(tMesa);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarTipoMesa(TipoMesaDTO tMesa) throws IOException, InterruptedException {
        return this.tMesaBO.eliminar(tMesa);
    }
}
