package pe.edu.pucp.softres.business;


import java.util.List;
import pe.edu.pucp.softres.dao.MotivoCancelacionDAO;
import pe.edu.pucp.softres.daoImp.MotivoCancelacionDAOImpl;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Rosse
 */
public class MotivoCancelacionBO {
    
    private MotivoCancelacionDAO motivoCancelacionDAO;

    public MotivoCancelacionBO() {
        this.motivoCancelacionDAO = new MotivoCancelacionDAOImpl();
    }
    
    public MotivosCancelacionDTO insertar(MotivosCancelacionDTO mCancelacion) {
        Integer id =  this.motivoCancelacionDAO.insertar(mCancelacion);
        mCancelacion.setIdMotivo(id);
        return mCancelacion;
    }

    public MotivosCancelacionDTO obtenerPorId(Integer tDocId) {
        return this.motivoCancelacionDAO.obtenerPorId(tDocId);
    }

    public List<MotivosCancelacionDTO> listar() {
        return this.motivoCancelacionDAO.listar();
    }

    public Integer modificar(MotivosCancelacionDTO mCancelacion) {
        return this.motivoCancelacionDAO.modificar(mCancelacion);
    }

    public Integer eliminar(MotivosCancelacionDTO mCancelacion) {
        return this.motivoCancelacionDAO.eliminar(mCancelacion);
    }
}
