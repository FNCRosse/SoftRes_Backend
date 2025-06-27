/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.dao;

import java.util.List;
import pe.edu.pucp.softres.model.MotivosCancelacionDTO;

/**
 *
 * @author Rosse
 */
public interface MotivoCancelacionDAO {
    Integer insertar(MotivosCancelacionDTO rol);
    MotivosCancelacionDTO obtenerPorId(Integer id);
    List<MotivosCancelacionDTO> listar();
    Integer modificar(MotivosCancelacionDTO rol);
    Integer eliminar(MotivosCancelacionDTO rol);
}
