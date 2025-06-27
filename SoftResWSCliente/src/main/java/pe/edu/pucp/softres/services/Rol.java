/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import java.io.IOException;
import java.util.List;
import pe.edu.pucp.softres.bo.client.RolBO;
import pe.edu.pucp.softres.model.RolDTO;
/**
 *
 * @author Rosse
 */
@WebService(serviceName = "rol")
@XmlSeeAlso({RolDTO.class})
public class Rol {
    private final RolBO rolBO;

    public Rol() {
        this.rolBO = new RolBO();
    }

    @WebMethod(operationName = "insertar")
    public Integer insertarRol(RolDTO rol) throws IOException, InterruptedException {
        return this.rolBO.insertar(rol);
    }

    @WebMethod(operationName = "obtenerPorId")
    public RolDTO obtenerRolPorId( Integer rolId) throws IOException, InterruptedException {
        return this.rolBO.obtenerPorId(rolId);
    }

    @WebMethod(operationName = "listar")
    public List<RolDTO> listarRoles() throws IOException, InterruptedException {
        return this.rolBO.listar();
    }

    @WebMethod(operationName = "modificar")
    public Integer modificarRol(RolDTO rol) throws IOException, InterruptedException {
        return this.rolBO.modificar(rol);
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminarRol(RolDTO rol) throws IOException, InterruptedException {
        return this.rolBO.eliminar(rol);
    }
}
