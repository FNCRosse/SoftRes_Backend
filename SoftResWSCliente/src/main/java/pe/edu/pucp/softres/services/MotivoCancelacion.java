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
import pe.edu.pucp.softres.bo.client.MotivoCancelacionBO;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "motivoCancelacion")
@XmlSeeAlso({MotivosCancelacionDTO.class})
public class MotivoCancelacion {
    private final MotivoCancelacionBO mCancelacionBO;

    public MotivoCancelacion() {
        this.mCancelacionBO = new MotivoCancelacionBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarMcancelacion(MotivosCancelacionDTO mCancelacion) throws IOException, InterruptedException {
        return this.mCancelacionBO.insertar(mCancelacion);
    }

    @WebMethod(operationName = "obtenerPorId")
    public MotivosCancelacionDTO obtenerMcancelacionPorId( Integer mCancelacionId) throws IOException, InterruptedException {
        return this.mCancelacionBO.obtenerPorId(mCancelacionId);
    }

    @WebMethod(operationName = "listar")
    public List<MotivosCancelacionDTO> listarMcancelacion() throws IOException, InterruptedException {
        return this.mCancelacionBO.listar();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarMcancelacion(MotivosCancelacionDTO mCancelacion) throws IOException, InterruptedException {
        return this.mCancelacionBO.modificar(mCancelacion);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarMcancelacion(MotivosCancelacionDTO mCancelacion) throws IOException, InterruptedException {
        return this.mCancelacionBO.eliminar(mCancelacion);
    }
}
