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
import pe.edu.pucp.softres.bo.client.TipoDocumentoBO;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "tipoDocumento")
@XmlSeeAlso({TipoDocumentoDTO.class})
public class TipoDocumento {
    private final TipoDocumentoBO tDocBO;

    public TipoDocumento() {
        this.tDocBO = new TipoDocumentoBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarTDoc(TipoDocumentoDTO tDoc) throws IOException, InterruptedException {
        return this.tDocBO.insertar(tDoc);
    }

    @WebMethod(operationName = "obtenerPorId")
    public TipoDocumentoDTO obtenerTDocPorId( Integer tDocId) throws IOException, InterruptedException {
        return this.tDocBO.obtenerPorId(tDocId);
    }

    @WebMethod(operationName = "listar")
    public List<TipoDocumentoDTO> listarTDoc() throws IOException, InterruptedException {
        return this.tDocBO.listar();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarTDoc(TipoDocumentoDTO tDoc) throws IOException, InterruptedException {
        return this.tDocBO.modificar(tDoc);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarTDoc(TipoDocumentoDTO tDoc) throws IOException, InterruptedException {
        return this.tDocBO.eliminar(tDoc);
    }
}
