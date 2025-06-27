package pe.edu.pucp.softres.business;

import pe.edu.pucp.softres.dao.FilaEsperaDAO;
import pe.edu.pucp.softres.daoImp.FilaEsperaDAOImpl;
import pe.edu.pucp.softres.model.FilaEsperaDTO;
import java.util.List;
import pe.edu.pucp.softres.parametros.FilaEsperaParametros;

public class FilaEsperaBO {

    private FilaEsperaDAO filaEsperaDAO;

    public FilaEsperaBO() {
        this.filaEsperaDAO = new FilaEsperaDAOImpl();
    }

    public FilaEsperaDTO insertar(FilaEsperaDTO filaespera) {
        Integer id = this.filaEsperaDAO.insertar(filaespera);
        filaespera.setIdFila(id);
        return filaespera;
    }

    public FilaEsperaDTO obtenerPorId(Integer idFila, Integer idUsuario) {
        return this.filaEsperaDAO.obtenerPorId(idFila, idUsuario);
    }

    public List<FilaEsperaDTO> listar(FilaEsperaParametros parametros) {
        return this.filaEsperaDAO.listar(parametros);
    }

    public Integer modificar(FilaEsperaDTO filaespera) {
        return this.filaEsperaDAO.modificar(filaespera);
    }

    public Integer eliminar(FilaEsperaDTO filaespera) {
        return this.filaEsperaDAO.eliminar(filaespera);
    }
}
