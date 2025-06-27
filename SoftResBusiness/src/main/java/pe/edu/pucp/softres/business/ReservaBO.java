package pe.edu.pucp.softres.business;

import pe.edu.pucp.softres.dao.ReservaDAO;
import pe.edu.pucp.softres.daoImp.ReservaDAOImpl;
import pe.edu.pucp.softres.model.ReservaDTO;
import pe.edu.pucp.softres.parametros.ReservaParametros;
import java.time.LocalDateTime;
import java.util.List;
import pe.edu.pucp.softres.model.EstadoReserva;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;

public class ReservaBO {

    private ReservaDAO reservaDAO;

    public ReservaBO() {
        this.reservaDAO = new ReservaDAOImpl();
    }

    public ReservaDTO insertar(ReservaDTO reserva) {
        Integer id = this.reservaDAO.insertar(reserva);
        reserva.setIdReserva(id);
        return reserva;
    }

    public ReservaDTO obtenerPorId(Integer reservaId) {
        return this.reservaDAO.obtenerPorId(reservaId);
    }

    public List<ReservaDTO> listar(ReservaParametros parametros) {
        return this.reservaDAO.listar(parametros);
    }

    public Integer modificar(ReservaDTO reserva) {
        return this.reservaDAO.modificar(reserva);
    }

    public Integer eliminar(ReservaDTO reserva) {
        return this.reservaDAO.eliminar(reserva);
    }
}

