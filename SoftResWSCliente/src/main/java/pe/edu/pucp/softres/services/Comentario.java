package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import pe.edu.pucp.softres.bo.client.ComentarioBO;
import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;

@WebService(serviceName = "comentarios")
@XmlSeeAlso({ComentariosDTO.class, ComentarioParametros.class})
public class Comentario {

    private final ComentarioBO comentarioBO;

    public Comentario() {
        this.comentarioBO = new ComentarioBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarComentario(ComentariosDTO comentario) throws IOException, InterruptedException {
        comentario.setFechaCreacion(new Date());
        comentario.setEstado(true);
        return this.comentarioBO.insertar(comentario);
    }

    @WebMethod(operationName = "obtenerPorId")
    public ComentariosDTO obtenerComentarioPorId( Integer comentarioId) throws IOException, InterruptedException {
        return this.comentarioBO.obtenerPorId(comentarioId);
    }

    @WebMethod(operationName = "listar")
    public List<ComentariosDTO> listarComentario(ComentarioParametros parametros) throws IOException, InterruptedException {
        return this.comentarioBO.listar(parametros);
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarComentario(ComentariosDTO comentario) throws IOException, InterruptedException {
        comentario.setFechaModificacion(new Date());
        return this.comentarioBO.modificar(comentario);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarComentario(ComentariosDTO comentario) throws IOException, InterruptedException {
        comentario.setFechaModificacion(new Date());
        comentario.setEstado(false);// lógica de eliminación lógica como en REST
        return this.comentarioBO.eliminar(comentario);
    }
} 