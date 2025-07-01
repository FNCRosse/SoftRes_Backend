/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.services;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softres.business.UsuarioBO;
import pe.edu.pucp.softres.model.CredencialesDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;

/**
 *
 * @author Rosse
 */
@Path("Usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class Usuario {

    private final UsuarioBO usuarioBO;

    public Usuario() {
        this.usuarioBO = new UsuarioBO();
    }

    @POST
    @Path("listar")
    public List<UsuariosDTO> listar(UsuariosParametros parametros) {
        return this.usuarioBO.listar(parametros);
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") Integer userID) {
        UsuariosDTO user = this.usuarioBO.obtenerPorId(userID);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(user).build();
    }

    @POST
    public Response insertar(UsuariosDTO user) {
        try {
            user.setFechaCreacion(new Date());
            user.setCantidadReservacion(0);
            user.setEstado(true);
            user = this.usuarioBO.insertar(user);
            return Response.status(Response.Status.CREATED).entity(user).build();
        } catch (Exception e) {
            e.printStackTrace(); // Para ver en consola
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error: " + e.getMessage()).build();
        }
    }

    @PUT
    public Response modificar(UsuariosDTO user) {
        user.setFechaModificacion(new Date());
        Integer respuesta = this.usuarioBO.modificar(user);
        if (respuesta > 0) {
            return Response.ok(user).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("/eliminar")
    public Response eliminar(UsuariosDTO user) {
        user.setFechaModificacion(new Date());
        user.setEstado(false);
        Integer respuesta = this.usuarioBO.eliminar(user);
        if (respuesta > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Path("login")
    public Response login(CredencialesDTO credenciales) {
        if (credenciales == null || credenciales.getEmail() == null || credenciales.getContrasenha() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email y contraseña son requeridos").build();
        }

        UsuariosDTO user = this.usuarioBO.login(credenciales.getEmail(), credenciales.getContrasenha());
        if (user == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciales inválidas").build();
        }

        return Response.ok(user).build();
    }

    @POST
    @Path("ExisteDoc")
    public Response ValidarDocumentoUnico(Map<String, String> json) {
        try {
            String numDocumento = json.get("numDocumento");
        boolean existe = this.usuarioBO.validarDocumentoUnico(numDocumento);
            return Response.ok().entity(existe).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al validar documento").build();
        }
    }
    @POST
    @Path("ExisteEmail")
    public Response ValidarEmailUnico(Map<String, String> json) {
        try {
            String email = json.get("email");
        boolean existe = this.usuarioBO.validarEmailUnico(email);
            return Response.ok().entity(existe).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al validar documento").build();
        }
    }
}
