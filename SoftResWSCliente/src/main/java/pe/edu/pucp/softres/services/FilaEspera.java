package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.List;
import pe.edu.pucp.softres.bo.client.FilaEsperaBO;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

/**
 *
 * @author Rosse
 */
@WebService(serviceName = "filaEspera")
@XmlSeeAlso({FilaEsperaDTO.class, FilaEsperaParametros.class})
public class FilaEspera {

    private final FilaEsperaBO filaEsperaBO;

    public FilaEspera() {
        this.filaEsperaBO = new FilaEsperaBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarFilaEspera(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        return this.filaEsperaBO.insertar(filaEspera);
    }

    @WebMethod(operationName = "obtenerPorId")
    public FilaEsperaDTO obtenerFilaEsperaPorId(Integer filaEsperaId) throws IOException, InterruptedException {
        return this.filaEsperaBO.obtenerPorId(filaEsperaId);
    }

    @WebMethod(operationName = "listar")
    public List<FilaEsperaDTO> listarFilaEspera(FilaEsperaParametros parametros) throws IOException, InterruptedException {
        return this.filaEsperaBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarFilaEspera(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        return this.filaEsperaBO.modificar(filaEspera);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarFilaEspera(FilaEsperaDTO filaEspera) throws IOException, InterruptedException {
        return this.filaEsperaBO.eliminar(filaEspera);
    }
}
