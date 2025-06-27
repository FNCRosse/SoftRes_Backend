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
import java.util.List;
import pe.edu.pucp.softres.model.RolDTO;


/**
 *
 * @author BryanGnr
 */
public class RolBO {
    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public RolBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/Rol";
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
//       this.client.close();
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

    private void crearHttpRequestGET(Integer rolId) {
        String urlGET = this.url;
        if (rolId != null) {
            urlGET = urlGET.concat("/" + rolId);
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

    private String serializar(RolDTO rolDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(rolDTO);
        return jsonRequest;
    }

    private RolDTO deserializar(Class<RolDTO> clase) throws JsonProcessingException {
        RolDTO RolesDTORespuesta = mapper.readValue(this.response.body(), clase);
        return RolesDTORespuesta;
    }

    private List<RolDTO> deserializarListaDTO(TypeReference<List<RolDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<RolDTO> listRoles = this.mapper.readValue(json, typeReference);
        return listRoles;
    }

    public Integer insertar(RolDTO rol) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(rol);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        RolDTO rolDTORespuesta = this.deserializar(RolDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return rolDTORespuesta.getIdRol();
        }
        return 0;
    }

    public RolDTO obtenerPorId(Integer rolId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(rolId);
        this.enviarRequest();
        this.cerrarHttpClient();
        RolDTO rolDTORespuesta = this.deserializar(RolDTO.class);
        return rolDTORespuesta;
    }

    public List<RolDTO> listar() throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(null);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        List<RolDTO> listaSedes = this.deserializarListaDTO(new TypeReference<List<RolDTO>>() {
        });
        return listaSedes;
    }

    public Integer modificar(RolDTO rol) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(rol);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return rol.getIdRol();
        }
        return 0;
    }

    public Integer eliminar(RolDTO rol) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(rol);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return rol.getIdRol();
        }
        return 0;
    }
}
