package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.SedeBO;
import pe.edu.pucp.softres.model.SedeDTO;
import pe.edu.pucp.softres.parametros.SedeParametros;
import pe.edu.pucp.softres.reports.ReporteUtil;

@WebService(serviceName = "sedes")
@XmlSeeAlso({SedeDTO.class, SedeParametros.class})
public class Sede {

    private final SedeBO sedeBO;

    public Sede() {
        this.sedeBO = new SedeBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarSede(SedeDTO sede) throws IOException, InterruptedException {
        sede.setFechaCreacion(new Date());
        sede.setEstado(true);
        return this.sedeBO.insertar(sede);
    }

    @WebMethod(operationName = "obtenerPorId")
    public SedeDTO obtenerSedePorId(Integer sedeId) throws IOException, InterruptedException {
        return this.sedeBO.obtenerPorId(sedeId);
    }

    @WebMethod(operationName = "listar")
    public List<SedeDTO> listarSedes(SedeParametros parametros) throws IOException, InterruptedException {
        return this.sedeBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarSede(SedeDTO sede) throws IOException, InterruptedException {
        sede.setFechaModificacion(new Date());
        return this.sedeBO.modificar(sede);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarSede(SedeDTO sede) throws IOException, InterruptedException {
        sede.setFechaModificacion(new Date());
        sede.setEstado(false);// lógica de eliminación lógica como en REST
        return this.sedeBO.eliminar(sede);
    }
}
