package pe.edu.pucp.softres.business;

import java.util.List;
import pe.edu.pucp.softres.dao.TipoDocumentoDAO;
import pe.edu.pucp.softres.daoImp.TipoDocumentoDAOImpl;
import pe.edu.pucp.softres.model.TipoDocumentoDTO;


public class TipoDocumentoBO {

    private final TipoDocumentoDAO tipoDocumentoDAO;

    public TipoDocumentoBO() {
        this.tipoDocumentoDAO = new TipoDocumentoDAOImpl();
    }

    public TipoDocumentoDTO insertar(TipoDocumentoDTO tipoDoc) {
        Integer id =  this.tipoDocumentoDAO.insertar(tipoDoc);
        tipoDoc.setIdTipoDocumento(id);
        return tipoDoc;
    }

    public TipoDocumentoDTO obtenerPorId(Integer tDocId) {
        return this.tipoDocumentoDAO.obtenerPorId(tDocId);
    }

    public List<TipoDocumentoDTO> listar() {
        return this.tipoDocumentoDAO.listar();
    }

    public Integer modificar(TipoDocumentoDTO tipoDoc) {
        return this.tipoDocumentoDAO.modificar(tipoDoc);
    }

    public Integer eliminar(TipoDocumentoDTO tipoDoc) {
        return this.tipoDocumentoDAO.eliminar(tipoDoc);
    }
}