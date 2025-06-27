package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.LocalBO;
import pe.edu.pucp.softres.model.LocalDTO;
import pe.edu.pucp.softres.parametros.LocalParametros;

@WebService(serviceName = "locales")
@XmlSeeAlso({LocalDTO.class, LocalParametros.class})
public class Local {

    private final LocalBO localBO;

    public Local() {
        this.localBO = new LocalBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarLocal(LocalDTO local) throws IOException, InterruptedException {
        return this.localBO.insertar(local);
    }

    @WebMethod(operationName = "obtenerPorId")
    public LocalDTO obtenerLocalPorId(Integer localId) throws IOException, InterruptedException {
        return this.localBO.obtenerPorId(localId);
    }

    @WebMethod(operationName = "listar")
    public List<LocalDTO> listarLocales(LocalParametros parametros) throws IOException, InterruptedException {
        return this.localBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarLocal(LocalDTO local) throws IOException, InterruptedException {
        local.setFechaModificacion(new Date());
        return this.localBO.modificar(local);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarLocal(LocalDTO local) throws IOException, InterruptedException {
        local.setFechaModificacion(new Date());
        local.setEstado(false);// lógica de eliminación lógica como en REST
        return this.localBO.eliminar(local);
    }
} 