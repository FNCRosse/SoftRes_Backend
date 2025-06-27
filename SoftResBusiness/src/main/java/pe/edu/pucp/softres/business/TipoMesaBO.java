package pe.edu.pucp.softres.business;
import pe.edu.pucp.softres.dao.TipoMesaDAO;
import pe.edu.pucp.softres.daoImp.TipoMesaDAOImpl;
import pe.edu.pucp.softres.model.TipoMesaDTO;

import java.util.List;
import pe.edu.pucp.softres.parametros.TipoMesaParametros;

public class TipoMesaBO {

    private TipoMesaDAO tipoMesaDAO;

    public TipoMesaBO() {
        this.tipoMesaDAO = new TipoMesaDAOImpl();
    }

    public TipoMesaDTO insertar(TipoMesaDTO tipomesa) {
        Integer id =  this.tipoMesaDAO.insertar(tipomesa);
        tipomesa.setIdTipoMesa(id);
        return tipomesa;
    }

    public TipoMesaDTO obtenerPorId(Integer tipoMesaId) {
        return this.tipoMesaDAO.obtenerPorId(tipoMesaId);
    }

    public List<TipoMesaDTO> listar() {
        return this.tipoMesaDAO.listar();
    }

    public Integer modificar(TipoMesaDTO tipoMesa) {
        return this.tipoMesaDAO.modificar(tipoMesa);
    }

    public Integer eliminar(TipoMesaDTO tipoMesa) {
        return this.tipoMesaDAO.eliminar(tipoMesa);
    }
}