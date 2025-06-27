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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softres.model.ComentariosDTO;
import pe.edu.pucp.softres.parametros.ComentarioParametros;

/**
 *
 * @author BryanGnr
 */
public class ComentarioBO {

    private String url;
    private ObjectMapper mapper;
    private HttpClient client;
    private HttpRequest request;
    private HttpResponse<String> response;

    public ComentarioBO() {
        this.url = "http://localhost:8080/SoftResBusinessServidorWS/resources/Comentarios";
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
        this.client.close();
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

    private void crearHttpRequestGET(Integer comentarioId) {
        String urlGET = this.url;
        if (comentarioId != null) {
            urlGET = urlGET.concat("/" + comentarioId);
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

    private String serializar(ComentariosDTO comentarioDTO) throws JsonProcessingException {
        String jsonRequest = this.mapper.writeValueAsString(comentarioDTO);
        return jsonRequest;
    }

    private ComentariosDTO deserializar(Class<ComentariosDTO> clase) throws JsonProcessingException {
        ComentariosDTO comentarioDTORespuesta = mapper.readValue(this.response.body(), clase);
        return comentarioDTORespuesta;
    }

    private List<ComentariosDTO> deserializarListaDTO(TypeReference<List<ComentariosDTO>> typeReference) throws JsonProcessingException {
        String json = this.response.body();
        List<ComentariosDTO> listComentarios = this.mapper.readValue(json, typeReference);
        return listComentarios;
    }

    public Integer insertar(ComentariosDTO comentario) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(comentario);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, null);
        this.enviarRequest();
        ComentariosDTO comentarioDTORespuesta = this.deserializar(ComentariosDTO.class);
        this.cerrarHttpClient();

        if (response.statusCode() == Response.Status.CREATED.getStatusCode()) {
            return comentarioDTORespuesta.getIdComentario();
        }
        return 0;
    }

    public ComentariosDTO obtenerPorId(Integer comentarioId) throws IOException, InterruptedException {
        this.crearHttpClient();
        this.crearHttpRequestGET(comentarioId);
        this.enviarRequest();
        this.cerrarHttpClient();
        ComentariosDTO comentarioDTORespuesta = this.deserializar(ComentariosDTO.class);
        return comentarioDTORespuesta;
    }

    public List<ComentariosDTO> listar(ComentarioParametros parametros) throws IOException, InterruptedException {
        String jsonRequest = this.mapper.writeValueAsString(parametros);
        System.out.println("➡ JSON enviado al backend:");
        System.out.println(jsonRequest);
        this.crearHttpClient();
        this.crearHttpRequestPOST(jsonRequest, "listar");
        this.enviarRequest();
        this.cerrarHttpClient();
        System.out.println("⬅ Respuesta recibida del backend:");
        System.out.println(this.response.body());
        List<ComentariosDTO> listaComentarios = this.deserializarListaDTO(new TypeReference<List<ComentariosDTO>>() {
        });
        return listaComentarios;
    }

    public Integer modificar(ComentariosDTO comentario) throws JsonProcessingException, IOException, InterruptedException {
        String jsonRequest = this.serializar(comentario);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, null);
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.OK.getStatusCode()) {
            return comentario.getIdComentario();
        }
        return 0;
    }

    public Integer eliminar(ComentariosDTO comentario) throws IOException, InterruptedException {
        String jsonRequest = this.serializar(comentario);
        this.crearHttpClient();
        this.crearHttpRequestPUT(jsonRequest, "eliminar");
        this.enviarRequest();
        this.cerrarHttpClient();

        if (this.response.statusCode() == Response.Status.NO_CONTENT.getStatusCode()) {
            return comentario.getIdComentario();
        }
        return 0;
    }
} 