package pe.edu.pucp.softres.business;

import java.time.LocalDateTime;
import pe.edu.pucp.softres.dao.HorarioAtencionDAO;
import pe.edu.pucp.softres.daoImp.HorarioAtencionDAOImpl;
import pe.edu.pucp.softres.model.HorarioAtencionDTO;
import pe.edu.pucp.softres.parametros.HorarioParametros;
import java.util.List;

public class HorarioAtencionBO {

    private final HorarioAtencionDAO horarioAtencionDAO;

    public HorarioAtencionBO() {
        this.horarioAtencionDAO = new HorarioAtencionDAOImpl();
    }

    public HorarioAtencionDTO insertar(HorarioAtencionDTO horario) {
        Integer id =  this.horarioAtencionDAO.insertar(horario);
        horario.setIdHorario(id);
        return horario;
    }

    public HorarioAtencionDTO obtenerPorId(Integer idHorario) {
        return this.horarioAtencionDAO.obtenerPorId(idHorario);
    }

    public List<HorarioAtencionDTO> listar(HorarioParametros parametros) {
        return this.horarioAtencionDAO.listar(parametros);
    }

    public Integer modificar(HorarioAtencionDTO horario) {
        return this.horarioAtencionDAO.modificar(horario);
    }

    public Integer eliminar(HorarioAtencionDTO horario) {
        return this.horarioAtencionDAO.eliminar(horario);
    }
}
