/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softres.bo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softres.model.CredencialesDTO;
import pe.edu.pucp.softres.model.UsuariosDTO;
import pe.edu.pucp.softres.parametros.UsuariosParametros;

/**
 *
 * @author BryanGnr
 */
public class UsuarioBO {

    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public UsuarioBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/Usuario";
        this.mapper = new ObjectMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        this.mapper.setDateFormat(sdf);
        this.mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.disable(com.fasterxml.jackson.databind.DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        this.client = null;
        this.request = null;
        this.response = null;
    }

    private void crearHttpClient() {
        this.client = HttpClient.newHttpClient();
    }

    private void cerrarHttpClient() {
//        this.client.close();
    }

    private void crearHttpRequestPOST(String jsonRequest, String urlExtra) {
        String urlPOST = this.url;
        if (urlExtra != null) {
            urlPOST = urlPOST.concat("/" + urlExtra);
        }
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlPOST))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void crearHttpRequestGET(Integer usuarioId) {
        String urlGET = this.url;
        if (usuarioId != null) {
            urlGET = urlGET.concat("/" + usuarioId);
        }

        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlGET))
                .GET()
                .build();
    }

    private void crearHttpRequestPUT(String jsonRequest, String urlExtra) {
        String urlPUT = this.url;
        if (urlExtra != null) {
            urlPUT = urlPUT.concat("/" + urlExtra);
        }
        this.request = HttpRequest.newBuilder()
                .uri(URI.create(urlPUT))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();
    }

    private void enviarRequest() throws IOException, InterruptedException {
        this.response = this.client.send(this.request, HttpResponse.BodyHandlers.ofString());
    }

    private String serializar(UsuariosDTO usuarioDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(usuarioDTO);
        return jsonRequest;
    }

    private UsuariosDTO deserializar(Class<UsuariosDTO> clase) throws JsonProcessingException {
        UsuariosDTO usuarioDTORespuesta = mapper.readValue(this.response.body(), clase);
        return usuarioDTORespuesta;
    }

    private List<UsuariosDTO> deserializarListaDTO(TypeReference<List<UsuariosDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<UsuariosDTO> listUsuarios = this.mapper.readValue(json, typeReference);
        return listUsuarios;
    }

    public Integer insertar(UsuariosDTO usuario) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(usuario);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        UsuariosDTO usuarioDTORespuesta = this.deserializar(UsuariosDTO.class);
        this.cerrarHttpClient();
        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return usuarioDTORespuesta.getIdUsuario();
        }
        return 0;
    }

    public UsuariosDTO obtenerPorId(Integer usuarioId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(usuarioId);
        this.enviarRequest();
        this.cerrarHttpClient();
        UsuariosDTO usuarioDTORespuesta = this.deserializar(UsuariosDTO.class);
        return usuarioDTORespuesta;
    }

    public List<UsuariosDTO> listar(UsuariosParametros parametros) throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(parametros);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        List<UsuariosDTO> listaUsuarios = this.deserializarListaDTO(new TypeReference<List<UsuariosDTO>>() {
        });
        return listaUsuarios;
    }

    public Integer modificar(UsuariosDTO usuario) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(usuario);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return usuario.getIdUsuario();
        }
        return 0;
    }

    public Integer eliminar(UsuariosDTO usuario) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(usuario);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return usuario.getIdUsuario();
        }
        return 0;
    }

    public UsuariosDTO login(CredencialesDTO credenciales) throws IOException, InterruptedException {
        // Serializar el DTO de credenciales
        String jsonRequest = this.mapper.writeValueAsString(credenciales);

        // Crear y enviar la solicitud POST a la ruta /login
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "login");  // <-- Usa la ruta /Usuario/login
        this.enviarRequest();
        this.cerrarHttpClient();

        // Procesar respuesta
        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return this.deserializar(UsuariosDTO.class); // Devuelve el usuario si el login es exitoso
        }

        return null; // Login fallido (email o contraseña incorrectos)
    }

    public Boolean ValidarDocumentoUnico(String numDocumento) throws IOException, InterruptedException {
        Map<String, String> body = new HashMap<>();
        body.put("numDocumento", numDocumento);
        String jsonRequest = this.mapper.writeValueAsString(body);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "ExisteDoc");
        this.enviarRequest();
        this.cerrarHttpClient();
        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return this.mapper.readValue(this.response.body(), Boolean.class);
        }
        return false;
    }
    public Boolean ValidarEmailUnico(String email) throws IOException, InterruptedException {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        String jsonRequest = this.mapper.writeValueAsString(body);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "ExisteEmail");
        this.enviarRequest();
        this.cerrarHttpClient();
        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return this.mapper.readValue(this.response.body(), Boolean.class);
        }
        return false;
    }
}
