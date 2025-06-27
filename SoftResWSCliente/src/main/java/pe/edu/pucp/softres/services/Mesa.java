package pe.edu.pucp.softres.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import pe.edu.pucp.softres.bo.client.MesaBO;
import pe.edu.pucp.softres.model.MesaDTO;
import pe.edu.pucp.softres.parametros.MesaParametros;

@WebService(serviceName = "mesas")
@XmlSeeAlso({MesaDTO.class, MesaParametros.class})
public class Mesa {

    private final MesaBO mesaBO;

    public Mesa() {
        this.mesaBO = new MesaBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarMesa(MesaDTO mesa) throws IOException, InterruptedException {
        mesa.setFechaCreacion(new Date());
        return this.mesaBO.insertar(mesa);
    }

    @WebMethod(operationName = "obtenerPorId")
    public MesaDTO obtenerMesaPorId(Integer mesaId) throws IOException, InterruptedException {
        return this.mesaBO.obtenerPorId(mesaId);
    }

    @WebMethod(operationName = "listar")
    public List<MesaDTO> listarMesas(MesaParametros parametros) throws IOException, InterruptedException {
        return this.mesaBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarMesa(MesaDTO mesa) throws IOException, InterruptedException {
        mesa.setFechaModificacion(new Date());
        return this.mesaBO.modificar(mesa);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarMesa(MesaDTO mesa) throws IOException, InterruptedException {
        mesa.setFechaModificacion(new Date());
        return this.mesaBO.eliminar(mesa);
    }
} 