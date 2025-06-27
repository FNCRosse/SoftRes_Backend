package pe.edu.pucp.softres.business;

import pe.edu.pucp.softres.dao.ComentarioDAO;
import pe.edu.pucp.softres.daoImp.ComentarioDAOImpl;
import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;
import java.time.LocalDateTime;
import java.util.List;

public class ComentarioBO {

    private ComentarioDAO comentarioDAO;

    public ComentarioBO() {
        this.comentarioDAO = new ComentarioDAOImpl();
    }

    public ComentariosDTO insertar(ComentariosDTO comentario) {
        Integer id = this.comentarioDAO.insertar(comentario);
        comentario.setIdComentario(id);
        return comentario;
    }

    public ComentariosDTO obtenerPorId(Integer comentarioId) {
        return this.comentarioDAO.obtenerPorId(comentarioId);
    }

    public List<ComentariosDTO> listar(ComentarioParametros parametros) {
        return this.comentarioDAO.listar(parametros);
    }

    public Integer modificar(ComentariosDTO comentario) {
        return this.comentarioDAO.modificar(comentario);
    }

    public Integer eliminar(ComentariosDTO comentario) {
        return this.comentarioDAO.eliminar(comentario);
    }
}

